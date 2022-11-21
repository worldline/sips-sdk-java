package com.worldline.sips.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Container for the global mapper object instance
 *
 * @see ObjectMapper
 */
public enum ObjectMapperHolder {
    INSTANCE;

    private final ObjectMapper mapper;

    ObjectMapperHolder() {
        this.mapper = create();
    }

    private static ObjectMapper create() {
        return new ObjectMapper()
            .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
            .registerModule(new JavaTimeModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.BASIC_ISO_DATE))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.BASIC_ISO_DATE))
                .addDeserializer(YearMonth.class, new YearMonthDeserializer(DateTimeFormatter.ofPattern("yyyyMM")))
                .addSerializer(YearMonth.class, new YearMonthSerializer(DateTimeFormatter.ofPattern("yyyyMM"))));
    }

    public ObjectMapper get() {
        return this.mapper;
    }
}

