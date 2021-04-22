package com.danilopeixoto.service;

import com.danilopeixoto.model.ExecutionModel;
import com.danilopeixoto.repository.ExecutionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ExecutionService {
  @Autowired
  private ExecutionRepository repository;

  @Autowired
  private ModelMapper mapper;

  public Mono<ExecutionModel> create(final ExecutionModel execution) {
    return this.repository.save(execution);
  }

  public Mono<ExecutionModel> get(final UUID id) {
    return this.repository.findById(id);
  }

  public Flux<ExecutionModel> find(final UUID lambdaID) {
    return this.repository.findByLambdaID(lambdaID);
  }

  public Flux<ExecutionModel> list() {
    return this.repository.findAll();
  }

  @Transactional
  public Mono<ExecutionModel> update(final UUID id, final ExecutionModel execution) {
    return this.repository
      .findById(id)
      .flatMap(newExecution -> {
        this.mapper.map(execution, newExecution);
        return this.repository.save(newExecution);
      });
  }

  @Transactional
  public Mono<ExecutionModel> delete(final UUID id) {
    return this.repository
      .findById(id)
      .flatMap(lambda -> this.repository
        .deleteById(id)
        .thenReturn(lambda));
  }
}
