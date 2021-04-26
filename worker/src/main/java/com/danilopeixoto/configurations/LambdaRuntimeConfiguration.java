package com.danilopeixoto.configurations;

import com.danilopeixoto.models.RuntimeType;
import com.danilopeixoto.services.runtimes.LambdaRuntimeProvider;
import com.danilopeixoto.services.runtimes.java.JavaLambdaRuntime;
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
