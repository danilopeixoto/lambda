package com.danilopeixoto.api.controller;

import com.danilopeixoto.api.model.ExecutionModel;
import com.danilopeixoto.api.model.ExecutionRequest;
import com.danilopeixoto.api.model.ExecutionUpdateRequest;
import com.danilopeixoto.api.service.ExecutionProducer;
import com.danilopeixoto.api.service.ExecutionService;
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
@RequestMapping("/execution")
@RestController
public class ExecutionController {
  @Autowired
  private ExecutionService executionService;

  @Autowired
  private LambdaService lambdaService;

  @Autowired
  private ExecutionProducer executionProducer;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public Mono<ResponseEntity<ExecutionModel>> create(
    @Valid @RequestBody final Mono<ExecutionRequest> executionRequest) {
    return executionRequest
      .flatMap(this.lambdaService::execute)
      .flatMap(this.executionService::create)
      .flatMap(this.executionProducer::enqueue)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> get(@PathVariable final UUID id) {
    return this.executionService
      .get(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<ExecutionModel> find(
    @RequestParam(value = "lambdaID", required = false) final UUID lambdaID,
    @RequestParam(value = "lambdaName", required = false) final String lambdaName) {
    return Flux
      .just(Optional.ofNullable(lambdaID))
      .filter(Optional::isPresent)
      .map(Optional::get)
      .switchIfEmpty(Flux
        .just(Optional.ofNullable(lambdaName))
        .flatMap(name -> Mono.fromCallable(name::get))
        .flatMap(this.lambdaService::find)
        .flatMap(lambda -> Mono.just(lambda.getID())))
      .flatMap(this.executionService::find)
      .onErrorResume(
        NoSuchElementException.class,
        exception -> this.executionService.list());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> update(
    @PathVariable final UUID id,
    @Valid @RequestBody final Mono<ExecutionUpdateRequest> executionUpdateRequest) {
    return executionUpdateRequest
      .map(request -> this.modelMapper.map(request, ExecutionModel.class))
      .flatMap(execution -> this.executionService.update(id, execution))
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> delete(@PathVariable final UUID id) {
    return this.executionService
      .delete(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
