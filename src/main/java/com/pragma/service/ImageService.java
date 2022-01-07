package com.pragma.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pragma.models.dto.ImageDTO;

public interface ImageService {

	ImageDTO findById(Long id);
	
	ImageDTO findByClient(Long idClient);
	
	List<ImageDTO> findAll();
	
	ImageDTO save(ImageDTO image, MultipartFile multipartFile);
	
	ImageDTO update(ImageDTO image, MultipartFile multipartFile);
	
	boolean delete(Long id);
}
