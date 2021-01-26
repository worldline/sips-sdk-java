package com.worldline.sips.util;

import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SealCalculatorTest {

    private static final String ENCODED_REQUEST_SEAL = "198e5f278e3f8548e174e84492953c4871732278b7e2aa2cbf20bb1ab85914ea";
    private static final String ENCODED_RESPONSE_SEAL = "dd6eb8dd6c951b1ddc1af121007aaabe8ad4fda1d15ce386cfa455821d602025";
    private static final String DEMO_KEY = "superSafeSecretKey";
    private static final String REPSONSE_DEMO_KEY = "002001000000002_KEY1";
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
        String expected = "10http://test.comcustomerIdIR_WS_2.19customCSS.css";
        assertEquals(expected, actual, "Sealstring is incorrect!");
    }

    @Test
    void getSealString_with_Currency() {
        paymentRequest.setCurrencyCode(Currency.EUR);
        String actual = SealCalculator.getSealString(paymentRequest);
        String expected = "10http://test.com978customerIdIR_WS_2.19customCSS.css";
        assertEquals(expected, actual, "Sealstring is incorrect!");
    }

    @Test
    void getSealString_with_list() {
        paymentRequest.getPaymentMeanBrandList().add(PaymentMeanBrand.VISA);
        paymentRequest.getPaymentMeanBrandList().add(PaymentMeanBrand.BCMC);
        String actual = SealCalculator.getSealString(paymentRequest);
        String expected = "10http://test.comcustomerIdIR_WS_2.19BCMCVISAcustomCSS.css";
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
        String expected = "10http://test.comcustomerBusinessNamecustomerCityfirstNamelastNamecustomerIddeliveryCompanydeliveryZipcodeIR_WS_2.19customCSS.css";
        assertEquals(expected, actual, "Sealstring is incorrect!");
    }

    @Test
    void getSealString_with_ignoredField() {
        paymentRequest.setKeyVersion(200);
        String actual = SealCalculator.getSealString(paymentRequest);
        String expected = "10http://test.comcustomerIdIR_WS_2.19customCSS.css";
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
        initializationResponse.setRedirectionUrl(new URL("https://payment-web.simu.sips-services.com/payment"));
        initializationResponse.setRedirectionData("FTEx33MrE9Nc0gvtIS0aydJngXH8uuirg3ZpFD_KRM22C9e3IFDdBdSZB3kdQwcyIjWhKpM9mHYTCaCCy7Vm0YWrLscP-TJ895c2GXsTKlwEkO_VEU-2j2VGVcbYAKE9kcoV6jnVU5OJE1AMXEXnm1AkMrm3riB35p7x-WUdnaU0oKIhsit2M4_mci3fWH9WNTqo-A07qPd9-5zRKCr2F91Z8R4HogmLYdx_xh6BOyXKp693Smrq-2H64A2BOC6n89JQT-e9GNBM_up1YMa-vvS3-b6bMoZ01ngO7n2-NttxvXn78XEDDDVQfYZGPrpjywvDhGTfhBun1mdbSfzGPOwd6dcLre4Nguht3JCsT27EyLOxGEjCDwMjKJ0Gb2toChEMRv7TtX2SlXGMuyUr24oToHGbdt6zOm3q1R7XnCqnVYT_YNnNPf6lppQCW81FVvdX5zfbW65tQzja0CFC2ss-kyT0v4owm8LLNMy2rEU2JYnQQrT2qhQYf8lGxL2qkC0S1TpPLRzK-ry3mkEcBgwxFKbB2DX4kRamx4n6v_Yke2PS1PUPoWbdruRkK1mDJAJhSorV4O3LeeM1B3LkuPNU2xZw__I55zaOyIy2sWz52Su6gapGcsw8qTNOhcOwQi1sYaSGviCfKF75zC-GCjbHGoyHfO5RBswwmqBMNbVc7l2FIAw8ZDwn18E07pDWNeMwaeWYrYhR_n1BfhaW03H5TojvjnEMMSfd11nab0PjFE5zWB3r--OadfE7m-NqHL70WKLPYNfvKdzXzoI3ejc3SmWycXMpJgENZAFVzy4MojbVby3prA4IXQ_xSEisoWoGJb1Kg2LxEUih9Jqpi9vinH_nqt29lHFKnWSIuhT8UdsBYvFRGYyrbxPOOz1eNBqvHO49eI2hpKeL280_eR4HmLPiiDASog3BgDFB2_Ed6upm_N1PUEdQ1xmwXkiL7fqnaFABXySxsTVhxyRgXGSAmA0gmeYwFf7bGIKUc-FPwqWewDxRnIfnz6Eh3qDG41xEr5URpc0LCzzo5XjFt5h7wmp3FftmSai4rCFIM6B4Jd9lljeXgAMayzfig77_S_JMtEDw6oqzbX6QpBsKnfnrAtxk26LL07qB5Yw9qj-NEbWf28GOPN9--xY5AeUbya5nyNQiT-3TyebxnjUysTrTuq1DptPflIrUuIwAewT6K1I-XRYDpgdTRwWFzTIi2kItzGcbozxMJi0S6eJnOQNb9zNSrUBysDlSGVct1UsHqYQO56_-uyAwZ7lv23WIZdgbUaGELoPsba8Jg9rzR-aZ0zEe9owORSbAh5qkk0ByvOQniLqGYn36AJbKiSrSjRG1n9vBjjzffvsWOQHlG_tPV6WPVaXvanjaHrg_v2tjtqWPCUCRFg6seM4KiZ3G5-WPkPX0LW19iu4ypazFsfgElIfF7--XuN2wnrbf5tvPxpk-EHACkcFUQ7ozBw5xjRG1n9vBjjzffvsWOQHlGzGBbU3BAx_KVN_SQuZCNZ66O6O6FdOnZF_dC9vfuhZx5livIUUGTQOiFt4JQv_tJ3gl32WWN5eAAxrLN-KDvv_z_69h-FQfvm0DChFKn5h9Jb54QR0qamngPqTNqRQxNcmK3onHxmG7IIS9Gsltfie7sc4Lt9p_hqLAC28DDefH03yQ0l15QSQSA7t8x3rtGuflj5D19C1tfYruMqWsxbF_OuasNvoqSnJcHI_HyMMnf7eGnYHEcQk4vutjrpZhcH1TRZ3nQYrrxzId10uYeGeB7NXgInfbYearASIu2_vnMuJj1F84sjXcMM29qxh5KvOAaCle_EK7_SCamPY6SzkmTCyJprTaIBkhUUjUDdem4rN_XaLIav0orq8E4IwU740RtZ_bwY483377FjkB5RsKKPYjzvh_RlAEOH7vo0AIf7eGnYHEcQk4vutjrpZhcI0RtZ_bwY483377FjkB5Rv07-b-FhxhFkrKQcO0J0LIaKn33u4Hp88PYNHX40sIyNK3sqPZPiYGDU7q6T76kxla1JZwz-5wSgABK-wV16UlA1v3M1KtQHKwOVIZVy3VSwephA7nr_67IDBnuW_bdYjqqqfJtu70VoEtZOvxckajWtSWcM_ucEoAASvsFdelJcaipDQGiSLyw4gWeiGVvJAJHG6sS8x21XuBIuCbQ8l9Orcf_-QEVEHjGdswxxpSUovuFIRWVkRniMqau0PF2uF2Vi5vBX5AQYvblDS0I-Nk5mOOxtF3rdTMKSdmHaylZGleYPX4zPU6HmEV4ipdMcHx_vB89SS_gwRtQ9PWLJh3HJwvIO2GJs6lrufoKpeHVk9jne_ybEoDL1qOawB6ESQK5q5UXSwdR3YaB8M0qw-UPPibFlDSsbVF-zNvfbA8IBQL83Jj6EwBHBwT0RHvrvQ9-0nLg03cckYVc3IJh-dMok6Z5Ga3fsi_953xGMoOX4_z-gTn5pXhDYgI-0g_CKeAtgLeNsxz-PlTwW_U_kfW6Q3V7HDgOi-QZxxNSTMSkQpAdC3JcbHIGuEHVGxs5zWWT5tmsYkXbS11VEduPVXb9NpuUzUB5GLL8W3y9w3hTgLI1bKcCSsF4b3mgLEwNNGbEzYudvCLn3MP8g5BKQ-GlMMZU93_ANjsgNXUNK3j0SHC1NaycxVaWqmVP9uWp1U");

        String sealString = SealCalculator.getSealString(initializationResponse);
        String actual = SealCalculator.calculate(sealString, REPSONSE_DEMO_KEY);
        assertEquals(ENCODED_RESPONSE_SEAL, actual, "Encoded seal is incorrect!");
    }
}