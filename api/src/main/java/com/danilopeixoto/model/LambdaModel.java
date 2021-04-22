package com.danilopeixoto.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;
import java.util.UUID;

public class LambdaModel {
  @Id
  @Column("id")
  private UUID id;

  @Column("name")
  private String name;

  @Column("description")
  private String description;

  @Column("runtime")
  private RuntimeType runtime;

  @Column("source")
  private String source;

  @Column("created_at")
  @CreatedDate
  private Instant createdAt;

  @Column("updated_at")
  @LastModifiedDate
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
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public RuntimeType getRuntime() {
    return runtime;
  }

  public String getSource() {
    return source;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }
}
