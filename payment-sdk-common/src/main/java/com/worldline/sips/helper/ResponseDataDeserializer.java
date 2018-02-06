package com.worldline.sips.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.worldline.sips.model.ResponseData;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseDataDeserializer extends JsonDeserializer<ResponseData> {


    private final ObjectMapper objectMapper;

    public ResponseDataDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ResponseData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getText() == null) {
            return null;
        }
        final String value = jsonParser.getText().trim();
        final Map<String, String> mapped = Arrays.stream(value.split("\\|"))
                .map(element -> element.split("=", 2))
                .filter(pair -> isNotNullOrEmpty(pair[1]))
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));


        return objectMapper
                .registerModule(new JavaTimeModule()
                        .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                        .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.BASIC_ISO_DATE))
                        .addDeserializer(YearMonth.class, new YearMonthDeserializer(DateTimeFormatter.ofPattern("yyyyMM"))))
                .convertValue(mapped, ResponseData.class);

    }

    private boolean isNotNullOrEmpty(final CharSequence cs) {
        return !StringUtils.isBlank(cs) && !StringUtils.equals(cs, "null");
    }

}
