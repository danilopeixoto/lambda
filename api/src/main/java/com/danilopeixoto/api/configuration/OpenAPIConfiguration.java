package com.danilopeixoto.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
  info = @Info(
    title = "Lambda API",
    description = "A distributed Function-as-a-Service (FaaS) platform.",
    contact = @Contact(
      name = "Danilo Peixoto",
      email = "danilopeixoto@outlook.com"),
    license = @License(
      name = "BSD-3-Clause",
      url = "https://github.com/danilopeixoto/lambda")))
public class OpenAPIConfiguration {
}
