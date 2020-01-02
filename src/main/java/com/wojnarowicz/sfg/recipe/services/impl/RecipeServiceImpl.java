package com.wojnarowicz.sfg.recipe.services.impl;

import org.springframework.stereotype.Service;

import com.wojnarowicz.sfg.recipe.commands.RecipeCommand;
import com.wojnarowicz.sfg.recipe.converters.RecipeCommandToRecipe;
import com.wojnarowicz.sfg.recipe.converters.RecipeToRecipeCommand;
import com.wojnarowicz.sfg.recipe.domain.Recipe;
import com.wojnarowicz.sfg.recipe.repositories.RecipeReactiveRepository;
import com.wojnarowicz.sfg.recipe.services.RecipeService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");

        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        log.debug("RecipeService: findById(" + id + ")");
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        log.debug("RecipeService: findCommandById(" + id + ")");
        return recipeRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                    recipeCommand.getIngredients().forEach(rc -> {
                        rc.setRecipeId(recipe.getId());
                    });
                    return recipeCommand;
                });
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        log.debug("RecipeService: saveRecipeCommand(" + command.getId() + ")");
        return recipeRepository.save(recipeCommandToRecipe.convert(command)).map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        log.debug("RecipeService: deleteById(" + id + ")");
        recipeRepository.deleteById(id).block();
        return Mono.empty();
    }
}
