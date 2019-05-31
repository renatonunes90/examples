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

import com.example.entities.persondata.Phone;
import com.example.exception.EmptyJsonResponse;
import com.example.services.persondata.PhoneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller responsible for receive, redirect and send HTTP requests to phone access endpoints.
 * 
 * @author Renato M B Nunes
 */
@Api(value = "person/phone", produces = "application/json")
@RestController
@RequestMapping(value = "/person/phone", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PhoneController {

	@Autowired
	private PhoneService phoneService;

	@PostMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.phone.create.description}")
	public ResponseEntity<?> create(@ApiParam(
		value = "${ws.swagger.endpoint.person.phone.param.phoneObj.description}") @RequestBody @Valid @NotNull Phone phone) {

		Optional<Phone> newPhone = phoneService.create(phone);
		if (!newPhone.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(newPhone.get(), HttpStatus.CREATED);
	}

	@DeleteMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.phone.delete.description}")
	public ResponseEntity<?> delete(@ApiParam(
		value = "${ws.swagger.endpoint.person.phone.param.phoneObj.description}") @RequestBody @Valid @NotNull Phone phone) {

		final Optional<Phone> currentPhone = phoneService.findOne(phone.getPhoneId());
		if (!currentPhone.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (!phoneService.delete(currentPhone.get())) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
	}

	@GetMapping(path = "/")
	@ApiOperation(value = "${ws.swagger.endpoint.person.phone.getAll.description}")
	public ResponseEntity<?> getAll(@ApiParam(
		value = "${ws.swagger.endpoint.person.phone.param.phoneNumber.description}") @RequestParam(
			value = "phoneNumber", required = false) final String phoneNumber, @ApiParam(
				value = "${ws.swagger.endpoint.generics.param.page.description}") @RequestParam(
					value = "page", required = false) Integer page, @ApiParam(
						value = "${ws.swagger.endpoint.generics.param.size.description}") @RequestParam(
							value = "size", required = false) Integer size) {
		final Page<Phone> result = phoneService.findAll(null, phoneNumber, null, page, size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(path = "{phoneId}")
	@ApiOperation(value = "${ws.swagger.endpoint.person.phone.getOne.description}")
	public ResponseEntity<?> getOne(@ApiParam(
		value = "${ws.swagger.endpoint.generics.param.phoneId.description}") @NotNull @PathVariable("phoneId") Long phoneId) {

		final Optional<Phone> entity = phoneService.findOne(phoneId);
		if (!entity.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(entity.get(), HttpStatus.OK);
	}

	@PutMapping
	@ApiOperation(value = "${ws.swagger.endpoint.person.phone.update.description}")
	public ResponseEntity<?> update(@ApiParam(
		value = "${ws.swagger.endpoint.person.phone.param.phoneObj.description}") @Valid @RequestBody Phone phone) {

		final Optional<Phone> currentPhone = phoneService.findOne(phone.getPhoneId());
		if (!currentPhone.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		final Optional<Phone> updatedPhone = phoneService.update(phone);
		if (!updatedPhone.isPresent()) {
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updatedPhone.get(), HttpStatus.OK);
	}
}
