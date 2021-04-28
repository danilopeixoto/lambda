package com.danilopeixoto.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;
import java.util.UUID;

public class LambdaModel {
  @Id
  @Column("id")
  @JsonProperty("id")
  private UUID id;

  @Column("name")
  @JsonProperty("name")
  private String name;

  @Column("description")
  @JsonProperty("description")
  private String description;

  @Column("runtime")
  @JsonProperty("runtime")
  private RuntimeType runtime;

  @Column("source")
  @JsonProperty("source")
  private String source;

  @CreatedDate
  @Column("created_at")
  @JsonProperty("created_at")
  private Instant createdAt;

  @LastModifiedDate
  @Column("updated_at")
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
