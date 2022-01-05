package com.pragma.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pragma.entity.ImageMongoDB;

public interface ImageMongoDBService {

	ImageMongoDB findById(String id);
	
	List<ImageMongoDB> findByClient(Long idClient);
	
	List<ImageMongoDB> findAll();
	
	ImageMongoDB save(ImageMongoDB imageMongoDB, MultipartFile multipartFile);
	
	ImageMongoDB update(ImageMongoDB imageMongoDB, MultipartFile multipartFile);
	
	boolean delete(String id);
}
