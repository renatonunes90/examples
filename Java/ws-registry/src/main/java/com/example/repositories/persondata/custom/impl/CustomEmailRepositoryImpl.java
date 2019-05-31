package com.example.repositories.persondata.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;

import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.entities.persondata.Email;
import com.example.repositories.persondata.custom.CustomEmailRepository;

/**
 * Implementation of CustomEmailRepository.
 * 
 * @author Renato M B Nunes
 */
public class CustomEmailRepositoryImpl implements CustomEmailRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Email create(Email email) {

		Email created = null;

		if (email != null) {

			Session session = entityManager.unwrap(Session.class);

			int i = 1;
			ProcedureCall call = session.createStoredProcedureCall("PRD_CREATE_EMAIL");
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, String.class, ParameterMode.IN);
			call.registerParameter(i++, Long.class, ParameterMode.OUT);

			i = 1;
			call.setParameter(i++, email.getPersonId());
			call.setParameter(i++, email.getEmail());

			call.execute();

			Long emailId = (Long) call.getOutputParameterValue(i);
			if (emailId != null) {
				email.setEmailId(emailId);
				created = email;
			}
		}

		return created;
	}

	@Override
	public int customDelete(Email email) {

		int rowsAffected = 0;

		if (email != null) {

			Session session = entityManager.unwrap(Session.class);

			int i = 1;
			ProcedureCall call = session.createStoredProcedureCall("PRD_DELETE_EMAIL");
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, Integer.class, ParameterMode.OUT);

			i = 1;
			call.setParameter(i++, email.getEmailId());

			call.execute();

			Integer rows = (Integer) call.getOutputParameterValue(i);
			if (rows != null) {
				rowsAffected = rows;
			}
		}

		return rowsAffected;
	}

	@Override
	public int update(Email email) {

		int rowsAffected = 0;

		if (email != null) {

			Session session = entityManager.unwrap(Session.class);

			int i = 1;
			ProcedureCall call = session.createStoredProcedureCall("PRD_UPDATE_EMAIL");
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, Long.class, ParameterMode.IN);
			call.registerParameter(i++, String.class, ParameterMode.IN);
			call.registerParameter(i++, Integer.class, ParameterMode.OUT);

			i = 1;
			call.setParameter(i++, email.getEmailId());
			call.setParameter(i++, email.getPersonId());
			call.setParameter(i++, email.getEmail());

			call.execute();

			Integer rows = (Integer) call.getOutputParameterValue(i);
			if (rows != null) {
				rowsAffected = rows;
			}
		}

		return rowsAffected;
	}
}
