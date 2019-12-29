package com.wojnarowicz.sfg.recipe.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by jt on 6/13/17.
 */
@Getter
@Setter
public class Ingredient extends NamedEntity {

    private BigDecimal amount;

    private UnitOfMeasure uom;
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String name, BigDecimal amount, UnitOfMeasure uom) {
        super(name);
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String name, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        super(name);
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

}
