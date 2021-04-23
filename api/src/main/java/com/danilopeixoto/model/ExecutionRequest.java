package com.danilopeixoto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.r2dbc.postgresql.codec.Json;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ExecutionRequest {
  @NotNull
  @JsonProperty("lambda_name")
  private String lambdaName;

  @NotNull
  @JsonProperty("arguments")
  private List<Json> arguments;

  public ExecutionRequest(String lambdaName, List<Json> arguments) {
    this.lambdaName = lambdaName;
    this.arguments = arguments;
  }

  public void setLambdaName(String lambdaName) {
    this.lambdaName = lambdaName;
  }

  public void setArguments(List<Json> arguments) {
    this.arguments = arguments;
  }

  public List<Json> getArguments() {
    return this.arguments;
  }

  public String getLambdaName() {
    return this.lambdaName;
  }
}
