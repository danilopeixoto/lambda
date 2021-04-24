package com.danilopeixoto.service;

import com.danilopeixoto.model.ExecutionModel;
import com.danilopeixoto.model.ExecutionUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ExecutionService {
  @Autowired
  private WebClient client;

  public Mono<ExecutionModel> get(final UUID id) {
    return this.client
      .get()
      .uri("/execution/" + id.toString())
      .retrieve()
      .bodyToMono(ExecutionModel.class);
  }

  public Mono<ExecutionModel> update(
    final UUID id,
    final ExecutionUpdateRequest executionUpdateRequest) {
    return this.client
      .put()
      .uri("/execution/" + id)
      .body(Mono.just(executionUpdateRequest), ExecutionUpdateRequest.class)
      .retrieve()
      .bodyToMono(ExecutionModel.class);
  }
}
