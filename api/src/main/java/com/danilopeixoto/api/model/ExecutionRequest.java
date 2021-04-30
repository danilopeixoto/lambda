package com.danilopeixoto.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public class ExecutionRequest {
  @JsonProperty("lambda_id")
  private UUID lambdaID;

  @JsonProperty("lambda_name")
  private String lambdaName;

  @NotNull(message = "The arguments field is required.")
  @JsonProperty("arguments")
  private ArrayNode arguments;

  public ExecutionRequest() {
  }

  public ExecutionRequest(UUID lambdaID, String lambdaName, ArrayNode arguments) {
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

  public void setArguments(ArrayNode arguments) {
    this.arguments = arguments;
  }

  @JsonIgnore
  public Optional<UUID> getLambdaID() {
    return Optional.ofNullable(this.lambdaID);
  }

  @JsonIgnore
  public Optional<String> getLambdaName() {
    return Optional.ofNullable(this.lambdaName);
  }

  public ArrayNode getArguments() {
    return this.arguments;
  }
}
