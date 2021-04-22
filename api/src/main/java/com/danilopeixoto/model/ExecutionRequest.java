package com.danilopeixoto.model;

import io.r2dbc.postgresql.codec.Json;

import java.util.List;

public class ExecutionRequest {
  private final List<Json> arguments;

  public ExecutionRequest(List<Json> arguments) {
    this.arguments = arguments;
  }

  public List<Json> getArguments() {
    return arguments;
  }
}
