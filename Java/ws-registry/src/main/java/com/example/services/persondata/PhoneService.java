package com.example.services.persondata;


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
import com.example.common.builder.domain.PhoneTypeBuilder;
import com.example.entities.Person;
import com.example.entities.domain.PhoneType;
import com.example.entities.persondata.Phone;
import com.example.exception.ErrorMessageGenericException;
import com.example.repositories.PersonRepository;
import com.example.repositories.persondata.PhoneRepository;
import com.example.services.log.AppLogService;


/**
 * Service responsible for searching phones through the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class PhoneService {

	private static final Logger log = LoggerFactory.getLogger("applogger");

	@Autowired
	private PropertiesBean properties;

	@Autowired
	private AppLogService appLogService;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PhoneRepository phoneRepository;

	/**
	 * Create a new phone number in database.
	 * 
	 * @param phoneToCreate
	 * @return
	 */
	public Optional<Phone> create(final Phone phoneToCreate) {
		log.debug("Starting phone number creation");

		Optional<Phone> createdPhone = Optional.empty();
		if (phoneToCreate != null && isValidToInsert(phoneToCreate)) {

			createdPhone = Optional.ofNullable(phoneRepository.create(phoneToCreate));
			if (createdPhone.isPresent()) {
				log.debug("Created with success. Id: " + createdPhone.get().getPhoneId());
				logAction(createdPhone.get(), CRUDOperation.CREATE);

				// to get all properties with default values
				createdPhone = findOne(createdPhone.get().getPhoneId());
			} else {
				log.error("Error creating phone number: " + phoneToCreate.toString());
			}
		}
		return createdPhone;
	}

	/**
	 * Delete a phone number in database.
	 * 
	 * @param phoneToRemove
	 * @return
	 */
	public boolean delete(final Phone phoneToRemove) {
		log.debug("Starting phone delete");

		if (phoneToRemove != null && isValidToDelete(phoneToRemove)) {
			int rowsAffected = phoneRepository.customDelete(phoneToRemove);
			if (rowsAffected > 0) {
				log.debug("Phone number deleted with success. " + phoneToRemove.toString());
				logAction(phoneToRemove, CRUDOperation.DELETE);
				return true;
			}
		}

		return false;
	}

	/**
	 * Search for a list of phones with filters. Return depends of parameters, searching in follow order: personId, or phone number, or phoneTypeId, or
	 * all phone number paginated without filter.
	 * 
	 * @param personId
	 * @param phoneNumber
	 * @param phoneTypeId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Phone> findAll(Long personId, String phoneNumber, Long phoneTypeId, Integer page,
		Integer size) {

		Page<Phone> results = null;

		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 30;
		}
		PageRequest pageable = PageRequest.of(page, size);

		if (personId != null) {
			results = phoneRepository.findAllByPersonId( personId, pageable );
		} else if (phoneNumber != null) {
		   results = phoneRepository.findAllByNumberContaining( phoneNumber, pageable );
		} else if (phoneTypeId != null) {
		   PhoneTypeBuilder builder = new PhoneTypeBuilder();
		   PhoneType pType = builder.withId( phoneTypeId ).build();
         results = phoneRepository.findAllByPhoneType( pType, pageable );
		} else {
			results = phoneRepository.findAll( pageable);
		}

		return results;
	}

	/**
	 * Search a phone by id.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Phone> findOne(final Long id) {
		return phoneRepository.findById(id);
	}

	/**
	 * Update a phone number in database.
	 * 
	 * @param phoneToUpdate
	 * @return
	 */
	public Optional<Phone> update(final Phone phoneToUpdate) {
		log.debug("Starting phone update");

		if (phoneToUpdate != null && isValidToUpdate(phoneToUpdate)) {

			int rowsAffected = phoneRepository.update(phoneToUpdate);
			if (rowsAffected > 0) {
				log.debug("Phone number updated with success. " + phoneToUpdate.toString());
				logAction(phoneToUpdate, CRUDOperation.UPDATE);
				return findOne(phoneToUpdate.getPhoneId());
			}
		}
		return Optional.empty();
	}

	private boolean isValidToDelete(final Phone phone) {

		StringBuilder msg = new StringBuilder("");

		if (phone.getPhoneId() == null) {
			msg.append(properties.getMessage("msg.error.phone.phoneId"));
		}

		if (msg.length() > 0) {
			throw new ErrorMessageGenericException(msg.toString());
		}

		return true;
	}

	private boolean isValidToInsert(final Phone phone) {

		return validateNotNull(phone);
	}

	private boolean isValidToUpdate(final Phone phone) {

		StringBuilder msg = new StringBuilder("");

		if (validateNotNull(phone)) {
			if (phone.getPhoneId() == null) {
				msg.append(properties.getMessage("msg.error.phone.phoneId"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		}

		return true;
	}

	private void logAction(final Phone phone, final CRUDOperation operation) {

		Optional<Person> person = personRepository.findById(phone.getPersonId());
		if (person.isPresent()) {

			String msg = "";
			if (CRUDOperation.CREATE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.phone.create"),
					phone.getNumber(), person.get().getName());
			} else if (CRUDOperation.DELETE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.phone.delete"),
					phone.getNumber(), person.get().getName());
			} else if (CRUDOperation.UPDATE.equals(operation)) {
				msg = String.format(properties.getMessage("msg.success.phone.update"),
					phone.getNumber(), person.get().getName());
			}

			if (!msg.isEmpty()) {
				appLogService.insertMsg(msg, "");
			}
		}

	}

	private boolean validateNotNull(final Phone phone) {
		StringBuilder msg = new StringBuilder("");

		if (phone.getPersonId() == null) {
         msg.append(properties.getMessage("msg.error.generic.personId"));
      }
      if (phone.getPhoneType() == null || phone.getPhoneType().getPhoneTypeId() == null) {
         msg.append(properties.getMessage("msg.error.phone.phoneType"));
      }
      if (phone.getNumber() == null) {
         msg.append(properties.getMessage("msg.error.phone.number"));
      }

		return true;
	}

}
