package com.worldline.sips.model;

import java.net.URL;

/**
 * The server's response to a session initialization request.
 */
public class InitializationResponse {
    private String errorFieldName;
    private String redirectionData;
    private RedirectionStatusCode redirectionStatusCode;
    private String redirectionStatusMessage;
    private URL redirectionUrl;
    private String redirectionVersion;
    private ResponseCode responseCode;
    private String seal;

    public String getErrorFieldName() {
        return errorFieldName;
    }

    public void setErrorFieldName(String errorFieldName) {
        this.errorFieldName = errorFieldName;
    }

    public String getRedirectionData() {
        return redirectionData;
    }

    public void setRedirectionData(String redirectionData) {
        this.redirectionData = redirectionData;
    }

    public RedirectionStatusCode getRedirectionStatusCode() {
        return redirectionStatusCode;
    }

    public void setRedirectionStatusCode(RedirectionStatusCode redirectionStatusCode) {
        this.redirectionStatusCode = redirectionStatusCode;
    }

    public String getRedirectionStatusMessage() {
        return redirectionStatusMessage;
    }

    public void setRedirectionStatusMessage(String redirectionStatusMessage) {
        this.redirectionStatusMessage = redirectionStatusMessage;
    }

    public URL getRedirectionUrl() {
        return redirectionUrl;
    }

    public void setRedirectionUrl(URL redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

    public String getRedirectionVersion() {
        return redirectionVersion;
    }

    public void setRedirectionVersion(String redirectionVersion) {
        this.redirectionVersion = redirectionVersion;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }
}
