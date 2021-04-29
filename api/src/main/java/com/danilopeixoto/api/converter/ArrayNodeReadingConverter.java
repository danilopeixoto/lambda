package com.danilopeixoto.api.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ArrayNodeReadingConverter implements Converter<Json, ArrayNode> {
  private final ObjectMapper jsonMapper;

  public ArrayNodeReadingConverter() {
    this.jsonMapper = new ObjectMapper();
  }

  @Override
  public ArrayNode convert(Json source) {
    try {
      return (ArrayNode) this.jsonMapper.readTree(source.asString());
    } catch (JsonProcessingException exception) {
      throw new IllegalArgumentException(exception.getMessage());
    }
  }
}
