package com.example.services.domain.base;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.common.beans.BaseBean;
import com.example.common.beans.PropertiesBean;

/**
 * Service responsible for searching and editing a domain object through the repositories.
 * 
 * @author Renato M B Nunes
 */
public abstract class AbstractDomainService<T extends BaseBean> {

	private static final Logger log = LoggerFactory.getLogger("applogger");

	@Autowired
	protected PropertiesBean properties;

	/**
	 * Create a new object in database.
	 * 
	 * @param toCreate
	 * @return
	 */
	public Optional<T> create(final T toCreate) {
		log.debug("Starting create operation");

		Optional<T> createdMaritalStatus = Optional.empty();
		if (toCreate != null && isValidToInsert(toCreate)) {

			createdMaritalStatus = Optional.ofNullable(getRepository().save(toCreate));
			if (createdMaritalStatus.isPresent()) {
				log.debug("Object created with success. " + createdMaritalStatus.get().toString());
			}
		}
		return createdMaritalStatus;
	}

	/**
	 * Delete an object in database.
	 * 
	 * @param toRemove
	 * @return
	 */
	public boolean delete(final T toRemove) {
		log.debug("Starting delete operation");

		if (toRemove != null && isValidToDelete(toRemove)) {
			getRepository().delete(toRemove);

			log.debug("Object deleted with success. " + toRemove.toString());
			return true;
		}

		return false;
	}

	/**
	 * Search for an object by id.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<T> findOne(final Long id) {
		return getRepository().findById(id);
	}

	/**
	 * Search for all objects.
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return getRepository().findAll();
	}

	/**
	 * Update an object in database.
	 * 
	 * @param toUpdate
	 * @return
	 */
	public Optional<T> update(final T toUpdate) {
		log.debug("Starting update operation");

		if (toUpdate != null && isValidToUpdate(toUpdate)) {

			T updated = getRepository().save(toUpdate);
			if (updated != null) {
				log.debug("Object updated with success. " + toUpdate.toString());
				return Optional.of(updated);
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @return Object with the string constants.
	 */
	protected PropertiesBean getProperties() {
		return properties;
	}

	/**
	 * Must return an instance of the repository.
	 * 
	 * @return
	 */
	protected abstract JpaRepository<T, Long> getRepository();

	/**
	 * Must validate if the register is able to be deleted.
	 * 
	 * @param object
	 * @return
	 */
	protected abstract boolean isValidToDelete(final T object);

	/**
	 * Must validate if the register is able to be inserted.
	 * 
	 * @param object
	 * @return
	 */
	protected abstract boolean isValidToInsert(final T object);

	/**
	 * Must validate if the register is able to be updated.
	 * 
	 * @param object
	 * @return
	 */
	protected abstract boolean isValidToUpdate(final T object);
}
