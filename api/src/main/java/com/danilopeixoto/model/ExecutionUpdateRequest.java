package com.danilopeixoto.model;

import io.r2dbc.postgresql.codec.Json;

public class ExecutionUpdateRequest {
  private final Json result;
  private final String log;
  private final StatusType status;

  public ExecutionUpdateRequest(
      Json result,
      String log,
      StatusType status) {
    this.result = result;
    this.log = log;
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
