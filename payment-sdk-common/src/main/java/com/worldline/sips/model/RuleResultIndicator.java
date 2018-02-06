package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RuleResultIndicator {
    B, D, E, N, P, U, X, ZERO;

    @JsonCreator
    public static RuleResultIndicator fromValue(String value) {
        if ("0".equals(value)) {
            return ZERO;
        }

        return RuleResultIndicator.valueOf(value);
    }
}
