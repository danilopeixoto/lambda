package com.danilopeixoto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.r2dbc.postgresql.codec.Json;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ExecutionUpdateRequest {
  @NotNull
  @JsonProperty("result")
  private Json result;

  @NotNull
  @JsonProperty("log")
  private String log;

  @NotEmpty
  @JsonProperty("status")
  private StatusType status;

  public ExecutionUpdateRequest(
    Json result,
    String log,
    StatusType status) {
    this.result = result;
    this.log = log;
    this.status = status;
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

  public Json getResult() {
    return this.result;
  }

  public String getLog() {
    return this.log;
  }

  public StatusType getStatus() {
    return this.status;
  }
}
