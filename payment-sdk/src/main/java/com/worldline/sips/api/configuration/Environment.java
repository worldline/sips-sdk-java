package com.worldline.sips.api.configuration;

/**
 * The different environments available for the Worldline SIPS API.
 */
public enum Environment {
    SIMU("https://payment-webinit.simu.sips-services.com/rs-services/v2/paymentInit"),
    TEST("https://payment-webinit.test.sips-services.com/rs-services/v2/paymentInit"),
    PROD("https://payment-webinit.sips-services.com/rs-services/v2/paymentInit");

    private final String url;

    Environment(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
