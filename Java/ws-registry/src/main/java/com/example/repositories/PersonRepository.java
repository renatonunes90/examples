package com.example.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Person;
import com.example.repositories.custom.CustomPersonRepository;

/**
 * JPA repository to access person entities.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> , CustomPersonRepository {

	/**
	 * Find all people by Account Id.
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	@Query(value = "SELECT p.* FROM PERSON p "
		+ "JOIN ACCOUNT_PERSON pc ON ( pc.ACCOUNT_ID = :ID_CONTA AND pc.PERSON_ID = p.ID_PESSOA ) "
		+ "WHERE p.ACTIVE = :ST_ATIVO", nativeQuery = true)
	List<Person> findAllByAccountIdAndActive(@Param("ID_CONTA") Long accountId,
		@Param("ST_ATIVO") String active);

	Page<Person> findAllByCpfAndActive(String cpf, String active, Pageable pageable);
	
	Page<Person> findAllByNameContainingAndActive(String name, String active, Pageable pageable);
	
	Page<Person> findAllByActive(String active, Pageable pageable);

	Optional<Person> findByPersonIdAndActive(Long personId, String active);
	
	

}
