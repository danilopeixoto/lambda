package com.danilopeixoto.controller;

import com.danilopeixoto.model.ExecutionModel;
import com.danilopeixoto.model.ExecutionRequest;
import com.danilopeixoto.model.ExecutionUpdateRequest;
import com.danilopeixoto.service.ExecutionProducer;
import com.danilopeixoto.service.ExecutionService;
import com.danilopeixoto.service.LambdaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/execution")
public class ExecutionController {
  @Autowired
  private ExecutionService executionService;

  @Autowired
  private LambdaService lambdaService;

  @Autowired
  private ExecutionProducer executionProducer;

  @Autowired
  private ModelMapper mapper;

  @PostMapping
  public Mono<ResponseEntity<ExecutionModel>> create(
    @Valid @RequestBody Mono<ExecutionRequest> executionRequest) {
    return this.lambdaService
      .execute(executionRequest)
      .flatMap(this.executionService::create)
      .flatMap(this.executionProducer::enqueue)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> get(@PathVariable UUID id) {
    return this.executionService
      .get(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<ExecutionModel> find(
    @RequestParam(value = "lambdaID", required = false) UUID lambdaID) {
    return Flux
      .just(lambdaID)
      .filter(Objects::nonNull)
      .flatMap(this.executionService::find)
      .switchIfEmpty(this.executionService.list());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> update(
    @PathVariable UUID id,
    @Valid @RequestBody Mono<ExecutionUpdateRequest> executionUpdateRequest) {
    return executionUpdateRequest
      .map(request -> this.mapper.map(request, ExecutionModel.class))
      .flatMap(execution -> this.executionService.update(id, execution))
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> delete(@PathVariable UUID id) {
    return this.executionService
      .delete(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
