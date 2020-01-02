package com.wojnarowicz.sfg.recipe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient extends NamedEntity {

    private BigDecimal amount;
    private UnitOfMeasure uom;

    public Ingredient() {
        this.setId(UUID.randomUUID().toString());
    }

    public Ingredient(String name, BigDecimal amount, UnitOfMeasure uom) {
        this.setId(UUID.randomUUID().toString());
        this.setName(name);
        this.amount = amount;
        this.uom = uom;
    }    
}
