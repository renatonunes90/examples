package com.example.services.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.domain.Breed;
import com.example.exception.ErrorMessageGenericException;
import com.example.repositories.domain.BreedRepository;
import com.example.services.domain.base.AbstractDomainService;


/**
 * Service responsible for searching and editing breed through the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class BreedService extends AbstractDomainService<Breed> {

	@Autowired
	private BreedRepository breedRepository;

	@Override
	protected JpaRepository<Breed, Long> getRepository() {
		return breedRepository;
	}

	@Override
	protected boolean isValidToDelete(Breed breed) {
		StringBuilder msg = new StringBuilder("");

		if (breed != null) {
			if (breed.getBreedId() == null) {
				msg.append(getProperties().getMessage("msg.error.domain.breed.breedId"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		} else {
		   throw new ErrorMessageGenericException(getProperties().getMessage("msg.error.domain.breed.empty"));
		}
		
		return true;
	}

	@Override
	protected boolean isValidToInsert(Breed breed) {
		StringBuilder msg = new StringBuilder("");

		if (breed != null && !alreadyExists(breed)) {
			if (breed.getBreedText() == null) {
				msg.append(getProperties().getMessage("msg.error.domain.breed.breedText"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		} else {
         throw new ErrorMessageGenericException(getProperties().getMessage("msg.error.domain.breed.empty"));
      }
		
		return true;
	}

	@Override
	protected boolean isValidToUpdate(Breed breed) {
		StringBuilder msg = new StringBuilder("");

		if ( breed != null && !alreadyExists(breed)) {
			if (breed.getBreedId() == null) {
				msg.append(getProperties().getMessage("msg.error.domain.breed.breedId"));
			}
			if (breed.getBreedText() == null) {
				msg.append(getProperties().getMessage("msg.error.domain.breed.breedText"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		} else {
         throw new ErrorMessageGenericException(getProperties().getMessage("msg.error.domain.breed.empty"));
      }

		return true;
	}

	/**
	 * Verifies if the breed already exists in database.
	 * 
	 * @param breed
	 * @return
	 */
	private boolean alreadyExists(Breed breed) {

		Optional<Breed> found = breedRepository.findByBreedText(breed.getBreedText());
		if (found.isPresent() && found.get().getBreedId() != breed.getBreedId()) {
			throw new ErrorMessageGenericException(getProperties().getMessage(
				"msg.error.domain.breed.alreadyExists"));
		}

		return false;
	}
}
