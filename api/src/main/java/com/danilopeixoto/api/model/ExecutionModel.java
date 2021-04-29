package com.danilopeixoto.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Table("execution")
public class ExecutionModel {
  @NotNull
  @Id
  @Column("id")
  @JsonProperty("id")
  private UUID id;

  @NotNull
  @Column("lambda_id")
  @JsonProperty("lambda_id")
  private UUID lambdaID;

  @NotNull
  @Column("arguments")
  @JsonProperty("arguments")
  private ArrayNode arguments;

  @NotNull
  @Column("result")
  @JsonProperty("result")
  private JsonNode result;

  @NotNull
  @Column("log")
  @JsonProperty("log")
  private String log;

  @NotNull
  @Column("status")
  @JsonProperty("status")
  private StatusType status;

  @NotNull
  @CreatedDate
  @Column("created_at")
  @JsonProperty("created_at")
  private Instant createdAt;

  @NotNull
  @LastModifiedDate
  @Column("updated_at")
  @JsonProperty("updated_at")
  private Instant updatedAt;

  public ExecutionModel() {
  }

  public ExecutionModel(
    UUID lambdaID,
    ArrayNode arguments,
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

  public void setArguments(ArrayNode arguments) {
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

  public ArrayNode getArguments() {
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
