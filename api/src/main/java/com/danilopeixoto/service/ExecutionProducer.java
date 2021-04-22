package com.danilopeixoto.service;

import com.danilopeixoto.configuration.APIConfiguration;
import com.danilopeixoto.model.ExecutionModel;
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

  public Mono<ExecutionModel> enqueue(ExecutionModel execution) {
    return this.template
      .send(this.configuration.getJavaWorkerTopicName(), execution)
      .thenReturn(execution);
  }
}
