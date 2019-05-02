package com.example.repositories.persondata.custom;

import org.springframework.stereotype.Repository;

import com.example.entities.persondata.Email;

/**
 * Cutomized repository used to call email procedures in database.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface CustomEmailRepository {

	/**
	 * Call "PRD_CREATE_EMAIL" procedure to insert a new email in database.
	 * 
	 * @param email
	 * @return New email created.
	 */
	Email create(Email email);

	/**
	 * Call "PRD_DELETE_EMAIL" procedure to delete an email in database.
	 * 
	 * @param email
	 * @return int Number of affected rows.
	 */
	int customDelete(Email email);

	/**
	 * Call "PRD_UPDATE_EMAIL" procedure to update an email in database.
	 * 
	 * @param email
	 * @return int Number of affected rows.
	 */
	int update(Email email);
}
