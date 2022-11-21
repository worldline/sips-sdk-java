package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public interface PaymentMeanBrand {

    @JsonValue
    String getRealName();
  
    @JsonCreator
    static PaymentMeanBrand fromRealName(String string) {
        if (string == null) return null;
        for (NamedPaymentMeanBrand responseCode : NamedPaymentMeanBrand.values()) {
            if (responseCode.getRealName().equals(string)) {
                return responseCode;
            }
        }
    
        return new UnknownPaymentMeanBrand(string);
    }
}
