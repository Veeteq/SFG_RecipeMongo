package com.wojnarowicz.sfg.recipe.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public abstract class BaseEntity {

	@Id
    private String id;

	public BaseEntity(String id) {
		this.id = id;
	}
}
