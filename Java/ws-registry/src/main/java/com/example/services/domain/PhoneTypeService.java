package com.example.services.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.domain.PhoneType;
import com.example.exception.ErrorMessageGenericException;
import com.example.repositories.domain.PhoneTypeRepository;
import com.example.services.domain.base.AbstractDomainService;


/**
 * Service responsible for searching and editing phone types through the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class PhoneTypeService extends AbstractDomainService<PhoneType> {

	@Autowired
	private PhoneTypeRepository phoneTypeRepository;

	@Override
	protected PhoneTypeRepository getRepository() {
		return phoneTypeRepository;
	}

	@Override
	protected boolean isValidToDelete(PhoneType phoneType) {
		StringBuilder msg = new StringBuilder("");

		if (phoneType != null) {
			if (phoneType.getPhoneTypeId() == null) {
				msg.append(getProperties().getMessage("msg.error.domain.phoneType.phoneTypeId"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		} else {
         throw new ErrorMessageGenericException(getProperties().getMessage("msg.error.domain.phoneType.empty"));
      }

		return true;
	}

	@Override
	protected boolean isValidToInsert(PhoneType phoneType) {
		StringBuilder msg = new StringBuilder("");

		if (phoneType != null && !alreadyExists(phoneType)) {
			if (phoneType.getPhoneTypeText() == null) {
				msg.append(getProperties().getMessage("msg.error.domain.phoneType.phoneTypeText"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		} else {
         throw new ErrorMessageGenericException(getProperties().getMessage("msg.error.domain.phoneType.empty"));
      }
		
		return true;
	}

	@Override
	protected boolean isValidToUpdate(PhoneType phoneType) {
		StringBuilder msg = new StringBuilder("");

		if (phoneType != null && !alreadyExists(phoneType)) {
			if (phoneType.getPhoneTypeId() == null) {
				msg.append(getProperties().getMessage("msg.error.domain.phoneType.phoneTypeId"));
			}
			if (phoneType.getPhoneTypeText() == null) {
				msg.append(getProperties().getMessage("msg.error.domain.phoneType.phoneTypeText"));
			}

			if (msg.length() > 0) {
				throw new ErrorMessageGenericException(msg.toString());
			}
		} else {
         throw new ErrorMessageGenericException(getProperties().getMessage("msg.error.domain.phoneType.empty"));
      }

		return true;
	}

	/**
	 * Verifies if the phone type already exists in database.
	 * 
	 * @param phoneType
	 * @return
	 */
	private boolean alreadyExists(PhoneType phoneType) {

		Optional<PhoneType> found = phoneTypeRepository.findByPhoneTypeText(
			phoneType.getPhoneTypeText());
		if (found.isPresent() && found.get().getPhoneTypeId() != phoneType.getPhoneTypeId()) {
			throw new ErrorMessageGenericException(getProperties().getMessage(
				"msg.error.domain.phoneType.alreadyExists"));
		}

		return false;
	}

}
