package com.worldline.sips.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum HolderAuthentProgram {
    ONE_EUROCOM,
    THREE_DS,
    THREE_DS_V2,
    APPLEPAY,
    ARP,
    BCMCMOBILE,
    MASTERPASS,
    NO_AUTHENT,
    NO_AUTHENT_METHOD,
    NOT_SPECIFIED,
    PAYLIB;

    @JsonCreator
    public static HolderAuthentProgram fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return NO_AUTHENT;
        }

        if (value.startsWith("3")) {
            value = value.replace("3", "THREE_");
        }

        if (value.startsWith("1")) {
            value = value.replace("1", "ONE_");
        }

        return HolderAuthentProgram.valueOf(value);
    }
}
