package com.danilopeixoto.api.controller;

import com.danilopeixoto.api.model.LambdaModel;
import com.danilopeixoto.api.model.LambdaRequest;
import com.danilopeixoto.api.service.LambdaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Validated
@RequestMapping("/lambda")
@RestController
public class LambdaController {
  @Autowired
  private LambdaService service;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public Mono<LambdaModel> create(@Valid @RequestBody final Mono<LambdaRequest> lambdaRequest) {
    return lambdaRequest
      .map(request -> this.modelMapper.map(request, LambdaModel.class))
      .flatMap(this.service::create);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> get(@PathVariable final UUID id) {
    return this.service
      .get(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<LambdaModel> find(@RequestParam(value = "name", required = false) final String name) {
    return Flux
      .just(Optional.ofNullable(name))
      .flatMap(lambdaName -> Mono.fromCallable(lambdaName::orElseThrow))
      .flatMap(this.service::find)
      .onErrorResume(
        NoSuchElementException.class,
        exception -> this.service.list());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> update(
    @PathVariable final UUID id,
    @Valid @RequestBody final Mono<LambdaRequest> lambdaUpdateRequest) {
    return lambdaUpdateRequest
      .map(request -> this.modelMapper.map(request, LambdaModel.class))
      .flatMap(lambda -> this.service.update(id, lambda))
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> delete(@PathVariable final UUID id) {
    return this.service
      .delete(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
