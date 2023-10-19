package com.worldline.sips.api.model.data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NamedWalletResponseCode implements WalletResponseCode {
    /**
     * Successful operation
     */
    SUCCESS("00"),
    /**
     * Invalid Merchant contract
     */
    INVALID_MERCHANT("03"),
    /**
     * Invalid data, verify the request
     */
    INVALID_DATA("12"),
    /**
     * Wallet / payment mean unknown by WL Sips
     */
    UNKNOWN_WALLET("25"),
    FORMAT_ERROR("30"),

    FRAUD_SUSPECTED("34"),
    /**
     * MerchantId not allowed to access this wallet service
     */
    NOT_ALLOWED("40"),
    /**
     * Duplicated wallet / payment mean
     */
    ALREADY_PRESENT("94"),
    /**
     * Temporary problem at the WL Sips server level
     */
    INTERNAL_TEMPORARY_PROBLEM("99");

    private final String code;

    NamedWalletResponseCode(String code) {
        this.code = code;
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

}
