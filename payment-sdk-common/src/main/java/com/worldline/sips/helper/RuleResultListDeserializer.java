package com.worldline.sips.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldline.sips.model.RuleResult;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RuleResultListDeserializer extends JsonDeserializer<List<RuleResult>> {

    private final ObjectMapper objectMapper;

    public RuleResultListDeserializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<RuleResult> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getText() == null) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(p.getText().trim(), new TypeReference<List<RuleResult>>() {
        });
    }
}
