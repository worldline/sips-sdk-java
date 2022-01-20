package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum ScoreColor {
    BLACK,
    EMPTY,
    GREEN,
    ORANGE,
    RED,
    WHITE;

    @JsonCreator
    public static ScoreColor fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return ScoreColor.EMPTY;
        }

        return ScoreColor.valueOf(value);
    }
}
