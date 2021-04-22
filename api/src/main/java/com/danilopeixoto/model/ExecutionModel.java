package com.danilopeixoto.model;

import io.r2dbc.postgresql.codec.Json;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Table("Execution")
public class ExecutionModel {
  @Id
  @Column("id")
  private UUID id;

  @Column("lambda_id")
  private UUID lambdaID;

  @Column("arguments")
  private List<Json> arguments;

  @Column("result")
  private Json result;

  @Column("log")
  private String log;

  @Column("status")
  private StatusType status;

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
