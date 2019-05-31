package com.example.controllers.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.controllers.domain.base.AbstractDomainController;
import com.example.entities.domain.PhoneType;
import com.example.services.domain.PhoneTypeService;
import com.example.services.domain.base.AbstractDomainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller responsible for receive, redirect and send HTTP requests to phone type access endpoints.
 * 
 * @author Renato M B Nunes
 */
@Api(value = "domain/phonetype", produces = "application/json")
@RestController
@RequestMapping(value = "/domain/phonetype", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PhoneTypeController extends AbstractDomainController<PhoneType> {

	@Autowired
	private PhoneTypeService phoneTypeService;

	@PostMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.phoneType.create.description}")
	public ResponseEntity<?> create(@ApiParam(
		value = "${ws.swagger.endpoint.domain.phoneType.param.phoneTypeObj.description}") @RequestBody @Valid @NotNull PhoneType phoneType) {
		return createImpl(phoneType);
	}

	@DeleteMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.phoneType.delete.description}")
	public ResponseEntity<?> delete(@ApiParam(
		value = "${ws.swagger.endpoint.domain.phoneType.param.phoneTypeObj.description}") @RequestBody @Valid @NotNull PhoneType phoneType) {
		return deleteImpl(phoneType);
	}

	@GetMapping(path = "/")
	@ApiOperation(value = "${ws.swagger.endpoint.domain.phoneType.getAll.description}")
	public ResponseEntity<List<PhoneType>> getAll() {
		return getAllImpl();
	}

	@GetMapping(path = "{phoneTypeId}")
	@ApiOperation(value = "${ws.swagger.endpoint.domain.phoneType.getOne.description}")
	public ResponseEntity<?> getOne(@ApiParam(
		value = "${ws.swagger.endpoint.domain.phoneType.param.phoneTypeId.description}") @NotNull @PathVariable("phoneTypeId") Long phoneTypeId) {
		return getOneImpl(phoneTypeId);
	}

	@PutMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.phoneType.update.description}")
	public ResponseEntity<?> update(@ApiParam(
		value = "${ws.swagger.endpoint.domain.phoneType.param.phoneTypeObj.description}") @Valid @RequestBody PhoneType phoneType) {
		return updateImpl(phoneType);
	}

	@Override
	protected Long getObjectId(PhoneType object) {
		return object.getPhoneTypeId();
	}

	@Override
	protected AbstractDomainService<PhoneType> getService() {
		return phoneTypeService;
	}
}
