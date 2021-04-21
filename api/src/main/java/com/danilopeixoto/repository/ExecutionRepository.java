package com.danilopeixoto.repository;

import com.danilopeixoto.model.ExecutionModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ExecutionRepository extends ReactiveCrudRepository<ExecutionModel, UUID> {
  @Query("select * from Execution where lambdaID = :lambdaID")
  Flux<ExecutionModel> findByLambdaID(UUID lambdaID);
}
