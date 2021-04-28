package com.danilopeixoto.api.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public
class JsonNodeReadingConverter implements Converter<Json, JsonNode> {
  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public JsonNode convert(Json source) {
    try {
      return this.objectMapper.readTree(source.toString());
    } catch (JsonProcessingException exception) {
      throw new IllegalArgumentException(exception.getMessage());
    }
  }
}
