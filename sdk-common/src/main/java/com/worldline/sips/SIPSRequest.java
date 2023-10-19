package com.worldline.sips;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.worldline.sips.exception.SealCalculationException;
import com.worldline.sips.security.SealCalculator;
import com.worldline.sips.security.Sealable;

/**
 * An abstract object containing the basis of every SIPS requests
 *
 * @param <Response> the type of the response that should be returned by sending this request to SIPS
 * @see Sealable
 * @see SIPSResponse
 */
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class SIPSRequest<Response extends SIPSResponse> implements Sealable {
    private final String endpoint;
    private String seal;
    private String merchantId;
    private Integer keyVersion;
    private String sealAlgorithm = "HMAC-SHA-256";

    /**
     * @param endpoint the http endpoint targeted by this request
     */
    public SIPSRequest(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Compute the seal of this request.
     *
     * @param secretKey the secret key that will be used to generate this request's seal
     * @throws SealCalculationException when seal calculation fails, see inner exception for details.
     */
    public void calculateSeal(String secretKey) throws SealCalculationException {
        this.seal = SealCalculator.calculate(SealCalculator.getSealString(this), secretKey);
    }

    /**
     * Should be provided by subclasses to permit the deserialization of the response
     *
     * @return the response's type
     */
    @JsonIgnore
    public abstract Class<Response> getResponseType();

    @JsonIgnore
    final Class<? extends SIPSRequest> getRealType() {
        return this.getClass();
    }

    @JsonIgnore
    public String getEndpoint() {
        return endpoint;
    }

    public String getSealAlgorithm() {
        return sealAlgorithm;
    }

    public void setSealAlgorithm(String sealAlgorithm) {
        this.sealAlgorithm = sealAlgorithm;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getKeyVersion() {
        return keyVersion;
    }

    public void setKeyVersion(Integer keyVersion) {
        this.keyVersion = keyVersion;
    }

    public String getSeal() {
        return seal;
    }

}
