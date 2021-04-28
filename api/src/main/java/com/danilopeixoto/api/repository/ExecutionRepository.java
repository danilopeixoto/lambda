package com.danilopeixoto.api.repository;

import com.danilopeixoto.api.model.ExecutionModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ExecutionRepository extends ReactiveCrudRepository<ExecutionModel, UUID> {
  @Query("select * from execution where lambda_id = :lambdaID")
  Flux<ExecutionModel> findByLambdaID(UUID lambdaID);
}
