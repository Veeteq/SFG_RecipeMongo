package com.wojnarowicz.sfg.recipe.services;

import com.wojnarowicz.sfg.recipe.commands.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();
}
