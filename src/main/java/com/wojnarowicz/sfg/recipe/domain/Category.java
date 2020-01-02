package com.wojnarowicz.sfg.recipe.domain;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Category extends NamedEntity {

	private Set<Recipe> recipes;
}
