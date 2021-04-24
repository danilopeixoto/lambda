package com.danilopeixoto.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

public class LambdaModel {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("runtime")
  private RuntimeType runtime;

  @JsonProperty("source")
  private String source;

  @JsonProperty("created_at")
  private Instant createdAt;

  @JsonProperty("updated_at")
  private Instant updatedAt;

  public LambdaModel(
    String name,
    String description,
    RuntimeType runtime,
    String source) {
    this.name = name;
    this.description = description;
    this.runtime = runtime;
    this.source = source;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setRuntime(RuntimeType runtime) {
    this.runtime = runtime;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public UUID getID() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public RuntimeType getRuntime() {
    return this.runtime;
  }

  public String getSource() {
    return this.source;
  }

  public Instant getCreatedAt() {
    return this.createdAt;
  }

  public Instant getUpdatedAt() {
    return this.updatedAt;
  }
}
