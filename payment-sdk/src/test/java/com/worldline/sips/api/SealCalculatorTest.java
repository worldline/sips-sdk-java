package com.worldline.sips.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.model.NamedPaymentMeanBrand;
import com.worldline.sips.model.data.Address;
import com.worldline.sips.model.data.Currency;
import com.worldline.sips.model.data.CustomerAddress;
import com.worldline.sips.model.data.CustomerContact;
import com.worldline.sips.model.data.RedirectionStatusCode;
import com.worldline.sips.model.request.PaymentRequest;
import com.worldline.sips.model.response.InitializationResponse;
import com.worldline.sips.security.SealCalculator;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SealCalculatorTest {

    private static final String ENCODED_REQUEST_SEAL = "198e5f278e3f8548e174e84492953c4871732278b7e2aa2cbf20bb1ab85914ea";
    private static final String ENCODED_RESPONSE_SEAL = "c3eb508b0d419e3bd2bb1a0416c2a48585fa6d683aa8d1a3dfba8ee877079f9f";
    private static final String DEMO_KEY = "superSafeSecretKey";
    private static final String RESPONSE_DEMO_KEY = "002001000000002_KEY1";

    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() throws Exception {
        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(10);
        paymentRequest.setCustomerId("customerId");
        paymentRequest.setAutomaticResponseUrl(new URL("http://test.com"));
        paymentRequest.setTemplateName("customCSS.css");
    }

    @Test
    void getSealString() {
        String actual = SealCalculator.getSealString(paymentRequest);
        String expected = "10http://test.comcustomerIdIR_WS_2.46customCSS.css";
        assertEquals(expected, actual, "Sealstring is incorrect!");
    }

    @Test
    void getSealString_with_Currency() {
        paymentRequest.setCurrencyCode(Currency.EUR);
        String actual = SealCalculator.getSealString(paymentRequest);
        String expected = "10http://test.com978customerIdIR_WS_2.46customCSS.css";
        assertEquals(expected, actual, "Sealstring is incorrect!");
    }

    @Test
    void getSealString_with_list() {
        paymentRequest.getPaymentMeanBrandList().add(NamedPaymentMeanBrand.VISA);
        paymentRequest.getPaymentMeanBrandList().add(NamedPaymentMeanBrand.BCMC);
        String actual = SealCalculator.getSealString(paymentRequest);
        String expected = "10http://test.comcustomerIdIR_WS_2.46BCMCVISAcustomCSS.css";
        assertEquals(expected, actual, "Sealstring is incorrect!");
    }

    @Test
    void getSealString_with_Container() {
        Address deliveryAddress = new Address();
        deliveryAddress.setZipcode("deliveryZipcode");
        deliveryAddress.setCompany("deliveryCompany");
        paymentRequest.setDeliveryAddress(deliveryAddress);

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCity("customerCity");
        customerAddress.setBusinessName("customerBusinessName");
        paymentRequest.setCustomerAddress(customerAddress);

        CustomerContact customerContact = new CustomerContact();
        customerContact.setFirstName("firstName");
        customerContact.setLastName("lastName");
        paymentRequest.setCustomerContact(customerContact);

        String actual = SealCalculator.getSealString(paymentRequest);
        String expected = "10http://test.comcustomerBusinessNamecustomerCityfirstNamelastNamecustomerIddeliveryCompanydeliveryZipcodeIR_WS_2.46customCSS.css";
        assertEquals(expected, actual, "Sealstring is incorrect!");
    }

    @Test
    void getSealString_with_ignoredField() {
        paymentRequest.setKeyVersion(200);
        String actual = SealCalculator.getSealString(paymentRequest);
        String expected = "10http://test.comcustomerIdIR_WS_2.46customCSS.css";
        assertEquals(expected, actual, "Sealstring is incorrect!");
    }

    @Test
    void calculate() throws SealCalculationException {
        String actual = SealCalculator.calculate("This is a test!", DEMO_KEY);
        assertEquals(ENCODED_REQUEST_SEAL, actual, "Encoded seal is incorrect!");
    }

    @Test
    void calculate_with_null_string() {
        assertThrows(NullPointerException.class, () -> SealCalculator.calculate(null, DEMO_KEY), "No exception is thrown when string to encode is null!");
    }

    @Test
    void calculate_with_null_key() {
        assertThrows(NullPointerException.class, () -> SealCalculator.calculate("This is a test!", null), "No exception is thrown when string to encode is null!");
    }

    @Test
    void calculate_response_seal() throws MalformedURLException, SealCalculationException {
        InitializationResponse initializationResponse = new InitializationResponse();
        initializationResponse.setRedirectionStatusCode(RedirectionStatusCode.TRANSACTION_INITIALIZED);
        initializationResponse.setRedirectionStatusMessage("INITIALISATION REQUEST ACCEPTED");
        initializationResponse.setRedirectionVersion("IR_WS_2.0");
        initializationResponse.setRedirectionUrl(new URL("https://payment-gateway.net/"));
        initializationResponse.setRedirectionData("4AgbsrffvPgzDghQysbOJIZBJTZsk1KNlTmoOCtSORkMfzQgSR5OEw0gAE2bAAFbHuYQXuBmiEfuwD81QlmDInPmanHWkKNA3X3jUbC8Jh9oPTfgoPO4PNo20aNt6yb5z8cDOX8J_rNvwzfJetyCxEVrB93g9YRFX4n3mM85FC5o");
        String sealString = SealCalculator.getSealString(initializationResponse);
        String actual = SealCalculator.calculate(sealString, RESPONSE_DEMO_KEY);
        assertEquals(ENCODED_RESPONSE_SEAL, actual, "Encoded seal is incorrect!");
    }
}
