package com.danilopeixoto.repository;

import com.danilopeixoto.model.LambdaModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface LambdaRepository extends ReactiveCrudRepository<LambdaModel, UUID> {
  @Query("select * from lambda where name = :name")
  Flux<LambdaModel> findByName(String name);
}
