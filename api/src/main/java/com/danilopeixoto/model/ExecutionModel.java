package com.danilopeixoto.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import io.r2dbc.postgresql.codec.Json;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Execution")
public class ExecutionModel {
  @Id
  @Column("id")
  private UUID id;

  @Column("lambda_id")
  private final UUID lambdaID;

  @Column("arguments")
  private final List<Json> arguments;

  @Column("result")
  private final Json result;

  @Column("log")
  private final String log;

  @Column("status")
  private final StatusType status;

  @CreatedDate
  @Column("created_at")
  private Instant createdAt;

  @LastModifiedDate
  @Column("updated_at")
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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }
}
