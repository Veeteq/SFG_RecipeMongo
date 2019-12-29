package com.wojnarowicz.sfg.recipe.services;

import java.util.Set;

import com.wojnarowicz.sfg.recipe.commands.RecipeCommand;
import com.wojnarowicz.sfg.recipe.domain.Recipe;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(String id);

    RecipeCommand findCommandById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(String idToDelete);
}
