package com.danilopeixoto.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class ExecutionRequest {
  @JsonProperty("lambda_id")
  private UUID lambdaID;

  @JsonProperty("lambda_name")
  private String lambdaName;

  @NotNull
  @JsonProperty("arguments")
  private List<JsonNode> arguments;

  public ExecutionRequest(UUID lambdaID, String lambdaName, List<JsonNode> arguments) {
    this.lambdaID = lambdaID;
    this.lambdaName = lambdaName;
    this.arguments = arguments;
  }

  public void setLambdaID(UUID lambdaID) {
    this.lambdaID = lambdaID;
  }

  public void setLambdaName(String lambdaName) {
    this.lambdaName = lambdaName;
  }

  public void setArguments(List<JsonNode> arguments) {
    this.arguments = arguments;
  }

  public UUID getLambdaID() {
    return this.lambdaID;
  }

  public List<JsonNode> getArguments() {
    return this.arguments;
  }

  public String getLambdaName() {
    return this.lambdaName;
  }
}
