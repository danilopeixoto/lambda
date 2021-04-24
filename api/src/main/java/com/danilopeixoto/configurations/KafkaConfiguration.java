package com.danilopeixoto.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
  @Autowired
  private APIConfiguration configuration;

  @Bean
  public NewTopic topic() {
    return TopicBuilder
      .name(this.configuration.getTopicName())
      .partitions(this.configuration.getTopicPartitions())
      .build();
  }
}
