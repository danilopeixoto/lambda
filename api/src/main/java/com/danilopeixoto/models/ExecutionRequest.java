package com.danilopeixoto.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ExecutionRequest {
  @NotEmpty
  @JsonProperty("lambda_name")
  private String lambdaName;

  @NotNull
  @JsonProperty("arguments")
  private List<JsonNode> arguments;

  public ExecutionRequest(String lambdaName, List<JsonNode> arguments) {
    this.lambdaName = lambdaName;
    this.arguments = arguments;
  }

  public void setLambdaName(String lambdaName) {
    this.lambdaName = lambdaName;
  }

  public void setArguments(List<JsonNode> arguments) {
    this.arguments = arguments;
  }

  public List<JsonNode> getArguments() {
    return this.arguments;
  }

  public String getLambdaName() {
    return this.lambdaName;
  }
}
