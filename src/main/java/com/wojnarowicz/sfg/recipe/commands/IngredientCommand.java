package com.wojnarowicz.sfg.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String recipeId;
    
    @NotBlank
    private String name;
    
    @Min(value = 0)
    @NotNull
    private BigDecimal amount;
    
    @NotNull
    private UnitOfMeasureCommand uom;
}
