package com.danilopeixoto.worker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class ExecutionUpdateRequest {
  @JsonProperty("result")
  private JsonNode result;

  @JsonProperty("log")
  private String log;

  @JsonProperty("status")
  private StatusType status;

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
