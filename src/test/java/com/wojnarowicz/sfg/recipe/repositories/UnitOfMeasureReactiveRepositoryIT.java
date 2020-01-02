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

import com.wojnarowicz.sfg.recipe.domain.UnitOfMeasure;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureReactiveRepositoryIT {

    private static final String TEST_SPOON = "TestSpoon";

    @Autowired
    UnitOfMeasureReactiveRepository uomReactiveRepository;

    private UnitOfMeasure testUoM;
    
    @BeforeEach
    void setUp() throws Exception {
        uomReactiveRepository.deleteAll().block();
        
        testUoM = new UnitOfMeasure();
        testUoM.setName(TEST_SPOON);
    }

    @AfterEach
    void cleanUp() {
        uomReactiveRepository.deleteAll().block();
    }
    
    @Test
    void testSaveCount() {
        uomReactiveRepository.save(testUoM).block();
        
        Long categoriesCount = uomReactiveRepository.count().block();
        assertEquals(1, categoriesCount);
    }

    @Test
    void testSaveFind() {
        uomReactiveRepository.save(testUoM).block();
        
        Long uomCount = uomReactiveRepository.count().block();
        
        Mono<UnitOfMeasure> savedUom = uomReactiveRepository.findByName(TEST_SPOON);
        assertEquals(1, uomCount);
        assertNotNull(savedUom);
    }

}
