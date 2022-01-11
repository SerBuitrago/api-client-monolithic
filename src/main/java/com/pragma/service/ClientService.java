package com.pragma.service;

import java.util.List;

import com.pragma.models.dto.ClientDTO;

public interface ClientService {
	
	ClientDTO findById(Long id);
	
	ClientDTO findClientAndImageById(Long id);
	
	ClientDTO findByTypeAndDocument(String type, Long document);
	
	List<ClientDTO> findAll();
	
	List<ClientDTO> findByHigherOrEqualsAge(int age);
	
	List<ClientDTO> findByType(String type);
	
	ClientDTO save(ClientDTO cliente);
	
	ClientDTO update(ClientDTO cliente);
	
	boolean delete(Long id);
}
