package com.worldline.sips.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.worldline.sips.model.data.RuleResult;
import com.worldline.sips.util.ObjectMapperHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RuleResultListDeserializer extends JsonDeserializer<List<RuleResult>> {

    @Override
    public List<RuleResult> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getText() == null) {
            return Collections.emptyList();
        }
        return ObjectMapperHolder.INSTANCE.get().readerFor(new TypeReference<List<RuleResult>>() {
        }).readValue(p.getText().trim());
    }
}
