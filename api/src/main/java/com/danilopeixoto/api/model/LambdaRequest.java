package com.danilopeixoto.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LambdaRequest {
  @NotEmpty(message = "The name field is required.")
  @JsonProperty("name")
  private String name;

  @NotEmpty(message = "The description field is required.")
  @JsonProperty("description")
  private String description;

  @NotNull(message = "The runtime field is required. Available option is java.")
  @JsonProperty("runtime")
  private RuntimeType runtime;

  @NotEmpty(message = "The source field is required.")
  @JsonProperty("source")
  private String source;

  public LambdaRequest() {
  }

  public LambdaRequest(
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
}
