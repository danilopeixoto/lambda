package com.danilopeixoto.api;

import com.danilopeixoto.configuration.APIConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

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
@SpringBootApplication
public class APIApplication {
  @Autowired
  private APIConfiguration configuration;

  public static void main(String[] args) {
    SpringApplication.run(APIApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    modelMapper
      .getConfiguration()
      .setPropertyCondition(Conditions.isNotNull());

    return modelMapper;
  }

  @Bean
  public NewTopic javaWorkerTopic() {
    return TopicBuilder
      .name(this.configuration.getJavaWorkerTopicName())
      .partitions(this.configuration.getPartitionsPerTopic())
      .build();
  }

  @Bean
  public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);

    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
    initializer.setDatabasePopulator(populator);

    return initializer;
  }
}
