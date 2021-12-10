package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.worldline.sips.exception.UnsupportedLanguageException;

public enum Language {
    BULGARIAN("bg"),
    BRETON("br"),
    CZECH("cs"),
    DANISH("da"),
    GERMAN("de"),
    GREEK("el"),
    ENGLISH("en"),
    SPANISH("es"),
    ESTONIAN("et"),
    FINNISH("fi"),
    FRENCH("fr"),
    HINDI("hi"),
    CROATIAN("hr"),
    HUNGARIAN("hu"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KOREAN("ko"),
    LITHUANIAN("lt"),
    LATVIAN("lv"),
    DUTCH("nl"),
    NORWEGIAN("no"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SLOVAK("sk"),
    SLOVENE("sl"),
    SWEDISH("sv"),
    TURKISH("tr"),
    UKRAINIAN("uk"),
    CHINESE("zh");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    @JsonCreator
    public static Language fromCode(String code) throws UnsupportedLanguageException {
        for (Language currency : Language.values()) {
            if (currency.getCode().equals(code)) {
                return currency;
            }
        }

        throw new UnsupportedLanguageException("Language code " + code + " is not supported!");
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
