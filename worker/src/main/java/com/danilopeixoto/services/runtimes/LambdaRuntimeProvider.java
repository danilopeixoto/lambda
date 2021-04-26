package com.danilopeixoto.services.runtimes;

import com.danilopeixoto.models.RuntimeType;

import java.util.HashMap;

public class LambdaRuntimeProvider {
  private HashMap<RuntimeType, LambdaRuntime> runtimes;

  public LambdaRuntimeProvider() {
    this.runtimes = new HashMap<>();
  }

  public void registerRuntime(RuntimeType type, LambdaRuntime builder) {
    this.runtimes.put(type, builder);
  }

  public void deregisterRuntime(RuntimeType type) {
    this.runtimes.remove(type);
  }

  public HashMap<RuntimeType, LambdaRuntime> getRuntimes() {
    return this.runtimes;
  }

  public LambdaRuntime get(RuntimeType type) {
    return this.runtimes.get(type);
  }
}
