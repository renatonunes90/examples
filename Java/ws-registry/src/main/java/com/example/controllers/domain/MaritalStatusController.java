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
import com.example.entities.domain.MaritalStatus;
import com.example.services.domain.MaritalStatusService;
import com.example.services.domain.base.AbstractDomainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller responsible for receive, redirect and send HTTP requests to marital status access endpoints.
 * 
 * @author Renato M B Nunes
 */
@Api(value = "domain/maritalstatus", produces = "application/json")
@RestController
@RequestMapping(value = "/domain/maritalstatus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaritalStatusController extends AbstractDomainController<MaritalStatus> {

	@Autowired
	private MaritalStatusService maritalStatusService;

	@PostMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.maritalStatus.create.description}")
	public ResponseEntity<?> create(@ApiParam(
		value = "${ws.swagger.endpoint.domain.maritalStatus.param.maritalStatusObj.description}") @RequestBody @Valid @NotNull MaritalStatus maritalStatus) {
		return createImpl(maritalStatus);
	}

	@DeleteMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.maritalStatus.delete.description}")
	public ResponseEntity<?> delete(@ApiParam(
		value = "${ws.swagger.endpoint.domain.maritalStatus.param.maritalStatusObj.description}") @RequestBody @Valid @NotNull MaritalStatus maritalStatus) {
		return deleteImpl(maritalStatus);
	}

	@GetMapping(path = "/")
	@ApiOperation(value = "${ws.swagger.endpoint.domain.maritalStatus.getAll.description}")
	public ResponseEntity<List<MaritalStatus>> getAll() {
		return getAllImpl();
	}

	@GetMapping(path = "{maritalStatusId}")
	@ApiOperation(value = "${ws.swagger.endpoint.domain.maritalStatus.getOne.description}")
	public ResponseEntity<?> getOne(@ApiParam(
		value = "${ws.swagger.endpoint.domain.maritalStatus.param.maritalStatusId.description}") @NotNull @PathVariable("maritalStatusId") Long maritalStatusId) {
		return getOneImpl(maritalStatusId);
	}

	@PutMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.maritalStatus.update.description}")
	public ResponseEntity<?> update(@ApiParam(
		value = "${ws.swagger.endpoint.domain.maritalStatus.param.maritalStatusObj.description}") @Valid @RequestBody MaritalStatus maritalStatus) {
		return updateImpl(maritalStatus);
	}

	@Override
	protected Long getObjectId(MaritalStatus object) {
		return object.getMaritalStatusId();
	}

	@Override
	protected AbstractDomainService<MaritalStatus> getService() {
		return maritalStatusService;
	}

}
