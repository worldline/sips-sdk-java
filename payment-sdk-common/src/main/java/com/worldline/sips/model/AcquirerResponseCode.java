package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.worldline.sips.exception.UnknownStatusException;

import java.util.Arrays;

public enum AcquirerResponseCode {
    THREED_AUTHENTICATION_DATA_MISSING("A1"),
    TRANSACTION_SUCCESS("00"),
    CONTACT_ISSUER("02"),
    INVALID_ACCEPTOR("03"),
    KEEP_PAYMENT_MEAN("04"),
    DO_NOT_HONOUR("05"),
    KEEP_PAYMENT_MEAN_UNDER_CONDITION("07"),
    APPROVE_AFTER_IDENTIFICATION("08"),
    INVALID_TRANSACTION("12"),
    INVALID_AMOUNT("13"),
    INVALID_PAN("14"),
    UNKNOWN_ISSUER("15"),
    CUSTOMER_CANCELLATION("17"),
    NOT_AUTHORIZED("24"),
    TRANSACTION_NOT_FOUND("25"),
    INVALID_FORMAT("30"),
    UNKNOWN_ACQUIRER_ID("31"),
    PAYMENT_MEAN_EXPIRED("33", "54"),
    FRAUD_SUSPECTED("34", "59"),
    NOT_SUPPORTED("40"),
    PAYMENT_MEAN_LOST("41"),
    PAYMENT_MEAN_STOLEN("43"),
    CREDIT_UNAVAILABLE("51"),
    //PAYMENT_MEAN_EXPIRED("54"),
    PAYMENT_MEAN_MISSING("56"),
    TRANSACTION_UNAUTHORIZED("57"),
    TRANSACTION_FORBIDDEN("58"),
    //FRAUD_SUSPECTED("59"),
    CONTACT_ACQUIRER("60"),
    EXCEEDED_AMOUNT_LIMIT("61"),
    AWAITING_PAYMENT_CONFIRMATION("62"),
    NOT_SECURITY_COMPLIANT("63"),
    DAILY_TRANSACTION_LIMIT_EXCEEDED("65"),
    MISSING_RESPONSE("68"),
    MAX_ATTEMPTS_REACHED("75"),
    TERMINAL_UNKNOWN("87"),
    SYSTEM_STOPPED("90"),
    ISSUER_INACCESSIBLE("91"),
    INCOMPLETE_TRANSACTION_INFO("92"),
    DUPLICATE_TRANSACTION("94"),
    SYSTEM_MALFUNCTION("96"),
    TIMEFRAME_EXCEEDED("97"),
    SERVER_UNAVAILABLE("98"),
    INITIATOR_DOMAIN_INCIDENT("99");

    private String[] codes;

    AcquirerResponseCode(String... codes) {
        this.codes = codes;
    }

    @JsonCreator
    public static AcquirerResponseCode fromCode(String code) throws UnknownStatusException {
        for (AcquirerResponseCode acquirerResponseCode : AcquirerResponseCode.values()) {
            if (Arrays.asList(acquirerResponseCode.getCodes()).contains(code)) {
                return acquirerResponseCode;
            }
        }

        throw new UnknownStatusException(code + " is an unknown acquirer response code!");
    }

    @JsonValue
    public String[] getCodes() {
        return codes;
    }

}
