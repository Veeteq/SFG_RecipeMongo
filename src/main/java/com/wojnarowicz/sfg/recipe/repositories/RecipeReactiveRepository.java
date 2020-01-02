package com.wojnarowicz.sfg.recipe.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.wojnarowicz.sfg.recipe.domain.Recipe;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
