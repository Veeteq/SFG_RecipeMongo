package com.wojnarowicz.sfg.recipe.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.wojnarowicz.sfg.recipe.domain.Category;

import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

    Mono<Category> findByName(String name);
}
