package com.example.repositories.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.log.ApplicationLog;

/**
 * JPA repository to access application log entities.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface AppLogRepository extends JpaRepository<ApplicationLog, Long> {

}
