package com.danilopeixoto.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
  @Autowired
  private APIConfiguration apiConfiguration;

  @Bean
  public NewTopic javaWorkerTopic() {
    return TopicBuilder
      .name(this.apiConfiguration.getJavaWorkerTopicName())
      .partitions(this.apiConfiguration.getPartitionsPerTopic())
      .build();
  }
}
