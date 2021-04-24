package com.danilopeixoto.services;

import com.danilopeixoto.configurations.APIConfiguration;
import com.danilopeixoto.models.ExecutionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExecutionProducer {
  @Autowired
  private APIConfiguration configuration;

  @Autowired
  private ReactiveKafkaProducerTemplate<String, ExecutionModel> template;

  public Mono<ExecutionModel> enqueue(final ExecutionModel execution) {
    return this.template
      .send(this.configuration.getTopicName(), execution)
      .thenReturn(execution);
  }
}
