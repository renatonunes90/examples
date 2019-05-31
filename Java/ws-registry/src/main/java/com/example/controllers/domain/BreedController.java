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
import com.example.entities.domain.Breed;
import com.example.services.domain.BreedService;
import com.example.services.domain.base.AbstractDomainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller responsible for receive, redirect and send HTTP requests to breed access endpoints.
 * 
 * @author Renato M B Nunes
 */
@Api(value = "domain/breed", produces = "application/json")
@RestController
@RequestMapping(value = "/domain/breed", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BreedController extends AbstractDomainController<Breed> {

	@Autowired
	private BreedService breedService;

	@PostMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.breed.create.description}")
	public ResponseEntity<?> create(@ApiParam(
		value = "${ws.swagger.endpoint.domain.breed.param.breedObj.description}") @RequestBody @Valid @NotNull Breed breed) {
		return createImpl(breed);
	}

	@DeleteMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.breed.delete.description}")
	public ResponseEntity<?> delete(@ApiParam(
		value = "${ws.swagger.endpoint.domain.breed.param.breedObj.description}") @RequestBody @Valid @NotNull Breed breed) {
		return deleteImpl(breed);
	}

	@GetMapping(path = "/")
	@ApiOperation(value = "${ws.swagger.endpoint.domain.breed.getAll.description}")
	public ResponseEntity<List<Breed>> getAll() {
		return getAllImpl();
	}

	@GetMapping(path = "{breedId}")
	@ApiOperation(value = "${ws.swagger.endpoint.domain.breed.getOne.description}")
	public ResponseEntity<?> getOne(@ApiParam(
		value = "${ws.swagger.endpoint.domain.breed.param.breedId.description}") @NotNull @PathVariable("breedId") Long breedId) {
		return getOneImpl(breedId);
	}

	@PutMapping
	@ApiOperation(value = "${ws.swagger.endpoint.domain.breed.update.description}")
	public ResponseEntity<?> update(@ApiParam(
		value = "${ws.swagger.endpoint.domain.breed.param.breedObj.description}") @Valid @RequestBody Breed breed) {
		return updateImpl(breed);
	}

	@Override
	protected Long getObjectId(Breed object) {
		return object.getBreedId();
	}

	@Override
	protected AbstractDomainService<Breed> getService() {
		return breedService;
	}
}
