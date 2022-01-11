package com.pragma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.pragma.models.dto.ImageDTO;
import com.pragma.service.ImageService;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class ImageRest {

	@Autowired
	ImageService imageService;

	public ImageRest(ImageService imageService) {
		this.imageService = imageService;
	}

	@GetMapping(value = { "/{id}", "/find/id/{id}" })
	public ResponseEntity<ImageDTO> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(imageService.findById(id));
	}

	@GetMapping(value = { "/find/client/{idClient}" })
	public ResponseEntity<ImageDTO> findByClient(@PathVariable("idClient") Long idClient) {
		return ResponseEntity.status(HttpStatus.OK).body(imageService.findByClient(idClient));
	}

	@GetMapping(value = { "", "/all" })
	public ResponseEntity<List<ImageDTO>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(imageService.findAll());
	}

	@PostMapping
	public ResponseEntity<ImageDTO> save(@ModelAttribute ImageDTO image,
			@RequestParam("file") MultipartFile multipartFile) {
		return ResponseEntity.status(HttpStatus.CREATED).body(imageService.save(image, multipartFile));
	}

	@PutMapping
	public ResponseEntity<ImageDTO> update(@ModelAttribute ImageDTO image,
			@RequestParam("file") MultipartFile multipartFile) {
		return ResponseEntity.status(HttpStatus.OK).body(imageService.update(image, multipartFile));
	}

	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(imageService.delete(id));
	}
}
