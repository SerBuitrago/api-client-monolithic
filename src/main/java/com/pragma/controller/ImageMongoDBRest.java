package com.pragma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping
	public ResponseEntity<ImageMongoDB> save(@ModelAttribute ImageMongoDB imageMongoDB, @RequestParam MultipartFile multipartFile) {
		return ResponseEntity.status(HttpStatus.OK).body(imageMongoDBService.save(imageMongoDB, multipartFile));
	}

}
