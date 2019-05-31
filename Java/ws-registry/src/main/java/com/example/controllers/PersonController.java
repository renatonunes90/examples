package com.example.controllers;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PersonInfoDto;
import com.example.entities.Person;
import com.example.entities.persondata.Email;
import com.example.entities.persondata.Phone;
import com.example.exception.EmptyJsonResponse;
import com.example.services.PersonService;
import com.example.services.persondata.EmailService;
import com.example.services.persondata.PhoneService;

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
	private EmailService emailService;

	@Autowired
	private PersonService personService;

	@Autowired
	private PhoneService phoneService;

	@PostMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.create.description}")
	public ResponseEntity<?> create(@ApiParam(
		value = "${ws.swagger.endpoint.person.param.personObj.description}") @RequestBody @Valid @NotNull Person person) {

		Optional<Person> newPhone = personService.create(person);
		if (!newPhone.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(newPhone.get(), HttpStatus.CREATED);
	}

	@DeleteMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.delete.description}")
	public ResponseEntity<?> delete(@ApiParam(
		value = "${ws.swagger.endpoint.person.param.personObj.description}") @RequestBody @Valid @NotNull Person person) {

		final Optional<Person> currentPerson = personService.findOne(person.getPersonId());
		if (!currentPerson.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (!personService.delete(currentPerson.get())) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
	}

	@GetMapping(path = "/")
	@ApiOperation(value = "${ws.swagger.endpoint.person.getAll.description}")
	public ResponseEntity<Page<Person>> getAll( @ApiParam(
				value = "${ws.swagger.endpoint.person.param.cpf.description}") @RequestParam(
					value = "cpf", required = false) final String cpf, @ApiParam(
						value = "${ws.swagger.endpoint.person.param.name.description}") @RequestParam(
							value = "name", required = false) final String name, @ApiParam(
					value = "${ws.swagger.endpoint.generics.param.page.description}") @RequestParam(
						value = "page", required = false) Integer page, @ApiParam(
							value = "${ws.swagger.endpoint.generics.param.size.description}") @RequestParam(
								value = "size", required = false) Integer size) {
		final Page<Person> result = personService.findAll(cpf, name, page, size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(path = "{personId}/email")
	@ApiOperation(value = "${ws.swagger.endpoint.person.getEmails.description}")
	public ResponseEntity<?> getEmails(@ApiParam(
		value = "${ws.swagger.endpoint.generics.param.personId.description}") @NotNull @PathVariable("personId") Long personId,
		@ApiParam( value = "${ws.swagger.endpoint.generics.param.page.description}") @RequestParam(
								value = "page", required = false) Integer page, @ApiParam(
									value = "${ws.swagger.endpoint.generics.param.size.description}") @RequestParam(
										value = "size", required = false) Integer size) {
		final Page<Email> result = emailService.findAll(personId, null, page,
			size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(path = "/{personId}/phone")
	@ApiOperation(value = "${ws.swagger.endpoint.person.getPhones.description}")
	public ResponseEntity<?> getPhones(@ApiParam(
		value = "${ws.swagger.endpoint.generics.param.personId.description}") @NotNull @PathVariable("personId") Long personId,
		@ApiParam(
			value = "${ws.swagger.endpoint.person.phone.param.phoneTypeId.description}") @RequestParam(
				value = "phoneTypeId", required = false) final Long phoneTypeId, @ApiParam(
					value = "${ws.swagger.endpoint.generics.param.page.description}") @RequestParam(
						value = "page", required = false) Integer page, @ApiParam(
							value = "${ws.swagger.endpoint.generics.param.size.description}") @RequestParam(
								value = "size", required = false) Integer size) {
		final Page<Phone> result = phoneService.findAll(personId, null, phoneTypeId, page, size);
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

	@PutMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.update.description}")
	public ResponseEntity<?> update(@ApiParam(
		value = "${ws.swagger.endpoint.person.param.personObj.description}") @Valid @RequestBody Person person) {

		final Optional<Person> currentPerson = personService.findOne(person.getPersonId());
		if (!currentPerson.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		final Optional<Person> updatedPerson = personService.update(person);
		if (!updatedPerson.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updatedPerson.get(), HttpStatus.OK);
	}

}
