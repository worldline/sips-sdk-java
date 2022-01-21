package com.worldline.sips.model.response;

import com.worldline.sips.SIPS2Response;
import com.worldline.sips.model.data.RedirectionStatusCode;
import com.worldline.sips.model.data.ResponseCode;

import java.net.URL;

/**
 * The server's response to a session initialization request.
 */
public class InitializationResponse extends SIPS2Response {
    private String errorFieldName;
    private String redirectionData;
    private RedirectionStatusCode redirectionStatusCode;
    private String redirectionStatusMessage;
    private URL redirectionUrl;
    private String redirectionVersion;
    private ResponseCode responseCode;

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

    @Override
    public String toString() {
        return String.format(
            "InitializationResponse{errorFieldName='%s', redirectionData='%s', redirectionStatusCode=%s, redirectionStatusMessage='%s', redirectionUrl=%s, redirectionVersion='%s', responseCode=%s, seal='%s'}",
            errorFieldName, redirectionData, redirectionStatusCode, redirectionStatusMessage, redirectionUrl, redirectionVersion, responseCode, getSeal());
    }
}
