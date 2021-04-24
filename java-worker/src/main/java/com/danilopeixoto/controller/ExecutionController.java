package com.danilopeixoto.controller;

import com.danilopeixoto.service.ExecutionConsumer;
import com.danilopeixoto.service.ExecutionService;
import com.danilopeixoto.service.LambdaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ExecutionController implements ApplicationRunner {
  @Autowired
  private LambdaService lambdaService;

  @Autowired
  private ExecutionService executionService;

  @Autowired
  private ExecutionConsumer executionConsumer;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    this.executionConsumer
      .dequeue()
      .flatMap(execution -> this.executionService
        .get(execution.getID())
        .zipWith(this.lambdaService.get(execution.getLambdaID())))
      .flatMap(result -> this.lambdaService.execute(result.getT1(), result.getT2()))
      .flatMap(result -> this.executionService.update(result.getT1(), result.getT2()))
      .subscribe();
  }
}
