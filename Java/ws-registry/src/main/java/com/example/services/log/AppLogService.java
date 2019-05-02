package com.example.services.log;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.log.ApplicationLog;
import com.example.repositories.log.AppLogRepository;

/**
 * Service responsible for searching and editing an application log message
 * through the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class AppLogService {

    private static final Logger log = LoggerFactory.getLogger("applogger");

    @Autowired
    private AppLogRepository appLogRepository;

    /**
     * Inset a new application log message in database.
     * 
     * @param message
     * @param username
     * @return
     */
    public Optional<ApplicationLog> insertMsg(final String message, final String username) {
	log.debug("Starting insert application log message operation");

	Optional<ApplicationLog> logMessage = Optional.empty();
	if (message != null && !message.isEmpty()) {

	    ApplicationLog appLog = new ApplicationLog();
	    appLog.setDate(new Date());
	    appLog.setMessage(message);
	    appLog.setUsername(username);

	    logMessage = Optional.ofNullable(appLogRepository.save(appLog));
	    if (logMessage.isPresent()) {
		log.debug("Log message saved successfully.");
	    }
	}
	return logMessage;
    }

}
