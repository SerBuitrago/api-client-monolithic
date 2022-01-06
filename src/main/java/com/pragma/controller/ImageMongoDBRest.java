package com.pragma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pragma.entity.ImageMongoDB;
import com.pragma.service.ImageMongoDBService;

@RestController
@RequestMapping("/api/image-mongodb")
public class ImageMongoDBRest {
	
	@Autowired
	ImageMongoDBService imageMongoDBService;
	
	@GetMapping(value = { "/{id}", "/find/id/{id}" })
	public ResponseEntity<ImageMongoDB> findById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.findById(id));
	}
	
	@GetMapping(value = { "", "/all" })
	public ResponseEntity<List<ImageMongoDB>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.findAll());
	}
	
	@GetMapping(value = { "/all/find/client/{idClient}" })
	public ResponseEntity<List<ImageMongoDB>> findByClient(@PathVariable("idClient") Long idClient) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.findByClient(idClient));
	}
	
	@PostMapping
	public ResponseEntity<ImageMongoDB> save(@ModelAttribute ImageMongoDB imageMongoDB, @RequestParam MultipartFile multipartFile) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.save(imageMongoDB, multipartFile));
	}
	
	@PutMapping
	public ResponseEntity<ImageMongoDB> update(@ModelAttribute ImageMongoDB imageMongoDB, @RequestParam MultipartFile multipartFile) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.update(imageMongoDB, multipartFile));
	}
	
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.delete(id));
	}
}
