package com.danilopeixoto.controller;

import com.danilopeixoto.model.LambdaModel;
import com.danilopeixoto.service.LambdaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/lambda")
public class LambdaController {
  @Autowired
  private LambdaService service;

  @PostMapping
  public Mono<LambdaModel> create(@RequestBody Mono<LambdaModel> execution) {
    return execution.flatMap(this.service::create);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> get(@PathVariable UUID id) {
    return this.service.get(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<LambdaModel> find(@RequestParam(value = "name", required = false) String name) {
    return name != null ? this.service.find(name) : this.service.list();
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> delete(@PathVariable UUID id) {
    return this.service.delete(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
