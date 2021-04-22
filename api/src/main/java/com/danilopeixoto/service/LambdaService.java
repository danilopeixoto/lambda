package com.danilopeixoto.service;

import com.danilopeixoto.model.ExecutionModel;
import com.danilopeixoto.model.ExecutionRequest;
import com.danilopeixoto.model.LambdaModel;
import com.danilopeixoto.model.StatusType;
import com.danilopeixoto.repository.LambdaRepository;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class LambdaService {
  @Autowired
  private LambdaRepository repository;

  public Mono<LambdaModel> create(final LambdaModel lambda) {
    return this.repository.save(lambda);
  }

  public Mono<LambdaModel> get(final UUID id) {
    return this.repository.findById(id);
  }

  public Flux<LambdaModel> find(final String name) {
    return this.repository.findByName(name);
  }

  public Flux<LambdaModel> list() {
    return this.repository.findAll();
  }

  @Transactional
  public Mono<LambdaModel> delete(final UUID id) {
    return this.repository
      .findById(id)
      .flatMap(lambda -> this.repository
        .deleteById(id)
        .then(Mono.just(lambda)));
  }

  public Mono<ExecutionModel> execute(final UUID id, final Mono<ExecutionRequest> executionRequest) {
    return this.repository
      .existsById(id)
      .flatMap(hasLambda -> hasLambda ?
        executionRequest.map(request -> new ExecutionModel(
          id,
          request.getArguments(),
          Json.of("null"),
          "",
          StatusType.Ready
        )) :
        Mono.empty());
  }
}
