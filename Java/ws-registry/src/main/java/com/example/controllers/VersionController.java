package com.example.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Versioning API communication.
 * 
 * @author Renato M B Nunes
 */
@Api(value = "version", produces = "application/json")
@RestController
@RequestMapping(path = "/version", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VersionController {

	@GetMapping(path = "/")
	@ApiOperation(value = "${ws.swagger.endpoint.version.getVersion.description}")
	public ResponseEntity<String> getVersion() {
		return ResponseEntity.ok().body("{ \"version\": \"1.0\" }");
	}
}
