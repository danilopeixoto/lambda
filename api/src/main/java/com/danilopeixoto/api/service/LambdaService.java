package com.danilopeixoto.api.service;

import com.danilopeixoto.api.model.ExecutionModel;
import com.danilopeixoto.api.model.ExecutionRequest;
import com.danilopeixoto.api.model.LambdaModel;
import com.danilopeixoto.api.model.StatusType;
import com.danilopeixoto.api.repository.LambdaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  private ObjectMapper objectMapper;

  @Autowired
  private ModelMapper modelMapper;

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
        this.modelMapper.map(lambda, newLambda);
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
        this.objectMapper.missingNode(),
        "",
        StatusType.Ready
      ));
  }
}
