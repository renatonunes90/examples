package com.example.repositories.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.entities.Person;

/**
 * Cutomized repository used to call customized queries in database.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface CustomPersonRepository {

	/**
	 * Get all people by their ids.
	 * 
	 * @param ids
	 * @return
	 */
	List<Person> findAllByIds(List<Long> ids);
}
