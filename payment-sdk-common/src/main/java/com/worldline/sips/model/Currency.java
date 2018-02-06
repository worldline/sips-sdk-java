package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.worldline.sips.exception.UnsupportedCurrencyException;

public enum Currency {
    ARS("032", "Argentinean Peso"),
    AUD("036", "Australian Dollar"),
    BHD("048", "Bahrain Dinar"),
    KHR("116", "Cambodian Riel"),
    CAD("124", "Canadian Dollar"),
    LKR("144", "Sri Lanka Rupee"),
    CNY("156", "China Yuan Renminbi"),
    HRK("191", "Croatia Kuna"),
    CZK("203", "Czech Republic Koruna"),
    DKK("208", "Danes crown"),
    HKD("344", "Hong Kong dollar"),
    HUF("348", "Hungary Forint"),
    ISK("352", "Iceland Rupee"),
    INR("356", "Indian rupee"),
    ILS("376", "Israel Shekel"),
    JPY("392", "Japanese Yen"),
    KRW("410", "South Korean Won"),
    KWD("414", "Kuwait Dinar"),
    MYR("458", "Malaysia Ringgit"),
    MUR("480", "Mauritius Rupee"),
    MXN("484", "Mexican Peso"),
    NPR("524", "Nepal Rupee"),
    NZD("554", "New Zealand Dollar"),
    NOK("578", "Norwegian crown"),
    QAR("634", "Qatar Riyal"),
    RUB("643", "Russia Ruble"),
    SAR("682", "Saudi Arabia Riyal"),
    SGD("702", "Singapore Dollar"),
    ZAR("710", "South Africa Rand"),
    SEK("752", "Swedish crown"),
    CHF("756", "Swiss Franc"),
    THB("764", "Thailand Baht"),
    AED("784", "United Arab Emirates Dirham"),
    TND("788", "Tunisia Dinar"),
    GBP("826", "Pound"),
    USD("840", "American Dollar"),
    TWD("901", "Taiwan Dollar"),
    RON("946", "Roumania New Leu"),
    TRY("949", "New Turkish Lira"),
    XOF("952", "CFA Franc"),
    XPF("953", "CFP Franc"),
    BGN("975", "Bulgaria Lev"),
    EUR("978", "Euro"),
    UAH("980", "Ukraine Hryvnia"),
    PLN("985", "Poland Zloty"),
    BRL("986", "Brazilian Real");

    private String code;
    private String description;

    Currency(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static Currency fromCode(String code) throws UnsupportedCurrencyException {
        for (Currency currency : Currency.values()) {
            if (currency.getCode().equals(code)) {
                return currency;
            }
        }

        throw new UnsupportedCurrencyException("Currency code " + code + " is not supported!");
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code;
    }
}
