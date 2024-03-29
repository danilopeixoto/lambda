package com.danilopeixoto.api.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class JsonNodeReadingConverter implements Converter<Json, JsonNode> {
  private final ObjectMapper jsonMapper;

  public JsonNodeReadingConverter() {
    this.jsonMapper = new ObjectMapper();
  }

  @Override
  public JsonNode convert(Json source) {
    try {
      return this.jsonMapper.readTree(source.asString());
    } catch (JsonProcessingException exception) {
      throw new IllegalArgumentException(exception.getMessage());
    }
  }
}
