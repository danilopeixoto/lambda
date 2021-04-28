package com.danilopeixoto.api.configuration;

import com.danilopeixoto.api.model.ExecutionModel;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class KafkaConfiguration {
  @Autowired
  private APIConfiguration configuration;

  @Primary
  @Bean
  public KafkaProperties kafkaProperties() {
    return new KafkaProperties();
  }

  @Bean
  ReactiveKafkaProducerTemplate<String, ExecutionModel> template() {
    KafkaProperties properties = this.kafkaProperties();
    Map<String, Object> producerProperties = properties.buildProducerProperties();

    return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(producerProperties));
  }

  @Bean
  public NewTopic topic() {
    return TopicBuilder
      .name(this.configuration.getTopicName())
      .partitions(this.configuration.getTopicPartitions())
      .build();
  }
}
