package com.example.repositories.persondata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.domain.PhoneType;
import com.example.entities.persondata.Phone;
import com.example.repositories.persondata.custom.CustomPhoneRepository;

/**
 * JPA repository to access phone entities.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> , CustomPhoneRepository {

	Page<Phone> findAllByNumberContaining(String number, Pageable pageable);

	Page<Phone> findAllByPersonId(Long personId, Pageable pageable);

	Page<Phone> findAllByPhoneType(PhoneType phoneType, Pageable pageable);
}
