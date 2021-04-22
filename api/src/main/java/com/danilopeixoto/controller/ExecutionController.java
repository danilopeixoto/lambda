package com.danilopeixoto.controller;

import com.danilopeixoto.model.ExecutionModel;
import com.danilopeixoto.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/execution")
public class ExecutionController {
  @Autowired
  private ExecutionService service;

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

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<ExecutionModel>> delete(@PathVariable UUID id) {
    return this.service
      .delete(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
