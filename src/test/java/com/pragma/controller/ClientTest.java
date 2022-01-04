package com.pragma.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.pragma.entity.Client;
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
}
