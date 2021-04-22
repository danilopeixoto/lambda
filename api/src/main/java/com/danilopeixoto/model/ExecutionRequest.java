package com.danilopeixoto.model;

import io.r2dbc.postgresql.codec.Json;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ExecutionRequest {
  @NotNull
  private List<Json> arguments;

  public ExecutionRequest(List<Json> arguments) {
    this.arguments = arguments;
  }

  public void setArguments(List<Json> arguments) {
    this.arguments = arguments;
  }

  public List<Json> getArguments() {
    return arguments;
  }
}
