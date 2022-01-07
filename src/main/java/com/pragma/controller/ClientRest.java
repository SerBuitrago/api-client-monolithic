package com.pragma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.models.dto.ClientDTO;
import com.pragma.service.ClientService;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "*")
public class ClientRest {

	@Autowired
	ClientService clientService;
	
	public ClientRest(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping(value = { "/{id}", "/find/id/{id}" })
	public ResponseEntity<ClientDTO> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(clientService.findById(id));
	}
	
	@GetMapping(value = { "/find/type/{type}/document/{document}" })
	public ResponseEntity<ClientDTO> findByTypeAndDocument(@PathVariable("type") String type, @PathVariable("document") Long document) {
		return ResponseEntity.status(HttpStatus.OK).body(clientService.findByTypeAndDocument(type, document));
	}
	
	@GetMapping(value = { "", "/all" })
	public ResponseEntity<List<ClientDTO>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(clientService.findAll());
	}
	
	@GetMapping(value = {"/all/find/age/{age}" })
	public ResponseEntity<List<ClientDTO>> findByHigherOrEqualsAge(@PathVariable("age") int age) {
		return ResponseEntity.status(HttpStatus.OK).body(clientService.findByHigherOrEqualsAge(age));
	}
	
	@GetMapping(value = {"/all/find/type/{type}" })
	public ResponseEntity<List<ClientDTO>> findByType(@PathVariable("type") String type) {
		return ResponseEntity.status(HttpStatus.OK).body(clientService.findByType(type));
	}
	
	@PostMapping
	public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO client) {
		return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(client));
	}
	
	@PutMapping
	public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO client) {
		return ResponseEntity.status(HttpStatus.OK).body(clientService.update(client));
	}
	
	@DeleteMapping(value = { "/delete/id/{id}" })
	public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(clientService.delete(id));
	}
}
