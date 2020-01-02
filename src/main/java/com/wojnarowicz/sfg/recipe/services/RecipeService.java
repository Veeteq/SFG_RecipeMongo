package com.wojnarowicz.sfg.recipe.services;

import com.wojnarowicz.sfg.recipe.commands.RecipeCommand;
import com.wojnarowicz.sfg.recipe.domain.Recipe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> findCommandById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<Void> deleteById(String idToDelete);
}
