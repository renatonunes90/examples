package com.example.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity who represents table registry in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

	private Long personId;

	private String genderType;

	private Date birthDate;

	private String name;

	private String cpf;

	private String birthCountry;

	private String birthCity;

	private String fatherName;

	private String motherName;

	private Date updatedDate;

	private String active;


}
