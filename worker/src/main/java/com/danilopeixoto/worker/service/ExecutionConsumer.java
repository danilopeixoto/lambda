package com.danilopeixoto.worker.service;

import com.danilopeixoto.worker.model.ExecutionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ExecutionConsumer {
  @Autowired
  private ReactiveKafkaConsumerTemplate<String, ExecutionModel> template;

  public Flux<ExecutionModel> dequeue() {
    return this.template
      .receive()
      .map(result -> result.value());
  }
}
