package com.wojnarowicz.sfg.recipe.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.wojnarowicz.sfg.recipe.domain.UnitOfMeasure;

import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String>{

    Mono<UnitOfMeasure> findByName(String string);

}
