package com.example.services;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.CRUDOperation;
import com.example.common.beans.PropertiesBean;
import com.example.dto.PersonInfoDto;
import com.example.entities.Person;
import com.example.exception.ErrorMessageGenericException;
import com.example.repositories.PersonRepository;
import com.example.services.log.AppLogService;
import com.example.services.persondata.EmailService;
import com.example.services.persondata.PhoneService;

/**
 * Service responsible for searching people through the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class PersonService {

	private static final Logger log = LoggerFactory.getLogger("applogger");

	@Autowired
	private PropertiesBean properties;

	@Autowired
	private AppLogService appLogService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PhoneService phoneService;

	/**
	 * Create a new person in database.
	 * 
	 * @param personToCreate
	 * @return
	 */
	public Optional<Person> create(final Person personToCreate) {
		log.debug("Starting person creation");

		Optional<Person> createdPerson = Optional.empty();
		if (personToCreate != null && isValidToInsert(personToCreate)) {

			consistFlagFields(personToCreate);

			createdPerson = Optional.ofNullable(personRepository.save(personToCreate));
			if (createdPerson.isPresent()) {
				log.debug("Created with success. Id: " + createdPerson.get().getPersonId());
				logAction(createdPerson.get(), CRUDOperation.CREATE);

				// to get all properties with default values
				createdPerson = findOne(createdPerson.get().getPersonId());
			} else {
				log.error("Error creating person: " + personToCreate.toString());
			}
		}
		return createdPerson;
	}

	/**
	 * Delete a person in database.
	 * 
	 * @param personToRemove
	 * @return
	 */
	public boolean delete(final Person personToRemove) {
		log.debug("Starting person delete");

		if (personToRemove != null && isValidToDelete(personToRemove)) {
			personRepository.delete(personToRemove);
			log.debug("Person deleted with success. " + personToRemove.toString());
			logAction(personToRemove, CRUDOperation.DELETE);
			return true;
		}

		return false;
	}

	/**
	 * Search for a person by id.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Person> findOne(final Long id) {
		return personRepository.findByPersonIdAndActive(id, "S");
	}

	/**
	 * Search for a list of people with filters. Return depends of parameters, searching in follow order: cpf, or name,
	 *  or all people paginated without filter.
	 * 
	 * @param cpf
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Person> findAll(String cpf, String name, Integer page, Integer size) {

		Page<Person> results = null;

		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 100;
		}

		PageRequest pageable = PageRequest.of(page, size);

		if (cpf == null && name == null) {
			results = personRepository.findAllByActive( "S",pageable);
		} else if (cpf != null) {
				results = personRepository.findAllByCpfAndActive( cpf, "S", pageable );
		} else {
				results = personRepository.findAllByNameContainingAndActive(name, "S", pageable);
		}

		return results;
	}

	/**
	 * Gets all person's info.
	 * 
	 * @param person
	 * @return
	 */
	public PersonInfoDto findAllPersonInfo(Person person) {
		PersonInfoDto personInfo = new PersonInfoDto();
		personInfo.setPerson(person);

		final int page = 0;
		final int size = 10000;

		personInfo.setEmails(emailService.findAll(person.getPersonId(), null, page,
			size).getContent());

		personInfo.setPhones(phoneService.findAll(person.getPersonId(), null, null, page,
			size).getContent());

		return personInfo;
	}


	/**
	 * Update a person in database.
	 * 
	 * @param personToUpdate
	 * @return
	 */
	public Optional<Person> update(final Person personToUpdate) {
		log.debug("Starting person update");

		if (personToUpdate != null && isValidToUpdate(personToUpdate)) {

			consistFlagFields(personToUpdate);

			Person savedPerson = personRepository.save(personToUpdate);
			if (savedPerson != null) {
				log.debug("Person updated with success. " + personToUpdate.toString());
				logAction(personToUpdate, CRUDOperation.UPDATE);
				return Optional.ofNullable( savedPerson );
			}
		}
		return Optional.empty();
	}

	private void consistFlagFields(Person personToCreate) {
		if (personToCreate.getUpdatedDate() == null) {
			personToCreate.setUpdatedDate(new Date());
		}

	}

	private boolean isValidToDelete(final Person person) {

		StringBuilder msg = new StringBuilder("");

		if (person.getPersonId() == null ) {
			msg.append(properties.getMessage("msg.error.generic.personId"));
		}

		if (msg.length() > 0) {
			throw new ErrorMessageGenericException(msg.toString());
		}

		return true;
	}

	private boolean isValidToInsert(final Person person) {
		return validateNotNull(person);
	}

	private boolean isValidToUpdate(final Person person) {

		StringBuilder msg = new StringBuilder("");

		if (validateNotNull(person)) {
			if (person.getPersonId() == null) {
				msg.append(properties.getMessage("msg.error.generic.personId"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		}

		return true;
	}

	private void logAction(final Person person, final CRUDOperation operation) {

		if (person != null) {

			String msg = "";
			if (CRUDOperation.CREATE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.person.create"),
					person.getName());
			} else if (CRUDOperation.DELETE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.person.delete"),
					person.getName());
			} else if (CRUDOperation.UPDATE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.person.update"),
					person.getName());
			}

			if (!msg.isEmpty()) {
				appLogService.insertMsg(msg, "");
			}
		}

	}

	private boolean validateNotNull(final Person person) {
		StringBuilder msg = new StringBuilder("");

		if (person.getGenderType() == null) {
         msg.append(properties.getMessage("msg.error.person.genderType"));
      }
      if (person.getBirthDate() == null) {
         msg.append(properties.getMessage("msg.error.person.birthDate"));
      }
      if (person.getName() == null) {
         msg.append(properties.getMessage("msg.error.person.name"));
      }
		if (msg.length() > 0) {
			throw new ErrorMessageGenericException(msg.toString());
		}

		return true;
	}
}
