package com.worldline.sips.configuration;

import com.worldline.sips.model.SipsEnvironment;

/**
 * The different environments available for the Worldline SIPS API.
 */
public enum PaymentEnvironment implements SipsEnvironment {
    SIMU("https://payment-webinit.simu.sips-services.com/rs-services/v2/paymentInit"),
    TEST("https://payment-webinit.test.sips-services.com/rs-services/v2/paymentInit"),
    PROD("https://payment-webinit.sips-services.com/rs-services/v2/paymentInit");

    private final String url;

    PaymentEnvironment(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
