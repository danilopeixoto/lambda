package com.danilopeixoto.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotNull;

public class ExecutionUpdateRequest {
  @NotNull(message = "The result field is required.")
  @JsonProperty("result")
  private JsonNode result;

  @NotNull(message = "The log field is required.")
  @JsonProperty("log")
  private String log;

  @NotNull(message = "The status field is required. Available options are ready, done and error.")
  @JsonProperty("status")
  private StatusType status;

  public ExecutionUpdateRequest() {
  }

  public ExecutionUpdateRequest(
    JsonNode result,
    String log,
    StatusType status) {
    this.result = result;
    this.log = log;
    this.status = status;
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

  public JsonNode getResult() {
    return this.result;
  }

  public String getLog() {
    return this.log;
  }

  public StatusType getStatus() {
    return this.status;
  }
}
