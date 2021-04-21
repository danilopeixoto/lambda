package com.danilopeixoto.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.r2dbc.postgresql.codec.Json;

public class ExecutionModel {
  @Id
  private final UUID id;
  private final UUID lambdaID;
  private final List<Json> arguments;
  private final Json result;
  private final String log;
  private final StatusType status;
  @CreatedDate
  private final Date createdAt;
  @LastModifiedDate
  private final Date updatedAt;

  public ExecutionModel(
      UUID id,
      UUID lambdaID,
      List<Json> arguments,
      Json result,
      String log,
      StatusType status,
      Date createdAt,
      Date updatedAt) {
    this.id = id;
    this.lambdaID = lambdaID;
    this.arguments = arguments;
    this.result = result;
    this.log = log;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getID() {
    return id;
  }

  public UUID getLambdaID() {
    return lambdaID;
  }

  public List<Json> getArguments() {
    return arguments;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }
}
