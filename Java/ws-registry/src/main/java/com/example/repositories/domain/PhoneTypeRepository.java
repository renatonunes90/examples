package com.example.repositories.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.domain.PhoneType;

/**
 * JPA repository to access phone type entities.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface PhoneTypeRepository extends JpaRepository<PhoneType, Long> {

	Optional<PhoneType> findByPhoneTypeText(String phoneTypeText);
}
