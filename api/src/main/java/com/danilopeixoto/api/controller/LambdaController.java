package com.danilopeixoto.api.controller;

import com.danilopeixoto.api.model.ErrorResponse;
import com.danilopeixoto.api.model.LambdaModel;
import com.danilopeixoto.api.model.LambdaRequest;
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

@Tag(name = "Lambda")
@Validated
@RequestMapping("/lambda")
@RestController
public class LambdaController {
  @Autowired
  private LambdaService service;

  @Autowired
  private ModelMapper modelMapper;

  @Operation(summary = "Create lambda.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = LambdaModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "422",
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
  public Mono<LambdaModel> create(@Valid @RequestBody final Mono<LambdaRequest> lambdaRequest) {
    return lambdaRequest
      .map(request -> this.modelMapper.map(request, LambdaModel.class))
      .flatMap(this.service::create);
  }

  @Operation(summary = "Get lambda by ID.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = LambdaModel.class),
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
  public Mono<LambdaModel> get(@PathVariable final UUID id) {
    return this.service
      .get(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException()));
  }

  @Operation(summary = "Find lambda by name.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        array = @ArraySchema(schema = @Schema(implementation = LambdaModel.class)),
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
  public Flux<LambdaModel> find(@RequestParam(value = "name", required = false) final String name) {
    return Flux
      .just(Optional.ofNullable(name))
      .flatMap(lambdaName -> Mono.fromCallable(lambdaName::get))
      .flatMap(this.service::find)
      .onErrorResume(
        NoSuchElementException.class,
        exception -> this.service.list());
  }

  @Operation(summary = "Update lambda by ID.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = LambdaModel.class),
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
  public Mono<LambdaModel> update(
    @PathVariable final UUID id,
    @Valid @RequestBody final Mono<LambdaRequest> lambdaUpdateRequest) {
    return lambdaUpdateRequest
      .map(request -> this.modelMapper.map(request, LambdaModel.class))
      .flatMap(lambda -> this.service.update(id, lambda))
      .switchIfEmpty(Mono.error(new NoSuchElementException()));
  }

  @Operation(summary = "Delete lambda by ID.", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = LambdaModel.class),
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
  public Mono<LambdaModel> delete(@PathVariable final UUID id) {
    return this.service
      .delete(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException()));
  }
}
