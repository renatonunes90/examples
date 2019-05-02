package com.example.services.log;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.log.ServiceLog;
import com.example.repositories.log.ServiceLogRepository;

/**
 * Service responsible for searching and editing a service log message through
 * the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class ServiceLogService {

    private static final Logger log = LoggerFactory.getLogger("applogger");

    @Autowired
    private ServiceLogRepository serviceLogRepository;

    /**
     * Insert a new service log in database.
     * 
     * @param toCreate
     * @return
     */
    public Optional<ServiceLog> insertLog(final ServiceLog toCreate) {
	log.debug("Starting insert service log message operation");

	Optional<ServiceLog> logMessage = Optional.empty();
	if (toCreate != null) {

	    logMessage = Optional.ofNullable(serviceLogRepository.save(toCreate));
	    if (logMessage.isPresent()) {
		log.debug("Log message saved successfully.");
	    }
	}
	return logMessage;
    }

}
