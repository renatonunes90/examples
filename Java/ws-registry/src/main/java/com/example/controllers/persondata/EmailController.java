package com.example.controllers.persondata;

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

import com.example.entities.persondata.Email;
import com.example.exception.EmptyJsonResponse;
import com.example.services.persondata.EmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller responsible for receive, redirect and send HTTP requests to e-mail access endpoints.
 * 
 * @author Renato M B Nunes
 */
@Api(value = "person/email", produces = "application/json")
@RestController
@RequestMapping(value = "/person/email", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EmailController {

	@Autowired
	private EmailService emailService;

	@GetMapping(path = "/exists")
	@ApiOperation(value = "${ws.swagger.endpoint.person.email.alreadyExists.description}")
	public ResponseEntity<Boolean> alreadyExists(@ApiParam(
		value = "${ws.swagger.endpoint.person.email.param.email.description}") @RequestParam(
			value = "email", required = true) final String email) {
		return new ResponseEntity<>(emailService.alreadyExists(email), HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.email.create.description}")
	public ResponseEntity<?> create(@ApiParam(
		value = "${ws.swagger.endpoint.person.email.param.emailObj.description}") @RequestBody @Valid @NotNull Email email) {

		Optional<Email> newEmail = emailService.create(email);
		if (!newEmail.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(newEmail.get(), HttpStatus.CREATED);
	}

	@DeleteMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.email.delete.description}")
	public ResponseEntity<?> delete(@ApiParam(
		value = "${ws.swagger.endpoint.person.email.param.emailObj.description}") @RequestBody @Valid @NotNull Email email) {

		final Optional<Email> currentEmail = emailService.findOne(email.getEmailId());
		if (!currentEmail.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (!emailService.delete(currentEmail.get())) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
	}

	@GetMapping(path = "/")
	@ApiOperation(value = "${ws.swagger.endpoint.person.email.getAll.description}")
	public ResponseEntity<?> getAll(@ApiParam(
		value = "${ws.swagger.endpoint.person.email.param.email.description}") @RequestParam(
			value = "email", required = false) final String email, @ApiParam(
				value = "${ws.swagger.endpoint.generics.param.personId.description}") @RequestParam(
					value = "personId", required = false) final Long personId,
		@ApiParam(value = "${ws.swagger.endpoint.generics.param.page.description}") @RequestParam(
			value = "page", required = false) Integer page, @ApiParam(
				value = "${ws.swagger.endpoint.generics.param.size.description}") @RequestParam(
					value = "size", required = false) Integer size) {
		final Page<Email> result = emailService.findAll(personId, email, page,
			size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(path = "{emailId}")
	@ApiOperation(value = "${ws.swagger.endpoint.person.email.getOne.description}")
	public ResponseEntity<?> getOne(@ApiParam(
		value = "${ws.swagger.endpoint.generics.param.emailId.description}") @NotNull @PathVariable("emailId") Long emailId) {

		final Optional<Email> entity = emailService.findOne(emailId);
		if (!entity.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(entity.get(), HttpStatus.OK);
	}

	@PutMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.email.update.description}")
	public ResponseEntity<?> update(@ApiParam(
		value = "${ws.swagger.endpoint.person.email.param.emailObj.description}") @Valid @RequestBody Email email) {
		final Optional<Email> currentEmail = emailService.findOne(email.getEmailId());
		if (!currentEmail.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		final Optional<Email> updatedEmail = emailService.update(email);
		if (!updatedEmail.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updatedEmail.get(), HttpStatus.OK);
	}

}
