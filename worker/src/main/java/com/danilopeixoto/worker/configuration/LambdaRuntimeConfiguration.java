package com.danilopeixoto.worker.configuration;

import com.danilopeixoto.worker.model.RuntimeType;
import com.danilopeixoto.worker.runtime.LambdaRuntimeProvider;
import com.danilopeixoto.worker.runtime.java.JavaLambdaRuntime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LambdaRuntimeConfiguration {
  @Bean
  public LambdaRuntimeProvider lambdaRuntimeProvider() {
    LambdaRuntimeProvider provider = new LambdaRuntimeProvider();
    provider.registerRuntime(RuntimeType.Java, new JavaLambdaRuntime());

    return provider;
  }
}
