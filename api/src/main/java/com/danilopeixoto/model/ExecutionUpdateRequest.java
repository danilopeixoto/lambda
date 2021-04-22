package com.danilopeixoto.model;

import io.r2dbc.postgresql.codec.Json;

import javax.validation.constraints.NotEmpty;

public class ExecutionUpdateRequest {
  @NotEmpty
  private Json result;

  @NotEmpty
  private String log;

  @NotEmpty
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
    return result;
  }

  public String getLog() {
    return log;
  }

  public StatusType getStatus() {
    return status;
  }
}
