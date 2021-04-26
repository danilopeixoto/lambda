package com.danilopeixoto.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class MapperConfiguration {
  @Bean
  public ObjectMapper jsonMapper(Jackson2ObjectMapperBuilder builder) {
    return builder.build();
  }
}
