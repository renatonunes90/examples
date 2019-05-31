package com.example.repositories.persondata.custom;

import org.springframework.stereotype.Repository;

import com.example.entities.persondata.Phone;

/**
 * Cutomized repository used to call phone procedures in database.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface CustomPhoneRepository {

	/**
	 * Call "PRD_CREATE_PHONE" procedure to insert a new phone in database.
	 * 
	 * @param phone
	 * @return New phone created.
	 */
	Phone create(Phone phone);

	/**
	 * Call "PRD_DELETE_PHONE" procedure to delete a phone in database.
	 * 
	 * @param phone
	 * @return int Number of affected rows.
	 */
	int customDelete(Phone phone);

	/**
	 * Call "PRD_UPDATE_PHONE" procedure to update a phone in database.
	 * 
	 * @param phone
	 * @return int Number of affected rows.
	 */
	int update(Phone phone);
}
