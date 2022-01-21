package com.worldline.sips.api.configuration;

import com.worldline.sips.model.SipsEnvironment;

import java.net.URI;

public enum OfficeEnvironment implements SipsEnvironment {
    TEST("https://office-server.test.sips-services.com"),
    PROD("https://office-server.sips-services.com");

    private final String url;
    private final URI uri;

    OfficeEnvironment(String url) {
        this.url = url + "/rs-services/v2";
        this.uri = URI.create(url);
    }

    public String getUrl() {
        return url;
    }

    public URI getURI() {
        return uri;
    }

}
