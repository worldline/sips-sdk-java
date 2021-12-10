package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.worldline.sips.exception.UnknownStatusException;

public enum ResponseCode {
    ACCEPTED("00"),
    CARD_CEILING_EXCEEDED("02"),
    INVALID_MERCHANT_CONTRACT("03"),
    AUTHORIZATION_REFUSED("05"),
    PAN_BLOCKED("11"),
    INVALID_TRANSACTION("12"),
    INVALID_DATA("14"),
    CUSTOMER_CANCELLATION("17"),
    INCORRECT_FORMAT("30"),
    FRAUD_SUSPECTED("34"),
    PAYMENT_MEAN_EXPIRED("54"),
    PENDING("60"),
    SECURITY_RULES_NOT_OBSERVED("63"),
    MAX_ATTEMPTS_REACHED("75"),
    SERVICE_UNAVAILABLE("90"),
    DUPLICATED_TRANSACTION("94"),
    TIMEFRAME_EXCEEDED("97"),
    INTERNAL_ERROR("99");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    @JsonCreator
    public static ResponseCode fromCode(String code) throws UnknownStatusException {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }

        throw new UnknownStatusException(code + " is an unknown response code!");
    }

    @JsonValue
    public String getCode() {
        return code;
    }

}
