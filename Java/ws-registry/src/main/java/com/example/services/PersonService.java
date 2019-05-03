package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.PersonInfoDto;
import com.example.entities.Person;
import com.example.repositories.PersonRepository;
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

	@Autowired
	private EmailService emailService;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PhoneService phoneService;

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
	 * Search for a list of people with filters. 
	 * 
	 * @param cpf
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Person> findAll(String cpf, Integer page, Integer size) {

		Page<Person> results = null;

		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 100;
		}

		PageRequest pageable = PageRequest.of(page, size);

		if ( cpf == null) {
			results = personRepository.findAll(pageable);
		} else {
			results = personRepository.findAllByCpfAndActive(cpf, "S", pageable);
		}

		return results;
	}

	/**
	 * Gets all active people of account.
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Person> findAllByAccountId(Long accountId) {
		return personRepository.findAllByAccountIdAndActive(accountId, "S");
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

		personInfo.setPhones(phoneService.findAll(person.getPersonId(), page, size).getContent());

		return personInfo;
	}

}
