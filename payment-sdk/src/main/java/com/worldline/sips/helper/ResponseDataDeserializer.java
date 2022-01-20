package com.worldline.sips.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.worldline.sips.model.data.ResponseData;
import com.worldline.sips.util.ObjectMapperHolder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseDataDeserializer extends JsonDeserializer<ResponseData> {

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


        return ObjectMapperHolder.INSTANCE.get().copy()
                .convertValue(mapped, ResponseData.class);

    }

    private boolean isNotNullOrEmpty(final CharSequence cs) {
        return !StringUtils.isBlank(cs) && !StringUtils.equals(cs, "null");
    }

}
