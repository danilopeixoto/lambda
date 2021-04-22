package com.danilopeixoto.model;

import javax.validation.constraints.NotEmpty;

public class LambdaRequest {
  @NotEmpty
  private String name;

  @NotEmpty
  private String description;

  @NotEmpty
  private RuntimeType runtime;

  @NotEmpty
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
}
