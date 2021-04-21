package com.danilopeixoto.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;

public class LambdaModel {
  @Id
  private final UUID id;
  private final String name;
  private final String description;
  private final RuntimeType runtime;
  private final String source;
  @CreatedDate
  private final Date createdAt;
  @LastModifiedDate
  private final Date updatedAt;

  public LambdaModel(
      UUID id,
      String name,
      String description,
      RuntimeType runtime,
      String source,
      Date createdAt,
      Date updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.runtime = runtime;
    this.source = source;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }
}
