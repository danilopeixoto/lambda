package com.danilopeixoto.controller;

import com.danilopeixoto.model.ExecutionModel;
import com.danilopeixoto.model.ExecutionRequest;
import com.danilopeixoto.model.LambdaModel;
import com.danilopeixoto.model.LambdaRequest;
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
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/lambda")
public class LambdaController {
  @Autowired
  private LambdaService lambdaService;

  @Autowired
  private ExecutionService executionService;

  @Autowired
  private ExecutionProducer executionProducer;

  @Autowired
  private ModelMapper mapper;

  @PostMapping
  public Mono<LambdaModel> create(@Valid @RequestBody Mono<LambdaRequest> lambdaRequest) {
    return lambdaRequest
      .map(request -> this.mapper.map(request, LambdaModel.class))
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

  @PutMapping("/{id}")
  public Mono<ResponseEntity<LambdaModel>> update(
    @PathVariable UUID id,
    @Valid @RequestBody Mono<LambdaRequest> lambdaUpdateRequest) {
    return lambdaUpdateRequest
      .map(request -> this.mapper.map(request, LambdaModel.class))
      .flatMap(lambda -> this.lambdaService.update(id, lambda))
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
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
    @Valid @RequestBody Mono<ExecutionRequest> executionRequest) {
    return this.lambdaService
      .execute(id, executionRequest)
      .flatMap(this.executionService::create)
      .flatMap(this.executionProducer::enqueue)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
