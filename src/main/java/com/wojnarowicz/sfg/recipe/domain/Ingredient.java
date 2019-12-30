package com.wojnarowicz.sfg.recipe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jt on 6/13/17.
 */
@Getter
@Setter
public class Ingredient extends NamedEntity {

    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure uom;

    public Ingredient() {
    	super(UUID.randomUUID().toString(), null);
    }

    public Ingredient(String name, BigDecimal amount, UnitOfMeasure uom) {
        super(UUID.randomUUID().toString(), name);
        this.amount = amount;
        this.uom = uom;
    }
}
