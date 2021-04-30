package com.danilopeixoto.api.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
  @Autowired
  private APIConfiguration configuration;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
      .components(new Components()
        .addSecuritySchemes("basicScheme", new SecurityScheme()
          .type(SecurityScheme.Type.HTTP)
          .scheme("basic")))
      .info(new Info()
        .title("Lambda API")
        .description("A distributed Function-as-a-Service (FaaS) service.")
        .version(this.configuration.getVersion())
        .contact(new Contact()
          .name("Danilo Peixoto")
          .email("danilopeixoto@outlook.com"))
        .license(new License()
          .name("BSD-3-Clause")
          .url("https://github.com/danilopeixoto/lambda")));
  }
}
