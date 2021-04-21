package com.danilopeixoto.service;

import com.danilopeixoto.model.LambdaModel;
import com.danilopeixoto.repository.LambdaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

  public Mono<LambdaModel> delete(final UUID id) {
    return this.repository
      .findById(id)
      .flatMap(lambda -> this.repository
        .deleteById(id)
        .then(Mono.just(lambda)));
  }
}
