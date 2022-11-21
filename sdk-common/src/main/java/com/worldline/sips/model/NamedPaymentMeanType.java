package com.worldline.sips.model;

public enum NamedPaymentMeanType implements PaymentMeanType {
    CARD, CREDIT_TRANSFER, DIRECT_DEBIT,
    VOUCHER, WALLET, ONLINE_CREDIT, EMPTY, PROVIDER;

    @Override
    public String getName() {
        return name();
    }
}
