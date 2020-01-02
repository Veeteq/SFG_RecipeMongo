package com.wojnarowicz.sfg.recipe.commands;

import com.wojnarowicz.sfg.recipe.domain.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCommand {

    private String firstName;
    private String lastName;
    
    public PersonCommand(Person person) {
        super();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
    }

    public String sayMyName() {
        return "PersonCommand [firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}
