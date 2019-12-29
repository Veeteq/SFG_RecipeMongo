package com.wojnarowicz.sfg.recipe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity {

    private String id;

	public BaseEntity(String id) {
		this.id = id;
	}
}
