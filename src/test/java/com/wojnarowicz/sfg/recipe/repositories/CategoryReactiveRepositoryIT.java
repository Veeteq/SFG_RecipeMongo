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

import com.wojnarowicz.sfg.recipe.domain.Category;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryReactiveRepositoryIT {

    private static final String TEST_CATEGORY = "TestCategory";

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    private Category testCategory;
    
    @BeforeEach
    void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
        
        testCategory = new Category();
        testCategory.setName(TEST_CATEGORY);
    }

    @AfterEach
    void cleanUp() {
        categoryReactiveRepository.deleteAll().block();
    }
    
    @Test
    void testSave() {
        categoryReactiveRepository.save(testCategory).block();
        
        Long categoriesCount = categoryReactiveRepository.count().block();
        assertEquals(1, categoriesCount);
    }

    @Test
    void testSaveFind() {
        categoryReactiveRepository.save(testCategory).block();
        
        Long categoriesCount = categoryReactiveRepository.count().block();
        
        Mono<Category> savedCategory = categoryReactiveRepository.findByName(TEST_CATEGORY);
        assertEquals(1, categoriesCount);
        assertNotNull(savedCategory);
    }

}
