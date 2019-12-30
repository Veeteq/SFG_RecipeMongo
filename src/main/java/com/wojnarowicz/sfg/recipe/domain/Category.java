package com.wojnarowicz.sfg.recipe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jt on 6/13/17.
 */
@Getter
@Setter
@NoArgsConstructor
@Document
public class Category extends NamedEntity {

	@DBRef
	private Set<Recipe> recipes;

    public Category(String id, String name) {
		super(id, name);
	}
}
