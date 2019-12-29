package com.wojnarowicz.sfg.recipe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class Category extends NamedEntity {

	private Set<Recipe> recipes;

    public Category(String id, String name) {
		super(id, name);
	}
}
