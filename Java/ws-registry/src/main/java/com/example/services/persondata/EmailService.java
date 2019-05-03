package com.example.services.persondata;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.CRUDOperation;
import com.example.common.beans.PropertiesBean;
import com.example.entities.Person;
import com.example.entities.persondata.Email;
import com.example.exception.ErrorMessageGenericException;
import com.example.repositories.PersonRepository;
import com.example.repositories.persondata.EmailRepository;
import com.example.services.log.AppLogService;

/**
 * Service responsible for searching emails through the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class EmailService {

	private static final Logger log = LoggerFactory.getLogger("applogger");

	@Autowired
	private PropertiesBean properties;

	@Autowired
	private AppLogService appLogService;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private PersonRepository personRepository;

	/**
	 * Verifies if the e-mail received already exists
	 * 
	 * @param email
	 * @return
	 */
	public boolean alreadyExists(final String email) {

		log.debug("Looking for e-mail: " + email);

		boolean hasDuplicate = false;

		if (!email.isEmpty()) {
			Email emailObj = new Email();
			emailObj.setEmailId(-1L);
			emailObj.setEmail(email);

			try {
				hasDuplicate = alreadyExists(emailObj);
			} catch (ErrorMessageGenericException e) {
				hasDuplicate = true;
			}
		}

		return hasDuplicate;
	}

	/**
	 * Create a new email in database.
	 * 
	 * @param emailToCreate
	 * @return
	 */
	public Optional<Email> create(final Email emailToCreate) {
		log.debug("Starting email creation");

		Optional<Email> createdEmail = Optional.empty();
		if (emailToCreate != null && isValidToInsert(emailToCreate)) {

			createdEmail = Optional.ofNullable(emailRepository.create(emailToCreate));
			if (createdEmail.isPresent()) {
				log.debug("Created with success. Id: " + createdEmail.get().getEmailId());
				logAction(createdEmail.get(), CRUDOperation.CREATE);
			} else {
				log.error("Error creating e-mail: " + emailToCreate.toString());
			}
		}
		return createdEmail;
	}

	/**
	 * Delete an email in database.
	 * 
	 * @param emailToRemove
	 * @return
	 */
	public boolean delete(final Email emailToRemove) {
		log.debug("Starting email delete");

		if (emailToRemove != null && isValidToDelete(emailToRemove)) {
			int rowsAffected = emailRepository.customDelete(emailToRemove);
			if (rowsAffected > 0) {
				log.debug("Email deleted with success. " + emailToRemove.toString());
				logAction(emailToRemove, CRUDOperation.DELETE);
				return true;
			}
		}

		return false;
	}

	/**
	 * Search an email by id.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Email> findOne(final Long id) {
		return emailRepository.findById(id);
	}

	/**
	 * Search for a list of emails with filters.
	 * 
	 * @param personId
	 * @param email
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Email> findAll(Long personId, String email,
		Integer page, Integer size) {

		Page<Email> results = null;

		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 30;
		}
		PageRequest pageable = PageRequest.of(page, size);

		if (personId != null) {
			List<Email> emails = emailRepository.callFuncGetAllEmails(personId);
			results = new PageImpl<>(emails);
		} else if (email != null && !email.isEmpty()) {
			results = emailRepository.findAllByEmailContaining(email, pageable);
		} else {
			results = emailRepository.findAll(pageable);
		}

		return results;
	}

	/**
	 * Update an email in database.
	 * 
	 * @param emailToUpdate
	 * @return
	 */
	public Optional<Email> update(final Email emailToUpdate) {
		log.debug("Starting email update");

		if (emailToUpdate != null && isValidToUpdate(emailToUpdate)) {

			int rowsAffected = emailRepository.update(emailToUpdate);
			if (rowsAffected > 0) {
				log.debug("Email updated with success. " + emailToUpdate.toString());
				logAction(emailToUpdate, CRUDOperation.UPDATE);
				return findOne(emailToUpdate.getEmailId());
			}
		}
		return Optional.empty();
	}

	/**
	 * Verifies if the e-mail exists in another person with reliability level 1 or 2.
	 * 
	 * @param email
	 * @return
	 */
	private boolean alreadyExists(final Email email) {

			List<Email> listFound = emailRepository.findByEmail(
				email.getEmail());
			for (Email e : listFound) {
				// ignores the same e-mail entity
				if (!e.getEmailId().equals(email.getEmailId())) {
					throw new ErrorMessageGenericException(properties.getMessage(
					         "msg.error.email.alreadyExists"));
				}
			}

		return false;
	}

	private void logAction(final Email email, final CRUDOperation operation) {

		Optional<Person> person = personRepository.findById(email.getPersonId());
		if (person.isPresent()) {

			String msg = "";
			if (CRUDOperation.CREATE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.email.create"),
					email.getEmail(), person.get().getName());
			} else if (CRUDOperation.DELETE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.email.delete"),
					email.getEmail(), person.get().getName());
			} else if (CRUDOperation.UPDATE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.email.update"),
					email.getEmail(), person.get().getName());
			}

			if (!msg.isEmpty()) {
				appLogService.insertMsg(msg, "someone");
			}
		}

	}

	private boolean isValidToDelete(final Email email) {

		StringBuilder msg = new StringBuilder("");

		if (validateNotNull(email)) {
			if (email.getEmailId() == null) {
				msg.append(properties.getMessage("msg.error.email.emailId"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		}

		return true;
	}

	private boolean isValidToInsert(final Email email) {

		StringBuilder msg = new StringBuilder("");

		if (validateNotNull(email) && !alreadyExists(email)) {
			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		}
		return true;
	}

	private boolean isValidToUpdate(final Email email) {

		StringBuilder msg = new StringBuilder("");

		if (validateNotNull(email) && !alreadyExists(email)) {
			if (email.getEmailId() == null) {
				msg.append(properties.getMessage("msg.error.email.emailId"));
			}
			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		}

		return true;
	}

	private boolean validateNotNull(final Email email) {
		StringBuilder msg = new StringBuilder("");

		if (email.getPersonId() == null) {
			msg.append(properties.getMessage("msg.error.generic.personId"));
		}
		if (email.getEmail() == null) {
			msg.append(properties.getMessage("msg.error.email.email"));
		}
		if (msg.length() > 0) {
			throw new ErrorMessageGenericException(msg.toString());
		}

		return true;
	}
}
