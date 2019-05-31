package com.example.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comm.registry.RegistryRequester;
import com.example.common.beans.PropertiesBean;
import com.example.entities.Person;
import com.example.exception.ErrorMessageGenericException;
import com.example.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	private static final Logger logger = LoggerFactory.getLogger("applogger");

	@Autowired
	private PropertiesBean properties;

	@Autowired
	private RegistryRequester registry;

	@Override
	public Person createPerson(Person person) {

   	// create person on registry database
   	Person registryPerson = registry.createPerson(person);
   	if (registryPerson == null) {
   		logger.error("Erro ao criar pessoa do usuário no Registry.");
   		throw new ErrorMessageGenericException(properties.getMessage(
   			"msg.error.person.createPerson"));
   	}
			
		return registryPerson;
	}

	@Override
	public Object getPersonInfo(Long personId) {

		logger.debug("Buscando informações da passoa no Registry");

		Object person = registry.getRegisterInfo(personId );
		if (person == null) {
			logger.error("Erro ao buscar pessoa no Registry.");
			throw new ErrorMessageGenericException(properties.getMessage(
				"msg.error.person.getPersonInfo"));
		}

		return person;
	}
}
