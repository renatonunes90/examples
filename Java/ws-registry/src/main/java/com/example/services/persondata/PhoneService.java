package com.example.services.persondata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.persondata.Phone;
import com.example.repositories.persondata.PhoneRepository;

/**
 * Service responsible for searching phones through the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class PhoneService {

	@Autowired
	private PhoneRepository phoneRepository;

	/**
	 * Search for a list of phones with filters.
	 * 
	 * @param personId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Phone> findAll(Long personId, Integer page, Integer size) {

		Page<Phone> results = null;

		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 30;
		}
		PageRequest pageable = PageRequest.of(page, size);

		if (personId != null) {
			results = phoneRepository.findAllByPersonId(personId, pageable);
		} else {
			results = phoneRepository.findAll(pageable);
		}

		return results;
	}

}
