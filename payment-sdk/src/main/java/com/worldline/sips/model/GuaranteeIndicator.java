package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum GuaranteeIndicator {
    Y, N, U, EMPTY;

    @JsonCreator
    public static GuaranteeIndicator fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return GuaranteeIndicator.EMPTY;
        }

        return GuaranteeIndicator.valueOf(value);
    }
}
