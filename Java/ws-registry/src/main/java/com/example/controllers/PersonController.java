package com.example.controllers;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PersonInfoDto;
import com.example.entities.Person;
import com.example.exception.EmptyJsonResponse;
import com.example.services.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller responsible for receive, redirect and send HTTP requests to person access endpoints.
 * 
 * @author Renato M B Nunes
 */
@Api(value = "person", produces = "application/json")
@RestController
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping(path = "/")
	@ApiOperation(value = "${ws.swagger.endpoint.person.getAll.description}")
	public ResponseEntity<Page<Person>> getAll(@ApiParam(
				value = "${ws.swagger.endpoint.person.param.cpf.description}") @RequestParam(
					value = "cpf", required = false) final String cpf, @ApiParam(
					value = "${ws.swagger.endpoint.generics.param.page.description}") @RequestParam(
						value = "page", required = false) Integer page, @ApiParam(
							value = "${ws.swagger.endpoint.generics.param.size.description}") @RequestParam(
								value = "size", required = false) Integer size) {
		final Page<Person> result = personService.findAll(cpf, page, size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(path = "{personId}")
	@ApiOperation(value = "${ws.swagger.endpoint.person.getOne.description}")
	public ResponseEntity<?> getOne(@ApiParam(
		value = "${ws.swagger.endpoint.generics.param.personId.description}") @NotNull @PathVariable("personId") Long personId) {

		final Optional<Person> entity = personService.findOne(personId);
		if (!entity.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(entity.get(), HttpStatus.OK);
	}

	@GetMapping(path = "{personId}/info")
	@ApiOperation(value = "${ws.swagger.endpoint.person.getOneWithAllInfo.description}")
	public ResponseEntity<?> getOneWithAllInfo(@ApiParam(
		value = "${ws.swagger.endpoint.generics.param.personId.description}") @NotNull @PathVariable("personId") Long personId) {

		final Optional<Person> entity = personService.findOne(personId);
		if (!entity.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		PersonInfoDto personInfo = personService.findAllPersonInfo(entity.get());
		return new ResponseEntity<>(personInfo, HttpStatus.OK);
	}

}
