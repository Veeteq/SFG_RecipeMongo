package com.wojnarowicz.sfg.recipe.repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wojnarowicz.sfg.recipe.domain.Recipe;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class RecipeReactiveRepositoryIT {

    private static final String TEST_RECIPE = "TestRecipe";

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    private Recipe testRecipe;
    
    @BeforeEach
    void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
        
        testRecipe = new Recipe();
        testRecipe.setName(TEST_RECIPE);
        testRecipe.setDescription(TEST_RECIPE);
    }

    @AfterEach
    void cleanUp() {
        recipeReactiveRepository.deleteAll().block();
    }
    
    @Test
    void testSaveCount() {
        recipeReactiveRepository.save(testRecipe).block();
        
        Long categoriesCount = recipeReactiveRepository.count().block();
        assertEquals(1, categoriesCount);
    }

    @Test
    void testSaveFindById() {
        Recipe savedRecipe = recipeReactiveRepository.save(testRecipe).block();
        
        Long recipeCount = recipeReactiveRepository.count().block();
        
        Mono<Recipe> foundRecipe = recipeReactiveRepository.findById(savedRecipe.getId());
        assertEquals(1, recipeCount);
        assertNotNull(foundRecipe);
    }

}
