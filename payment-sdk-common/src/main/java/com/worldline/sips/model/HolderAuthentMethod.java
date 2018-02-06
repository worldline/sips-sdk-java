package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum HolderAuthentMethod {
    NO_AUTHENT,
    NO_AUTHENT_METHOD,
    NOT_SPECIFIED,
    OTP_HARDWARE,
    OTP_SOFTWARE,
    OTP_TELE,
    PASSWORD;

    @JsonCreator
    public static HolderAuthentMethod fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return NOT_SPECIFIED;
        }

        return HolderAuthentMethod.valueOf(value);
    }
}
