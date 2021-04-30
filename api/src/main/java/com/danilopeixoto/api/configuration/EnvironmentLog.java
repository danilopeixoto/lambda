package com.danilopeixoto.api.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class EnvironmentLog {
  @Autowired
  private AbstractEnvironment environment;

  private final Logger logger;

  public EnvironmentLog() {
    this.logger = LoggerFactory.getLogger(EnvironmentLog.class);
  }

  @PostConstruct
  public void logProperties() {
    this.environment
      .getPropertySources()
      .stream()
      .filter(EnumerablePropertySource.class::isInstance)
      .map(EnumerablePropertySource.class::cast)
      .map(EnumerablePropertySource::getPropertyNames)
      .flatMap(Arrays::stream)
      .forEach(name -> this.logger.info(
        "{}: {}",
        name,
        this.environment.getRequiredProperty(name)));
  }
}
