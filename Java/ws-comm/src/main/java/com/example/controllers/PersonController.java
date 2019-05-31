package com.example.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Person;
import com.example.exception.EmptyJsonResponse;
import com.example.services.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "person", produces = "application/json")
@RestController
@RequestMapping(value = "/person", method = { RequestMethod.POST })
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.create.description}")
	public ResponseEntity<?> create(@ApiParam(
		value = "${ws.swagger.endpoint.generics.param.accountcredentials.description}") @Valid @NotNull Person newPerson) {

		Person result = personService.createPerson(newPerson);
		if (result == null) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/")
	@ApiOperation(value = "${ws.swagger.endpoint.person.getPersonInfo.description}")
	public ResponseEntity<?> getPersonInfo(@ApiParam(
		value = "${ws.swagger.endpoint.person.param.personId.description}") @RequestParam(
			value = "personId", required = true) final Long personId) {

		Object credentials = personService.getPersonInfo(personId);
		if (credentials == null) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(credentials, HttpStatus.OK);
	}
}
