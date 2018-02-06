package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.worldline.sips.helper.ResponseDataDeserializer;

/**
 * The result of payment made via the SIPS payment page.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaypageResponse {

    @JsonProperty("Data")
    @JsonUnwrapped
    @JsonDeserialize(using = ResponseDataDeserializer.class)
    private ResponseData data;
    @JsonProperty("Encode")
    private String encode;
    @JsonProperty("InterfaceVersion")
    private String interFaceVersion;
    @JsonProperty("Seal")
    private String seal;

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getInterFaceVersion() {
        return interFaceVersion;
    }

    public void setInterFaceVersion(String interFaceVersion) {
        this.interFaceVersion = interFaceVersion;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }
}
