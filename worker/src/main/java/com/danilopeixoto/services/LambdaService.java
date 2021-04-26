package com.danilopeixoto.services;

import com.danilopeixoto.models.ExecutionModel;
import com.danilopeixoto.models.ExecutionUpdateRequest;
import com.danilopeixoto.models.LambdaModel;
import com.danilopeixoto.models.StatusType;
import com.danilopeixoto.services.runtimes.LambdaRuntimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;

@Service
public class LambdaService {
  @Autowired
  private WebClient client;

  @Autowired
  private LambdaRuntimeProvider lambdaRuntimeProvider;

  public Mono<LambdaModel> get(final UUID id) {
    return this.client
      .get()
      .uri("/lambda/" + id.toString())
      .retrieve()
      .bodyToMono(LambdaModel.class);
  }

  public Mono<? extends Tuple2<UUID, ExecutionUpdateRequest>> execute(
    final ExecutionModel execution,
    final LambdaModel lambda) {
    return Mono
      .just(lambdaRuntimeProvider.get(lambda.getRuntime()))
      .map(runtime -> runtime.execute(lambda.getSource(), execution.getArguments()))
      .flatMap(result -> Mono
        .just(execution.getID())
        .zipWith(Mono.just(new ExecutionUpdateRequest(
          result.getT1(),
          result.getT2(),
          result.getT3() == 0 ? StatusType.Done : StatusType.Error))));
  }
}
