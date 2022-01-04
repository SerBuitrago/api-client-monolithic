package com.pragma.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pragma.entity.Image;

public interface ImageService {

	Image findById(Long id);
	
	Image findByClient(Long idClient);
	
	List<Image> findAll();
	
	Image save(Image image, MultipartFile multipartFile);
	
	Image update(Image image, MultipartFile multipartFile);
	
	boolean delete(Long id);
}
