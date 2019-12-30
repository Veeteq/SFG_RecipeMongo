package com.wojnarowicz.sfg.recipe.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wojnarowicz.sfg.recipe.bootstrap.RecipeBootstrap;
import com.wojnarowicz.sfg.recipe.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
class UnitOfMeasureRepositoryIT {

	private static final String CUP = "Cup";
	private static final String TEASPOON = "Teaspoon";
	
	@Autowired
    CategoryRepository categoryRepository;
	
	@Autowired
    RecipeRepository recipeRepository;
	
	@Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;
	
	@BeforeEach
	public void setUp() {
		recipeRepository.deleteAll();
		categoryRepository.deleteAll();
		unitOfMeasureRepository.deleteAll();
		
		RecipeBootstrap recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
		recipeBootstrap.onApplicationEvent(null);
	}
	
	@Test
	void testFindByName() throws Exception {
		Optional<UnitOfMeasure> optional = unitOfMeasureRepository.findByName(TEASPOON);
		assertEquals(TEASPOON, optional.get().getName());
	}

	@Test
	void testFindByNameCup() throws Exception {
		Optional<UnitOfMeasure> optional = unitOfMeasureRepository.findByName(CUP);
		assertEquals(CUP, optional.get().getName());
	}
}
