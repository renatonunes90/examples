package com.example.repositories.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.domain.MaritalStatus;

/**
 * JPA repository to access marital status entities.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long> {

	Optional<MaritalStatus> findByMaritalStatusText(String maritalStatusText);
}
