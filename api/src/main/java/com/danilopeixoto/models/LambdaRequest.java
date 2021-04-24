package com.danilopeixoto.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class LambdaRequest {
  @NotEmpty
  @JsonProperty("name")
  private String name;

  @NotEmpty
  @JsonProperty("description")
  private String description;

  @NotEmpty
  @JsonProperty("runtime")
  private RuntimeType runtime;

  @NotEmpty
  @JsonProperty("source")
  private String source;

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
