package com.worldline.sips.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum HolderAuthentStatus {
    ATTEMPT,
    THREE_D_ATTEMPT,
    BYPASS,
    THREE_D_BYPASS,
    CANCEL,
    THREE_D_ABORT,
    ERROR,
    THREE_D_ERROR,
    FAILURE,
    THREE_D_FAILURE,
    NO_AUTHENT,
    SSL,
    NOT_ENROLLED,
    THREE_D_NOTENROLLED,
    NOT_PARTICIPATING,
    NOT_SPECIFIED,
    SUCCESS,
    THREE_D_SUCCESS;

    @JsonCreator
    public static HolderAuthentStatus fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return NOT_SPECIFIED;
        }

        if (value.startsWith("3D")) {
            value = value.replace("3D", "THREE_D");
        }

        return HolderAuthentStatus.valueOf(value);
    }
}
