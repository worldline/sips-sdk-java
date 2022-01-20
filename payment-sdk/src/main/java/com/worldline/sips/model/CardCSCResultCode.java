package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.worldline.sips.exception.UnknownStatusException;

public enum CardCSCResultCode {
    INCORRECT_CRYPTOGRAM("4E"),
    CORRECT_CRYPTOGRAM("4D"),
    CRYPTOGRAM_NOT_PROCESSED("50"),
    CRYPTOGRAM_MISSING("53"),
    COULD_NOT_PREFORM("55");

    private final String code;

    CardCSCResultCode(String code) {
        this.code = code;
    }

    @JsonCreator
    public static CardCSCResultCode fromCode(String code) throws UnknownStatusException {
        for (CardCSCResultCode cardCSCResultCode : CardCSCResultCode.values()) {
            if (cardCSCResultCode.getCode().equals(code)) {
                return cardCSCResultCode;
            }
        }

        throw new UnknownStatusException(code + " is an unknown card CSC result code!");
    }

    @JsonValue
    public String getCode() {
        return code;
    }

}
