package com.danilopeixoto.configuration;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
  @Bean
  public ModelMapper mapper() {
    ModelMapper mapper = new ModelMapper();

    mapper
      .getConfiguration()
      .setPropertyCondition(Conditions.isNotNull());

    return mapper;
  }
}
