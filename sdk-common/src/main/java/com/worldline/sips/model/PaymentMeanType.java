package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public interface PaymentMeanType {
    String getName();
  
  
    @JsonCreator
    static PaymentMeanType fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return NamedPaymentMeanType.EMPTY;
        }
        try {
            return NamedPaymentMeanType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return new UnknownPaymentMeanType(value);
        }
    }
}
