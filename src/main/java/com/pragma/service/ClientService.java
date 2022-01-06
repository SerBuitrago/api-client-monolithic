package com.pragma.service;

import java.util.List;

import com.pragma.models.entity.Client;

public interface ClientService {
	
	Client findById(Long id);
	
	Client findByTypeAndDocument(String type, Long document);
	
	List<Client> findAll();
	
	List<Client> findByHigherOrEqualsAge(int age);
	
	List<Client> findByType(String type);
	
	Client save(Client cliente);
	
	Client update(Client cliente);
	
	boolean delete(String type, Long document);
	
	boolean delete(Long id);
}
