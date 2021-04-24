package com.danilopeixoto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.r2dbc.postgresql.codec.Json;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ExecutionModel {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("lambda_id")
  private UUID lambdaID;

  @JsonProperty("arguments")
  private List<Json> arguments;

  @JsonProperty("result")
  private Json result;

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
    List<Json> arguments,
    Json result,
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

  public void setArguments(List<Json> arguments) {
    this.arguments = arguments;
  }

  public void setResult(Json result) {
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

  public List<Json> getArguments() {
    return this.arguments;
  }

  public Json getResult() {
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
