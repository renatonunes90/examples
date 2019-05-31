package com.example.comm.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entities.Person;

import feign.FeignException;

/**
 * Executes the communication with ws-registry.
 * 
 * @author Renato M B Nunes
 */
@Component
public class RegistryRequester {

	private static final Logger logger = LoggerFactory.getLogger("applogger");

	@Autowired
	private WsRegistryFeignClient wsSiuClient;

	/**
	 * Requests person creation on ws-registry.
	 * 
	 * @param person
	 * @return
	 */
	public Person createPerson(Person person) {

	   logger.debug("Registrando pessoa no ws-registry");

	   Person newPerson = null;
      try {
         newPerson = wsSiuClient.createPerson(person);
      } catch (FeignException e) {
         logger.error("Exceção em RegistryRequester.createPerson " + e.getMessage());
      } catch (Exception e) {
         e.printStackTrace();
      }

      return newPerson;
	}

	/**
	 * Gets all register info of a person.
	 * 
	 * @param personId
	 * @return
	 */
	public Object getRegisterInfo(Long personId) {

		Object registerInfo = null;
		try {
			registerInfo = wsSiuClient.getRegisterInfo( personId);
		} catch (FeignException e) {
			logger.error("Exceção em RegistryRequester.getRegisterInfo " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return registerInfo;
	}
}
