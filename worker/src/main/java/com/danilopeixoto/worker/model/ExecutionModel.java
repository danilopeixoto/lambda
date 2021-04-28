package com.danilopeixoto.worker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ExecutionModel {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("lambda_id")
  private UUID lambdaID;

  @JsonProperty("arguments")
  private List<JsonNode> arguments;

  @JsonProperty("result")
  private JsonNode result;

  @JsonProperty("log")
  private String log;

  @JsonProperty("status")
  private StatusType status;

  @JsonProperty("created_at")
  private Instant createdAt;

  @JsonProperty("updated_at")
  private Instant updatedAt;

  public ExecutionModel(
    UUID lambdaID,
    List<JsonNode> arguments,
    JsonNode result,
    String log,
    StatusType status) {
    this.lambdaID = lambdaID;
    this.arguments = arguments;
    this.result = result;
    this.log = log;
    this.status = status;
  }

  public void setLambdaID(UUID lambdaID) {
    this.lambdaID = lambdaID;
  }

  public void setArguments(List<JsonNode> arguments) {
    this.arguments = arguments;
  }

  public void setResult(JsonNode result) {
    this.result = result;
  }

  public void setLog(String log) {
    this.log = log;
  }

  public void setStatus(StatusType status) {
    this.status = status;
  }

  public UUID getID() {
    return this.id;
  }

  public UUID getLambdaID() {
    return this.lambdaID;
  }

  public List<JsonNode> getArguments() {
    return this.arguments;
  }

  public JsonNode getResult() {
    return this.result;
  }

  public String getLog() {
    return this.log;
  }

  public StatusType getStatus() {
    return this.status;
  }

  public Instant getCreatedAt() {
    return this.createdAt;
  }

  public Instant getUpdatedAt() {
    return this.updatedAt;
  }
}
