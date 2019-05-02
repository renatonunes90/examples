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

	Optional<Person> findByPersonIdAndActive(Long personId, String active);

	@Query(value = "SELECT p.* FROM PERSON p "
		+ "JOIN PERSON_ACCOUNT cp ON ( p.PERSON_ID = cp.PERSON_ID ) "
		+ "JOIN ACCOUNT c ON ( cp.ACCOUNT_ID = c.ACCOUNT_ID AND c.ACTIVE = 'S' ) "
		+ "WHERE p.CPF LIKE '%' || ?1 || '%' AND p.NAME LIKE '%' || ?2 || '%' "
		+ "AND cp.ACCOUNT_ID IN ( SELECT DISTINCT(a.ACCOUNT_ID) FROM AUTHENTICATION a WHERE a.AUTHENTICATION LIKE '%' || ?3 || '%' ) "
		+ "AND p.ACTUVE = 'S'", countQuery = "SELECT COUNT(*) FROM PERSON p " 
		        + "JOIN PERSON_ACCOUNT cp ON ( p.PERSON_ID = cp.PERSON_ID ) " 
		        + "JOIN ACCOUNT c ON ( cp.ACCOUNT_ID = c.ACCOUNT_ID AND c.ACTIVE = 'S' ) " 
		        + "WHERE p.CPF LIKE '%' || ?1 || '%' AND p.NAME LIKE '%' || ?2 || '%' " 
		        + "AND cp.ACCOUNT_ID IN ( SELECT DISTINCT(a.ACCOUNT_ID) FROM AUTHENTICATION a WHERE a.AUTHENTICATION LIKE '%' || ?3 || '%' ) " 
		        + "AND p.ACTUVE = 'S'", nativeQuery = true)
	Page<Person> findAllWithAccountByCpfAndNameAndAuth(String cpf, String name, String auth,
		Pageable pageable);

}
