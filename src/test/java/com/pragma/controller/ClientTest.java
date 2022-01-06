package com.pragma.controller;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.pragma.models.entity.Client;
import com.pragma.repository.ClientRepository;
import com.pragma.service.ClientService;
import com.pragma.service.impl.ClientServiceImpl;

public class ClientTest {

	ClientRepository clientRepositoryMock = mock(ClientRepository.class);

	@Autowired
	ClientService clientService = new ClientServiceImpl(clientRepositoryMock);

	@Autowired
	ClientRest clientRest = new ClientRest(clientService);

	Client clientMock = new Client(100L, "Danna", "Mendoza", "TI", 123456789L, 10, "Bucaramanga");
    List<Client> listMock = new ArrayList<>();
    
    @BeforeEach
	void setUp() {
    	listMock.add(new Client(1L, "Jose", "Martinez", "CC", 000001L, 10, "Bucaramanga"));
    	listMock.add(new Client(2L, "Carlos", "Lopez", "CC", 000002L, 21, "Cucuta"));
    	listMock.add(new Client(3L, "Juan", "Mendoza", "CC", 000003L, 30, "Bogota"));
    	listMock.add(new Client(4L, "Stives", "Barrios", "CC", 000004L, 40, "Cali"));
    	listMock.add(new Client(5L, "Sergio", "Buitrago", "CC", 000005L, 20, "Bucaramanga"));
    	
    	Optional<Client> optionalMock = Optional.of(listMock.get(0));
    	
    	when(clientRepositoryMock.findById(1L)).thenReturn(optionalMock);
    	when(clientRepositoryMock.findByTypeAndDocument("CC", 000001L)).thenReturn(listMock.get(0));
        when(clientRepositoryMock.findAll()).thenReturn(listMock);
        when(clientRepositoryMock.findByHigherOrEqualsAge(1)).thenReturn(listMock);
        when(clientRepositoryMock.findByType("CC")).thenReturn(listMock);
        when(clientRepositoryMock.save(clientMock)).thenReturn(clientMock);
        when(clientRepositoryMock.save(listMock.get(0))).thenReturn(listMock.get(0));
    }
    
    @Test
	void findById() {
    	ResponseEntity<Client> response = clientRest.findById(1L);
    	assertEquals(1L, response.getBody().getId());
		assertThatNoException();
    }
    
    @Test
	void findByTypeAndDocument() {
    	ResponseEntity<Client> response = clientRest.findByTypeAndDocument("CC", 000001L);
    	assertEquals(1L, response.getBody().getId());
    	assertThatNoException();
    }
    
    @Test
	void findByHigherOrEqualsAge() {
    	ResponseEntity<List<Client>> response = clientRest.findByHigherOrEqualsAge(1);
    	assertNotEquals(true, response.getBody().isEmpty());
    	assertThatNoException();
    }
    
    @Test
	void findAll() {
    	ResponseEntity<List<Client>> response = clientRest.findAll();
		assertNotEquals(true, response.getBody().isEmpty());
    }
    
    @Test
	void findByType() {
    	ResponseEntity<List<Client>> response = clientRest.findByType("CC");
		assertNotEquals(true, response.getBody().isEmpty());
		assertThatNoException();
    }
    
    @Test
	void save() {
    	ResponseEntity<Client> response = clientRest.save(clientMock);
    	assertNotNull(response.getBody());
		assertThatNoException();
    }
    
    @Test
	void update() {
    	ResponseEntity<Client> response = clientRest.update(listMock.get(0));
    	assertNotNull(response.getBody());
		assertThatNoException();
    }

    @Test
	void delete() {
    	
    }
}
