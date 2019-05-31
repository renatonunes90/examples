package com.example.comm.registry;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.entities.Person;

/**
 * Requets interface for ws-registry services
 * 
 * @author Renato M B Nunes
 */
@FeignClient(name = "ws-siu-feign", url = "${registry.resource.uri}")
public interface WsRegistryFeignClient {

	@PostMapping(path = "person/")
	Person createPerson(@RequestBody Person person);

	@GetMapping(path = "person/{personId}/info")
	Object getRegisterInfo(@PathVariable("personId") Long personId);
}
