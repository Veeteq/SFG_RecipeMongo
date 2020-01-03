package com.wojnarowicz.sfg.recipe.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wojnarowicz.sfg.recipe.commands.IngredientCommand;
import com.wojnarowicz.sfg.recipe.converters.IngredientCommandToIngredient;
import com.wojnarowicz.sfg.recipe.converters.IngredientToIngredientCommand;
import com.wojnarowicz.sfg.recipe.domain.Ingredient;
import com.wojnarowicz.sfg.recipe.domain.Recipe;
import com.wojnarowicz.sfg.recipe.repositories.RecipeReactiveRepository;
import com.wojnarowicz.sfg.recipe.repositories.UnitOfMeasureReactiveRepository;
import com.wojnarowicz.sfg.recipe.services.IngredientService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeReactiveRepository recipeRepository, UnitOfMeasureReactiveRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        return recipeRepository
                .findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
                    ingredientCommand.setRecipeId(recipeId);
                    return ingredientCommand;
                });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        recipeRepository.findById(command.getRecipeId())
        .subscribe(recipe -> {
            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
            .stream()
            .filter(ingredient -> ingredient.getId().equals(command.getId()))
            .findFirst();
            
            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setName(command.getName());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(command.getUom().getId()).block());
                
                if(ingredientFound.getUom() == null) {                        
                    new RuntimeException("UOM NOT FOUND"); //todo address this
                }
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                recipe.addIngredient(ingredient);
            }
            
            recipeRepository.save(recipe)
            .subscribe(savedRecipe -> {
                savedRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();
            });
        });
        
        return recipeRepository
                .findById(command.getRecipeId())
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .single()
                .map(ingredient -> {
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
                    ingredientCommand.setRecipeId(command.getRecipeId());
                    return ingredientCommand;
                });
/*        


            recipeRepository.save(recipe).subscribe(savedRecipe -> {

            Optional<Ingredient> savedIngredientOptional = savedRecipe
                    .getIngredients()
                    .stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getName().equals(command.getName()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            //savedIngredientCommand = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            //savedIngredientCommand.setRecipeId(recipe.getId());
            
            //return Mono.just(savedIngredientCommand);
        });
        });
*/        
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String idToDelete) {

        log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

        Recipe recipe = recipeRepository.findById(recipeId).block();

        if(recipe != null) {
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("found Ingredient");
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe).block();
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }
        
        return Mono.empty();
    }
}
