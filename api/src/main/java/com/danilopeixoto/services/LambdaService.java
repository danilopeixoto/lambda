package com.danilopeixoto.services;

import com.danilopeixoto.models.ExecutionModel;
import com.danilopeixoto.models.ExecutionRequest;
import com.danilopeixoto.models.LambdaModel;
import com.danilopeixoto.models.StatusType;
import com.danilopeixoto.repositories.LambdaRepository;
import io.r2dbc.postgresql.codec.Json;
import org.modelmapper.ModelMapper;
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

  @Autowired
  private ModelMapper mapper;

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
  public Mono<LambdaModel> update(final UUID id, final LambdaModel lambda) {
    return this.repository
      .findById(id)
      .flatMap(newLambda -> {
        this.mapper.map(lambda, newLambda);
        return this.repository.save(newLambda);
      });
  }

  @Transactional
  public Mono<LambdaModel> delete(final UUID id) {
    return this.repository
      .findById(id)
      .flatMap(lambda -> this.repository
        .deleteById(id)
        .then(Mono.just(lambda)));
  }

  public Mono<ExecutionModel> execute(final ExecutionRequest executionRequest) {
    return this.repository
      .findByName(executionRequest.getLambdaName())
      .singleOrEmpty()
      .zipWith(Mono.just(executionRequest))
      .map(result -> new ExecutionModel(
        result.getT1().getID(),
        result.getT2().getArguments(),
        Json.of(""),
        "",
        StatusType.Ready
      ));
  }
}
