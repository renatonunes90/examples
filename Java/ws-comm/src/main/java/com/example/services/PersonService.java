package com.example.services;

import com.example.entities.Person;

public interface PersonService {

	Person createPerson(Person person);
	
	Object getPersonInfo(Long personId);
}
