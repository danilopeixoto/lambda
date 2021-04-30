package com.danilopeixoto.api.controller;

import com.danilopeixoto.api.model.ErrorResponse;
import com.danilopeixoto.api.model.ExecutionModel;
import com.danilopeixoto.api.model.ExecutionRequest;
import com.danilopeixoto.api.model.ExecutionUpdateRequest;
import com.danilopeixoto.api.service.ExecutionProducer;
import com.danilopeixoto.api.service.ExecutionService;
import com.danilopeixoto.api.service.LambdaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Execution")
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

  @Operation(summary = "Execute lambda by ID or name.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = ExecutionModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json"))
  })
  @PostMapping
  public Mono<ExecutionModel> create(
    @Valid @RequestBody final Mono<ExecutionRequest> executionRequest) {
    return executionRequest
      .flatMap(this.lambdaService::execute)
      .flatMap(this.executionService::create)
      .flatMap(this.executionProducer::enqueue)
      .switchIfEmpty(Mono.error(new NoSuchElementException()));
  }

  @Operation(summary = "Get execution by ID.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = ExecutionModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json"))
  })
  @GetMapping("/{id}")
  public Mono<ExecutionModel> get(@PathVariable final UUID id) {
    return this.executionService
      .get(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException()));
  }

  @Operation(summary = "Find lambda executions by lambda ID or name.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        array = @ArraySchema(schema = @Schema(implementation = ExecutionModel.class)),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json"))
  })
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

  @Operation(summary = "Update execution by ID.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = ExecutionModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json"))
  })
  @PutMapping("/{id}")
  public Mono<ExecutionModel> update(
    @PathVariable final UUID id,
    @Valid @RequestBody final Mono<ExecutionUpdateRequest> executionUpdateRequest) {
    return executionUpdateRequest
      .map(request -> this.modelMapper.map(request, ExecutionModel.class))
      .flatMap(execution -> this.executionService.update(id, execution))
      .switchIfEmpty(Mono.error(new NoSuchElementException()));
  }

  @Operation(summary = "Delete execution by ID.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = ExecutionModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json"))
  })
  @DeleteMapping("/{id}")
  public Mono<ExecutionModel> delete(@PathVariable final UUID id) {
    return this.executionService
      .delete(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException()));
  }
}
