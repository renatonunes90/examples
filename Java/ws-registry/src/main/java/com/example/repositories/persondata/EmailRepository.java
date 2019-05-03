package com.example.repositories.persondata;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.persondata.Email;
import com.example.repositories.persondata.custom.CustomEmailRepository;

/**
 * JPA repository to access email entities.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> , CustomEmailRepository {

	/**
	 * Call "FUN_GET_ALL_EMAILS" function to search for person emails.
	 * 
	 * @param personId
	 * @return
	 */
	@Transactional(readOnly = true)
	@Query(
		value = "SELECT * FROM TABLE(REGISTRY.FUN_GET_ALL_EMAILS(:ID_PESSOA))",
		nativeQuery = true)
	List<Email> callFuncGetAllEmails(@Param("ID_PESSOA") Long personId);

	List<Email> findByEmail(String email);

	Page<Email> findAllByEmailContaining(String email, Pageable pageable);

}
