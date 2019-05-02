package com.example.repositories.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.entities.Person;
import com.example.repositories.custom.CustomPersonRepository;

/**
 * Implementation of CustomPersonRepository;
 * 
 * @author Renato M B Nunes
 */
@Repository
public class CustomPersonRepositoryImpl implements CustomPersonRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Person> findAllByIds(List<Long> ids) {
		List<Person> personList = new ArrayList<>();

		if (ids.size() > 0) {
			Session session = entityManager.unwrap(Session.class);

			MultiIdentifierLoadAccess<Person> multiLoadAccess = session.byMultipleIds(Person.class);
			personList = multiLoadAccess.multiLoad(ids);
		}

		return personList;
	}
}
