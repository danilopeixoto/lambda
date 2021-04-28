package com.danilopeixoto.worker.configuration;

import com.danilopeixoto.worker.model.ExecutionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;
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
  ReactiveKafkaConsumerTemplate<String, ExecutionModel> template() {
    KafkaProperties properties = this.kafkaProperties();
    Map<String, Object> consumerProperties = properties.buildConsumerProperties();

    return new ReactiveKafkaConsumerTemplate<>(ReceiverOptions
      .<String, ExecutionModel>create(consumerProperties)
      .subscription(List.of(this.configuration.getTopicName())));
  }
}
