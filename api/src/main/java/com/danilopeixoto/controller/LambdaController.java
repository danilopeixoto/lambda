package com.danilopeixoto.controller;

import com.danilopeixoto.model.ExecutionModel;
import com.danilopeixoto.model.ExecutionRequest;
import com.danilopeixoto.model.LambdaModel;
import com.danilopeixoto.model.LambdaRequest;
import com.danilopeixoto.service.ExecutionService;
import com.danilopeixoto.service.LambdaService;
import org.modelmapper.ModelMapper;
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
  private LambdaService lambdaService;

  @Autowired
  private ExecutionService executionService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public Mono<LambdaModel> create(@RequestBody Mono<LambdaRequest> lambdaRequest) {
    return lambdaRequest
      .map(request -> this.modelMapper.map(request, LambdaModel.class))
      .flatMap(this.lambdaService::create);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> get(@PathVariable UUID id) {
    return this.lambdaService
      .get(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<LambdaModel> find(@RequestParam(value = "name", required = false) String name) {
    return name != null ?
      this.lambdaService.find(name) :
      this.lambdaService.list();
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> delete(@PathVariable UUID id) {
    return this.lambdaService
      .delete(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/{id}/execution")
  public Mono<ResponseEntity<ExecutionModel>> execute(
    @PathVariable UUID id,
    @RequestBody Mono<ExecutionRequest> executionRequest) {
    return this.lambdaService
      .execute(id, executionRequest)
      .flatMap(execution -> this.executionService.create(execution))
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
