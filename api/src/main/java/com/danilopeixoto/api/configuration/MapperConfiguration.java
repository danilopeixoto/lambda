package com.danilopeixoto.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class MapperConfiguration {
  @Bean
  public ObjectMapper jsonMapper(Jackson2ObjectMapperBuilder builder) {
    return builder.build();
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();

    mapper
      .getConfiguration()
      .setPropertyCondition(Conditions.isNotNull());

    return mapper;
  }
}
