package com.wojnarowicz.sfg.recipe.services;

import java.util.Set;

import com.wojnarowicz.sfg.recipe.commands.UnitOfMeasureCommand;

/**
 * Created by jt on 6/28/17.
 */
public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
