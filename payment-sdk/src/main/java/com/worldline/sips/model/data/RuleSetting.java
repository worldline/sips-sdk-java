package com.worldline.sips.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum RuleSetting {
    D, I, N, S;

    @JsonCreator
    public static RuleSetting fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return N;
        }

        return RuleSetting.valueOf(value);
    }
}
