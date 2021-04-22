package com.danilopeixoto.controller;

import com.danilopeixoto.model.ExecutionModel;
import com.danilopeixoto.model.ExecutionUpdateRequest;
import com.danilopeixoto.service.ExecutionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/execution")
public class ExecutionController {
  @Autowired
  private ExecutionService service;

  @Autowired
  private ModelMapper mapper;

  @GetMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> get(@PathVariable UUID id) {
    return this.service
      .get(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<ExecutionModel> find(@RequestParam(value = "lambdaID", required = false) UUID lambdaID) {
    return lambdaID != null ?
      this.service.find(lambdaID) :
      this.service.list();
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> update(
    @PathVariable UUID id,
    @Valid @RequestBody Mono<ExecutionUpdateRequest> executionUpdateRequest) {
    return executionUpdateRequest
      .map(request -> this.mapper.map(request, ExecutionModel.class))
      .flatMap(execution -> this.service.update(id, execution))
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> delete(@PathVariable UUID id) {
    return this.service
      .delete(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
