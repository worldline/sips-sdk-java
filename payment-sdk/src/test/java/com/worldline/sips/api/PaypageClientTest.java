package com.worldline.sips.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.worldline.sips.SipsClient;
import com.worldline.sips.configuration.PaymentEnvironment;
import com.worldline.sips.exception.IncorrectProxyConfException;
import com.worldline.sips.model.data.Currency;
import com.worldline.sips.model.data.OrderChannel;
import com.worldline.sips.model.data.RedirectionStatusCode;
import com.worldline.sips.model.data.S10TransactionReference;
import com.worldline.sips.model.request.PaymentRequest;
import com.worldline.sips.model.response.InitializationResponse;
import com.worldline.sips.model.response.PaypageResponse;
import com.worldline.sips.security.SealCalculator;
import com.worldline.sips.util.ObjectMapperHolder;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaypageClientTest {

    public static final String SECRET_KEY = "002001000000003_KEY1";
    private SipsClient paypageClient;
    private SipsClient decodeClient;
    private PaymentRequest paymentRequest;
    private Map<String, String> responseParameters;

    @BeforeEach
    void setUp() throws Exception {
        paypageClient = new SipsClient(PaymentEnvironment.SIMU, "002001000000003", 1, SECRET_KEY);
        decodeClient = new SipsClient(PaymentEnvironment.SIMU, "002001000000001", 1, "002001000000001_KEY1");

        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(2);

        paymentRequest.setCurrencyCode(Currency.EUR);
        paymentRequest.setOrderChannel(OrderChannel.INTERNET);
        paymentRequest.setNormalReturnUrl(new URL("http://localhost"));
        paymentRequest.setS10TransactionReference(new S10TransactionReference(ThreadLocalRandom.current().nextInt(0, 1_000_000), LocalDate.now()));
//        paymentRequest.setTransactionReference("1");

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

      System.out.println(ObjectMapperHolder.INSTANCE.get().writerFor(PaymentRequest.class)
          .writeValueAsString(paymentRequest));
      System.err.println(ObjectMapperHolder.INSTANCE.get().writerFor(InitializationResponse.class)
          .writeValueAsString(initializationResponse));
      assertEquals(RedirectionStatusCode.TRANSACTION_INITIALIZED, initializationResponse.getRedirectionStatusCode(), "Initialization failed! " + initializationResponse.getRedirectionStatusCode().name());
    }
    
    @Test
    void computeSealFromDoc_success() throws Exception {
      PaymentRequest request = ObjectMapperHolder.INSTANCE.get().readerFor(PaymentRequest.class).readValue("{\n"
          + "  \"amount\": \"2500\",\n"
          + "  \"automaticResponseUrl\": \"https://automatic-response-url.fr/\",\n"
          + "  \"normalReturnUrl\": \"https://normal-return-url/\",\n"
          + "  \"captureDay\": \"0\",\n"
          + "  \"captureMode\": \"AUTHOR_CAPTURE\",\n"
          + "  \"currencyCode\": \"978\",\n"
          + "  \"customerContact\":{\n"
          + "    \"email\":\"customer@email.com\"\n"
          + "  },\n"
          + "  \"interfaceVersion\": \"IR_WS_2.22\",\n"
          + "  \"keyVersion\": \"1\",\n"
          + "  \"merchantId\": \"011223344550000\",\n"
          + "  \"orderChannel\": \"INTERNET\",\n"
          + "  \"orderId\": \"ORD101\",\n"
          + "  \"returnContext\": \"ReturnContext\",\n"
          + "  \"transactionOrigin\": \"SO_WEBAPPLI\",\n"
          + "  \"transactionReference\": \"TREFEXA2012\",\n"
          + "  \"seal\": \"322b943d833417c1570e0a282641e8e29d6a5b968c9b846694b5610e18ab5b81\"\n"
          + "}");
      assertEquals("2500https://automatic-response-url.fr/0AUTHOR_CAPTURE978customer@email.comIR_WS_2.22011223344550000https://normal-return-url/INTERNETORD101ReturnContextSO_WEBAPPLITREFEXA2012", SealCalculator.getSealString(request), "Seal strings don't match");
      assertEquals("322b943d833417c1570e0a282641e8e29d6a5b968c9b846694b5610e18ab5b81", SealCalculator.calculate(SealCalculator.getSealString(request), "secret123"));
    }

    @Test
    void decodeResponse_with_succeeded_request() throws Exception {
        responseParameters.put("Data", "captureDay=0|captureMode=AUTHOR_CAPTURE|currencyCode=978|merchantId=002001000000001|orderChannel=INTERNET|responseCode=00|transactionDateTime=2018-02-06T07:54:23+01:00|transactionReference=b4fb98a9c2c|keyVersion=1|acquirerResponseCode=00|amount=2|authorisationId=12345|guaranteeIndicator=Y|cardCSCResultCode=4D|panExpiryDate=201902|paymentMeanBrand=VISA|paymentMeanType=CARD|customerIpAddress=194.78.195.168|maskedPan=4500#############01|holderAuthentRelegation=N|holderAuthentStatus=3D_SUCCESS|tokenPan=g011040a730424d1ba6|transactionOrigin=INTERNET|paymentPattern=ONE_SHOT");
        responseParameters.put("Seal", "56bddfce68695b9b8a9de51c426aae31bb303fb15570f343975eaa3bd33c8c59");
        PaypageResponse paypageResponse = decodeClient.decodeResponse(PaypageResponse.class, responseParameters);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());

        String rawJson = responseParameters.entrySet().stream().map(e -> "\"" + e.getKey() + "\":\"" + e.getValue() + "\"").collect(Collectors.joining(",", "{", "}"));
  
        paypageResponse = decodeClient.decodeResponse(PaypageResponse.class, rawJson);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());
    }

    @Test
    void decodeResponse_with_cancelled_request() throws Exception {
        responseParameters.put("Data", "captureDay=0|captureMode=AUTHOR_CAPTURE|currencyCode=978|merchantId=002001000000001|orderChannel=INTERNET|responseCode=17|transactionDateTime=2018-02-06T07:43:55+01:00|transactionReference=e1445438c15|keyVersion=1|amount=2|customerIpAddress=194.78.195.168|paymentPattern=ONE_SHOT");
        responseParameters.put("Seal", "8f488030781e3196726ce0658dbc26f19781f7c7fbe212b39d63d3f4d1d77301");
        PaypageResponse paypageResponse = decodeClient.decodeResponse(PaypageResponse.class, responseParameters);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());
    }

    @Test
    void decodeResponse_with_cancelled_request_unknown() throws Exception {
        responseParameters.put("Data", "captureDay=0|captureMode=AUTHOR_CAPTURE|currencyCode=978|merchantId=011122211100002|orderChannel=INTERNET|responseCode=17|transactionDateTime=2022-11-21T10:58:26+01:00|transactionReference=20221121896|keyVersion=1|amount=99|paymentMeanBrand=UNKNOWN|paymentMeanType=CARD|customerEmail=dl-fr-rcs-store@worldline.com|customerId=ATCBE606_196784528|customerIpAddress=160.92.8.86|merchantWalletId=ATCBE606_196784528|orderId=ATCBE0007452752|returnContext=b2261cc1c58baa7bc95ed414d48c9282cdf008094d56457bc3c3bbafdd82e168#SHOP_BE_REF#1626#WEB#false#false#false#true#FR#147.161.183.78#null#null#false#null#null#null#0#FR#fr|paymentPattern=ONE_SHOT|customerMobilePhone=null|mandateAuthentMethod=null|mandateUsage=null|transactionActors=null|mandateId=null|captureLimitDate=null|dccStatus=null|dccResponseCode=null|dccAmount=null|dccCurrencyCode=null|dccExchangeRate=null|dccExchangeRateValidity=null|dccProvider=null|statementReference=null|panEntryMode=null|walletType=null|holderAuthentMethod=null|holderAuthentProgram=null|paymentMeanId=null|instalmentNumber=null|instalmentDatesList=null|instalmentTransactionReferencesList=null|instalmentAmountsList=null|settlementMode=null|mandateCertificationType=null|valueDate=null|creditorId=null|acquirerResponseIdentifier=null|acquirerResponseMessage=null|paymentMeanTradingName=null|additionalAuthorisationNumber=null|issuerWalletInformation=null|s10TransactionId=896|s10TransactionIdDate=20221121|preAuthenticationColor=null|preAuthenticationInfo=null|preAuthenticationProfile=null|preAuthenticationThreshold=null|preAuthenticationValue=null|invoiceReference=null|s10transactionIdsList=null|cardProductCode=null|cardProductName=null|cardProductProfile=null|issuerCode=null|issuerCountryCode=null|acquirerNativeResponseCode=null|settlementModeComplement=null|preAuthorisationProfile=null|preAuthorisationProfileValue=null|preAuthorisationRuleResultList=null|preAuthenticationProfileValue=null|preAuthenticationRuleResultList=null|paymentMeanBrandSelectionStatus=null|transactionPlatform=PROD|avsAddressResponseCode=null|avsPostcodeResponseCode=null|customerCompanyName=null|customerBusinessName=null|customerLegalId=null|customerPositionOccupied=null|paymentAttemptNumber=1|holderContactEmail=null|installmentIntermediateServiceProviderOperationIdsList=null|holderAuthentType=null|acquirerContractNumber=null|secureReference=null|authentExemptionReasonList=null|paymentAccountReference=null|schemeTransactionIdentifier=null|guaranteeLimitDateTime=null|paymentMeanDataProvider=null");
        responseParameters.put("Seal", "14aba0bb3002a8fa88fc3e8fb1b61e2af103009d9489b0685f7f235590b00580");
        PaypageResponse paypageResponse = decodeClient.decodeResponse(PaypageResponse.class, responseParameters);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());
    }

    @Test
    void decodeResponse_with_refused_request() throws Exception {
        responseParameters.put("Data", "captureDay=0|captureMode=AUTHOR_CAPTURE|currencyCode=978|merchantId=002001000000001|orderChannel=INTERNET|responseCode=05|transactionDateTime=2018-02-06T07:50:34+01:00|transactionReference=8bd59312ff4|keyVersion=1|amount=2|guaranteeIndicator=N|panExpiryDate=201803|paymentMeanBrand=VISA|paymentMeanType=CARD|customerIpAddress=194.78.195.168|maskedPan=4500#############01|holderAuthentRelegation=N|holderAuthentStatus=3D_FAILURE|tokenPan=g011040a730424d1ba6|transactionOrigin=INTERNET|paymentPattern=ONE_SHOT");
        responseParameters.put("Seal", "e8c5bf4551ec60ce9b8ece6a98bdb1b5fde511539a391bc4ba314aaeac93b5be");
        PaypageResponse paypageResponse = decodeClient.decodeResponse(PaypageResponse.class, responseParameters);
        assertNotNull(paypageResponse.getData(), "Data field is empty!");
        assertNotNull(paypageResponse.getData().getResponseCode());
    }
}
