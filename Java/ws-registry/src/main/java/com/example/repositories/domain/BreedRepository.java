package com.example.repositories.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.domain.Breed;


/**
 * JPA repository to access breed entities.
 * 
 * @author Renato M B Nunes
 */
@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

	Optional<Breed> findByBreedText(String breedText);
}
