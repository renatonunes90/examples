package com.example.repositories.persondata.custom.impl;


import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;

import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.entities.persondata.Phone;
import com.example.repositories.persondata.custom.CustomPhoneRepository;


/**
 * Implementation of CustomPhoneRepository.
 * 
 * @author Renato M B Nunes
 */
public class CustomPhoneRepositoryImpl implements CustomPhoneRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Phone create(Phone phone) {

		Phone created = null;

		if (phone != null) {

			Session session = entityManager.unwrap(Session.class);

			int i = 1;
			ProcedureCall call = session.createStoredProcedureCall("PRD_CREATE_PHONE");
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, String.class, ParameterMode.IN);
			call.registerParameter(i++, String.class, ParameterMode.IN).enablePassingNulls(true);
			call.registerParameter(i++, String.class, ParameterMode.IN).enablePassingNulls(true);
			call.registerParameter(i++, Long.class, ParameterMode.OUT);

			i = 1;
			call.setParameter(i++, phone.getPersonId());
			call.setParameter(i++,
				phone.getPhoneType() != null ? phone.getPhoneType().getPhoneTypeId() : null);
			call.setParameter(i++, phone.getNumber());
			call.setParameter(i++, phone.getDdd());
			call.setParameter(i++, phone.getDdi());

			call.execute();

			Long phoneId = (Long) call.getOutputParameterValue(i);
			if (phoneId != null) {
				phone.setPhoneId(phoneId);
				created = phone;
			}
		}

		return created;
	}

	@Override
	public int customDelete(Phone phone) {

		int rowsAffected = 0;

		if (phone != null) {

			Session session = entityManager.unwrap(Session.class);

			int i = 1;
			ProcedureCall call = session.createStoredProcedureCall("PRD_DELETE_PHONE");
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, Integer.class, ParameterMode.OUT);

			i = 1;
			call.setParameter(i++, phone.getPhoneId());

			call.execute();

			Integer rows = (Integer) call.getOutputParameterValue(i);
			if (rows != null) {
				rowsAffected = rows;
			}
		}

		return rowsAffected;
	}

	@Override
	public int update(Phone phone) {

		int rowsAffected = 0;

		if (phone != null) {

			Session session = entityManager.unwrap(Session.class);

			int i = 1;
			ProcedureCall call = session.createStoredProcedureCall("PRD_UPDATE_PHONE");
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, String.class, ParameterMode.IN);
			call.registerParameter(i++, String.class, ParameterMode.IN).enablePassingNulls(true);
			call.registerParameter(i++, String.class, ParameterMode.IN).enablePassingNulls(true);
			call.registerParameter(i++, Integer.class, ParameterMode.OUT);

			i = 1;
			call.setParameter(i++, phone.getPhoneId());
			call.setParameter(i++, phone.getPersonId());
			call.setParameter(i++,
				phone.getPhoneType() != null ? phone.getPhoneType().getPhoneTypeId() : null);
			call.setParameter(i++, phone.getNumber());
			call.setParameter(i++, phone.getDdd());
			call.setParameter(i++, phone.getDdi());

			call.execute();

			Integer rows = (Integer) call.getOutputParameterValue(i);
			if (rows != null) {
				rowsAffected = rows;
			}
		}

		return rowsAffected;
	}
}
