package com.example.dto;

import java.util.List;

import com.example.entities.Person;
import com.example.entities.persondata.Email;
import com.example.entities.persondata.Phone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonInfoDto {

	private Person person;

	private List<Email> emails;

	private List<Phone> phones;

	public PersonInfoDto() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{ ");
		builder.append("'person': ");
		builder.append(person != null ? person.toString() : "null").append(", ");
		builder.append("'emails': ");
		builder.append(emails != null ? emails.toString() : "[]").append(", ");
		builder.append("'phones': ");
		builder.append(phones != null ? phones.toString() : "[]");
		builder.append(" }");
		return builder.toString();
	}

}
