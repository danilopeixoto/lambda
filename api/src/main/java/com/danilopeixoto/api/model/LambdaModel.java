package com.danilopeixoto.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Table("lambda")
public class LambdaModel {
  @NotNull(message = "The ID field is required.")
  @Id
  @Column("id")
  @JsonProperty("id")
  private UUID id;

  @NotEmpty(message = "The name field is required.")
  @Column("name")
  @JsonProperty("name")
  private String name;

  @NotEmpty(message = "The description field is required.")
  @Column("description")
  @JsonProperty("description")
  private String description;

  @NotNull(message = "The runtime field is required. Available option is java.")
  @Column("runtime")
  @JsonProperty("runtime")
  private RuntimeType runtime;

  @NotEmpty(message = "The source field is required.")
  @Column("source")
  @JsonProperty("source")
  private String source;

  @NotNull(message = "The created at field is required.")
  @CreatedDate
  @Column("created_at")
  @JsonProperty("created_at")
  private Instant createdAt;

  @NotNull(message = "The updated at field is required.")
  @LastModifiedDate
  @Column("updated_at")
  @JsonProperty("updated_at")
  private Instant updatedAt;

  public LambdaModel() {
  }

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
