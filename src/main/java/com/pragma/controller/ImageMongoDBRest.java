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

import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.service.ImageMongoDBService;

@RestController
@RequestMapping("/api/image-mongodb")
public class ImageMongoDBRest {
	
	@Autowired
	ImageMongoDBService imageMongoDBService;
	
	public ImageMongoDBRest(ImageMongoDBService imageMongoDBService) {
		this.imageMongoDBService = imageMongoDBService;
	}

	@GetMapping(value = { "/{id}", "/find/id/{id}" })
	public ResponseEntity<ImageMongoDBDTO> findById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.findById(id));
	}
	
	@GetMapping(value = { "", "/all" })
	public ResponseEntity<List<ImageMongoDBDTO>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.findAll());
	}
	
	@GetMapping(value = { "/all/find/client/{idClient}" })
	public ResponseEntity<List<ImageMongoDBDTO>> findByClient(@PathVariable("idClient") Long idClient) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.findByClient(idClient));
	}
	
	@PostMapping
	public ResponseEntity<ImageMongoDBDTO> save(@ModelAttribute ImageMongoDBDTO imageMongoDB, @RequestParam("fileMongoDB") MultipartFile multipartFile) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.save(imageMongoDB, multipartFile));
	}
	
	@PutMapping
	public ResponseEntity<ImageMongoDBDTO> update(@ModelAttribute ImageMongoDBDTO imageMongoDB, @RequestParam("fileMongoDB") MultipartFile multipartFile) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.update(imageMongoDB, multipartFile));
	}
	
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.delete(id));
	}
}
