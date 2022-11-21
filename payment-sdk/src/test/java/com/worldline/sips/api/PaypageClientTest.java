package com.worldline.sips.api;

import com.worldline.sips.SipsClient;
import com.worldline.sips.configuration.PaymentEnvironment;
import com.worldline.sips.exception.IncorrectProxyConfException;
import com.worldline.sips.model.data.Currency;
import com.worldline.sips.model.data.OrderChannel;
import com.worldline.sips.model.data.RedirectionStatusCode;
import com.worldline.sips.model.request.PaymentRequest;
import com.worldline.sips.model.response.InitializationResponse;
import com.worldline.sips.model.response.PaypageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaypageClientTest {
    private SipsClient paypageClient;
    private PaymentRequest paymentRequest;
    private Map<String, String> responseParameters;

    @BeforeEach
    void setUp() throws Exception {
        paypageClient = new SipsClient(PaymentEnvironment.SIMU, "002001000000001", 1, "002001000000001_KEY1");

        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(2);
        paymentRequest.setCurrencyCode(Currency.EUR);
        paymentRequest.setOrderChannel(OrderChannel.INTERNET);
        paymentRequest.setNormalReturnUrl(new URL("http://localhost"));
        paymentRequest.setTransactionReference(UUID.randomUUID().toString().substring(0, 12).replace("-", ""));

        responseParameters = new HashMap<>();
        responseParameters.put("InterfaceVersion", "HP_2.0");

    }

    @Test
    void testClientProxyException() {
        assertThrows(IncorrectProxyConfException.class, () -> new SipsClient(PaymentEnvironment.TEST, "002001000000001", 1, "002001000000001_KEY1", true, "monProxy", null));

        assertThrows(IncorrectProxyConfException.class, () -> new SipsClient(PaymentEnvironment.TEST, "002001000000001", 1, "002001000000001_KEY1", true, "", 3128));

        assertThrows(IncorrectProxyConfException.class, () -> new SipsClient(PaymentEnvironment.TEST, "002001000000001", 1, "002001000000001_KEY1", true, null, null));
    }

    @Test
    void execute() throws Exception {
        InitializationResponse initializationResponse = paypageClient.send(paymentRequest);
        assertEquals(RedirectionStatusCode.TRANSACTION_INITIALIZED, initializationResponse.getRedirectionStatusCode(), "Initialization failed!");
    }

    @Test
    void decodeResponse_with_succeeded_request() throws Exception {
        responseParameters.put("Data", "captureDay=0|captureMode=AUTHOR_CAPTURE|currencyCode=978|merchantId=002001000000001|orderChannel=INTERNET|responseCode=00|transactionDateTime=2018-02-06T07:54:23+01:00|transactionReference=b4fb98a9c2c|keyVersion=1|acquirerResponseCode=00|amount=2|authorisationId=12345|guaranteeIndicator=Y|cardCSCResultCode=4D|panExpiryDate=201902|paymentMeanBrand=VISA|paymentMeanType=CARD|customerIpAddress=194.78.195.168|maskedPan=4500#############01|holderAuthentRelegation=N|holderAuthentStatus=3D_SUCCESS|tokenPan=g011040a730424d1ba6|transactionOrigin=INTERNET|paymentPattern=ONE_SHOT");
        responseParameters.put("Seal", "56bddfce68695b9b8a9de51c426aae31bb303fb15570f343975eaa3bd33c8c59");
        PaypageResponse paypageResponse = paypageClient.decodeResponse(PaypageResponse.class, responseParameters);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());
    }

    @Test
    void decodeResponse_with_cancelled_request() throws Exception {
        responseParameters.put("Data", "captureDay=0|captureMode=AUTHOR_CAPTURE|currencyCode=978|merchantId=002001000000001|orderChannel=INTERNET|responseCode=17|transactionDateTime=2018-02-06T07:43:55+01:00|transactionReference=e1445438c15|keyVersion=1|amount=2|customerIpAddress=194.78.195.168|paymentPattern=ONE_SHOT");
        responseParameters.put("Seal", "8f488030781e3196726ce0658dbc26f19781f7c7fbe212b39d63d3f4d1d77301");
        PaypageResponse paypageResponse = paypageClient.decodeResponse(PaypageResponse.class, responseParameters);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());
    }

    @Test
    void decodeResponse_with_cancelled_request_unknown() throws Exception {
        responseParameters.put("Data", "captureDay=0|captureMode=AUTHOR_CAPTURE|currencyCode=978|merchantId=011122211100002|orderChannel=INTERNET|responseCode=17|transactionDateTime=2022-11-21T10:58:26+01:00|transactionReference=20221121896|keyVersion=1|amount=99|paymentMeanBrand=UNKNOWN|paymentMeanType=CARD|customerEmail=dl-fr-rcs-store@worldline.com|customerId=ATCBE606_196784528|customerIpAddress=160.92.8.86|merchantWalletId=ATCBE606_196784528|orderId=ATCBE0007452752|returnContext=b2261cc1c58baa7bc95ed414d48c9282cdf008094d56457bc3c3bbafdd82e168#SHOP_BE_REF#1626#WEB#false#false#false#true#FR#147.161.183.78#null#null#false#null#null#null#0#FR#fr|paymentPattern=ONE_SHOT|customerMobilePhone=null|mandateAuthentMethod=null|mandateUsage=null|transactionActors=null|mandateId=null|captureLimitDate=null|dccStatus=null|dccResponseCode=null|dccAmount=null|dccCurrencyCode=null|dccExchangeRate=null|dccExchangeRateValidity=null|dccProvider=null|statementReference=null|panEntryMode=null|walletType=null|holderAuthentMethod=null|holderAuthentProgram=null|paymentMeanId=null|instalmentNumber=null|instalmentDatesList=null|instalmentTransactionReferencesList=null|instalmentAmountsList=null|settlementMode=null|mandateCertificationType=null|valueDate=null|creditorId=null|acquirerResponseIdentifier=null|acquirerResponseMessage=null|paymentMeanTradingName=null|additionalAuthorisationNumber=null|issuerWalletInformation=null|s10TransactionId=896|s10TransactionIdDate=20221121|preAuthenticationColor=null|preAuthenticationInfo=null|preAuthenticationProfile=null|preAuthenticationThreshold=null|preAuthenticationValue=null|invoiceReference=null|s10transactionIdsList=null|cardProductCode=null|cardProductName=null|cardProductProfile=null|issuerCode=null|issuerCountryCode=null|acquirerNativeResponseCode=null|settlementModeComplement=null|preAuthorisationProfile=null|preAuthorisationProfileValue=null|preAuthorisationRuleResultList=null|preAuthenticationProfileValue=null|preAuthenticationRuleResultList=null|paymentMeanBrandSelectionStatus=null|transactionPlatform=PROD|avsAddressResponseCode=null|avsPostcodeResponseCode=null|customerCompanyName=null|customerBusinessName=null|customerLegalId=null|customerPositionOccupied=null|paymentAttemptNumber=1|holderContactEmail=null|installmentIntermediateServiceProviderOperationIdsList=null|holderAuthentType=null|acquirerContractNumber=null|secureReference=null|authentExemptionReasonList=null|paymentAccountReference=null|schemeTransactionIdentifier=null|guaranteeLimitDateTime=null|paymentMeanDataProvider=null");
        responseParameters.put("Seal", "abcdfb8ee60361aa29856f0c4d1f2947cebbc89189227dda1c32a4bf8d852413");
        PaypageResponse paypageResponse = paypageClient.decodeResponse(PaypageResponse.class, responseParameters);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());
    }

    @Test
    void decodeResponse_with_refused_request() throws Exception {
        responseParameters.put("Data", "captureDay=0|captureMode=AUTHOR_CAPTURE|currencyCode=978|merchantId=002001000000001|orderChannel=INTERNET|responseCode=05|transactionDateTime=2018-02-06T07:50:34+01:00|transactionReference=8bd59312ff4|keyVersion=1|amount=2|guaranteeIndicator=N|panExpiryDate=201803|paymentMeanBrand=VISA|paymentMeanType=CARD|customerIpAddress=194.78.195.168|maskedPan=4500#############01|holderAuthentRelegation=N|holderAuthentStatus=3D_FAILURE|tokenPan=g011040a730424d1ba6|transactionOrigin=INTERNET|paymentPattern=ONE_SHOT");
        responseParameters.put("Seal", "e8c5bf4551ec60ce9b8ece6a98bdb1b5fde511539a391bc4ba314aaeac93b5be");
        PaypageResponse paypageResponse = paypageClient.decodeResponse(PaypageResponse.class, responseParameters);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());
    }
}
