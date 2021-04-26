package com.danilopeixoto.controllers;

import com.danilopeixoto.models.ExecutionModel;
import com.danilopeixoto.models.ExecutionRequest;
import com.danilopeixoto.models.ExecutionUpdateRequest;
import com.danilopeixoto.services.ExecutionProducer;
import com.danilopeixoto.services.ExecutionService;
import com.danilopeixoto.services.LambdaService;
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
  private ModelMapper modelMapper;

  @PostMapping
  public Mono<ResponseEntity<ExecutionModel>> create(
    @Valid @RequestBody Mono<ExecutionRequest> executionRequest) {
    return executionRequest
      .flatMap(this.lambdaService::execute)
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
      .map(request -> this.modelMapper.map(request, ExecutionModel.class))
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
