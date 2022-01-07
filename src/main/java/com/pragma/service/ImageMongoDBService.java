package com.pragma.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pragma.models.dto.ImageMongoDBDTO;

public interface ImageMongoDBService {

	ImageMongoDBDTO findById(String id);
	
	ImageMongoDBDTO findByClient(Long idClient);
	
	List<ImageMongoDBDTO> findAll();
	
	ImageMongoDBDTO save(ImageMongoDBDTO imageMongoDB, MultipartFile multipartFile);
	
	ImageMongoDBDTO update(ImageMongoDBDTO imageMongoDB, MultipartFile multipartFile);
	
	boolean delete(String id);
}
