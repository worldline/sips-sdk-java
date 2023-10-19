package com.worldline.sips.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum HolderAuthentMethod {
    DYNAMIC,
    NO_AUTHENT,
    NO_AUTHENT_METHOD,
    NOT_SPECIFIED,
    OOB,
    OTP_HARDWARE,
    OTP_SOFTWARE,
    OTP_TELE,
    PASSWORD,
    STATIC;

    @JsonCreator
    public static HolderAuthentMethod fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return NOT_SPECIFIED;
        }

        return HolderAuthentMethod.valueOf(value);
    }
}
