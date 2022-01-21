package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum PaymentMeanType {
    CARD, CREDIT_TRANSFER, DIRECT_DEBIT,
  VOUCHER, WALLET, ONLINE_CREDIT, EMPTY, PROVIDER;

    @JsonCreator
    public static PaymentMeanType fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return PaymentMeanType.EMPTY;
        }

        return PaymentMeanType.valueOf(value);
    }

}
