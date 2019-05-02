package com.example.controllers.domain.base;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.common.beans.BaseBean;
import com.example.exception.EmptyJsonResponse;
import com.example.services.domain.base.AbstractDomainService;

/**
 * Controller responsible for receive, redirect and send HTTP requests to a domain object access endpoints.
 * 
 * @author Renato M B Nunes
 */
public abstract class AbstractDomainController<T extends BaseBean> {

	/**
	 * Must return the object identifier.
	 * 
	 * @param object
	 * 
	 * @return
	 */
	protected abstract Long getObjectId(T object);

	/**
	 * Must return the service who will communicate wiht repositories.
	 * 
	 * @return
	 */
	protected abstract AbstractDomainService<T> getService();

	/**
	 * Implements the create operation of the controller.
	 * 
	 * @param object
	 * @return
	 */
	protected ResponseEntity<?> createImpl(T object) {
		final Optional<T> newObject = getService().create(object);
		if (!newObject.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(newObject.get(), HttpStatus.CREATED);
	}

	/**
	 * Implements the delete operation of the controller.
	 * 
	 * @param object
	 * @return
	 */
	protected ResponseEntity<?> deleteImpl(T object) {

		final Optional<T> currentObject = getService().findOne(getObjectId(object));
		if (!currentObject.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (!getService().delete(currentObject.get())) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
	}

	/**
	 * Implements the get all operation of the controller.
	 * 
	 * @param object
	 * @return
	 */
	protected ResponseEntity<List<T>> getAllImpl() {
		return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
	}

	/**
	 * Implements the get one operation of the controller.
	 * 
	 * @param object
	 * @return
	 */
	protected ResponseEntity<?> getOneImpl(Long id) {

		final Optional<T> entity = getService().findOne(id);
		if (!entity.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(entity.get(), HttpStatus.OK);
	}

	/**
	 * Implements the update operation of the controller.
	 * 
	 * @param object
	 * @return
	 */
	protected ResponseEntity<?> updateImpl(T object) {
		final Optional<T> currentObject = getService().findOne(getObjectId(object));
		if (!currentObject.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		final Optional<T> updatedObject = getService().update(object);
		if (!updatedObject.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updatedObject.get(), HttpStatus.OK);
	}
}
