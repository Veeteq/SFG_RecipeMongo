package com.wojnarowicz.sfg.recipe.services.impl;

import org.springframework.stereotype.Service;

import com.wojnarowicz.sfg.recipe.commands.UnitOfMeasureCommand;
import com.wojnarowicz.sfg.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.wojnarowicz.sfg.recipe.repositories.UnitOfMeasureReactiveRepository;
import com.wojnarowicz.sfg.recipe.services.UnitOfMeasureService;

import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {

        return unitOfMeasureRepository
                .findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
