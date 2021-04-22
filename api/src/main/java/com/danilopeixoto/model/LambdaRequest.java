package com.danilopeixoto.model;

public class LambdaRequest {
  private final String name;
  private final String description;
  private final RuntimeType runtime;
  private final String source;

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
