package com.wojnarowicz.sfg.recipe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class NamedEntity extends BaseEntity {

	private String name;
}
