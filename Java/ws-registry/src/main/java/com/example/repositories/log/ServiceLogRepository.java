package com.example.repositories.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.log.ServiceLog;

/**
 * JPA repository to access service log entities.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface ServiceLogRepository extends JpaRepository<ServiceLog, Long> {

}
