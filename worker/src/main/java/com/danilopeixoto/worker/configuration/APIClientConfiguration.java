package com.danilopeixoto.worker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class APIClientConfiguration {
  @Autowired
  private APIConfiguration configuration;

  @Bean
  public WebClient client() {
    String baseURL = UriComponentsBuilder
      .newInstance()
      .scheme("http")
      .host(this.configuration.getHostname())
      .port(this.configuration.getPort())
      .path("/api/{version}")
      .buildAndExpand(this.configuration.getVersion())
      .toUriString();

    return WebClient
      .builder()
      .baseUrl(baseURL)
      .build();
  }
}
