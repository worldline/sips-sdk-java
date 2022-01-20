package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.worldline.sips.exception.UnknownStatusException;

public enum RedirectionStatusCode {
    TRANSACTION_INITIALIZED("00"),
    INVALID_MERCHANT_CONTRACT("03"),
    INVALID_TRANSACTION("12"),
    INCORRECT_FORMAT("30"),
    FRAUD_SUSPECTED("34"),
    DUPLICATED_TRANSACTION("94"),
    INTERNAL_ERROR("99");

    private final String code;

    RedirectionStatusCode(String code) {
        this.code = code;
    }

    @JsonCreator
    public static RedirectionStatusCode fromCode(String code) throws UnknownStatusException {
        for (RedirectionStatusCode redirectionStatusCode : RedirectionStatusCode.values()) {
            if (redirectionStatusCode.getCode().equals(code)) {
                return redirectionStatusCode;
            }
        }

        throw new UnknownStatusException(code + " is an unknown redirection status code!");
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
