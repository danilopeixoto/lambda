package com.danilopeixoto.controllers;

import com.danilopeixoto.models.LambdaModel;
import com.danilopeixoto.models.LambdaRequest;
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
@RequestMapping("/lambda")
public class LambdaController {
  @Autowired
  private LambdaService service;

  @Autowired
  private ModelMapper mapper;

  @PostMapping
  public Mono<LambdaModel> create(@Valid @RequestBody Mono<LambdaRequest> lambdaRequest) {
    return lambdaRequest
      .map(request -> this.mapper.map(request, LambdaModel.class))
      .flatMap(this.service::create);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> get(@PathVariable UUID id) {
    return this.service
      .get(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<LambdaModel> find(@RequestParam(value = "name", required = false) String name) {
    return Flux
      .just(name)
      .filter(Objects::nonNull)
      .flatMap(this.service::find)
      .switchIfEmpty(this.service.list());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> update(
    @PathVariable UUID id,
    @Valid @RequestBody Mono<LambdaRequest> lambdaUpdateRequest) {
    return lambdaUpdateRequest
      .map(request -> this.mapper.map(request, LambdaModel.class))
      .flatMap(lambda -> this.service.update(id, lambda))
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> delete(@PathVariable UUID id) {
    return this.service
      .delete(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
