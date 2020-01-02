package com.wojnarowicz.sfg.recipe.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wojnarowicz.sfg.recipe.commands.UnitOfMeasureCommand;
import com.wojnarowicz.sfg.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.wojnarowicz.sfg.recipe.domain.UnitOfMeasure;
import com.wojnarowicz.sfg.recipe.repositories.UnitOfMeasureReactiveRepository;
import com.wojnarowicz.sfg.recipe.services.impl.UnitOfMeasureServiceImpl;

import reactor.core.publisher.Flux;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    
    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void test() {
        Set<UnitOfMeasure> uoms = new HashSet<>();
        
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");
        uoms.add(uom1);
        
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom1.setId("2");
        uoms.add(uom2);
        
        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2));
        
        List<UnitOfMeasureCommand> uomCommands = unitOfMeasureService.listAllUoms().collectList().block();
        
        assertEquals(2, uomCommands.size());
        verify(unitOfMeasureReactiveRepository, times(1)).findAll();
    }

}
