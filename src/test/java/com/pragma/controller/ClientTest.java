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

import com.pragma.mapper.ClientMapper;
import com.pragma.models.dto.ClientDTO;
import com.pragma.models.entity.Client;
import com.pragma.repository.ClientRepository;
import com.pragma.service.ClientService;
import com.pragma.service.impl.ClientServiceImpl;

public class ClientTest {

	@Autowired
	ClientRepository clientRepositoryMock = mock(ClientRepository.class);

	@Autowired
	ClientMapper clientMapperMock = mock(ClientMapper.class);

	@Autowired
	ClientService clientService = new ClientServiceImpl(clientRepositoryMock, clientMapperMock, null, null);

	@Autowired
	ClientRest clientRest = new ClientRest(clientService);

	Client clientMock;
	ClientDTO clientDTOMock;

	List<Client> listMock;
	List<ClientDTO> listDTOMock;

	@BeforeEach
	void setUp() {
		/**
		 * Fill
		 */
		clientMock = new Client(100L, "Danna", "Mendoza", "TI", 123456789L, 10, "Bucaramanga");
		clientDTOMock = new ClientDTO(clientMock.getId(), clientMock.getName(), clientMock.getSubname(),
				clientMock.getType(), clientMock.getDocument(), clientMock.getAge(), clientMock.getCityBirth());
		
		listMock = new ArrayList<>();
		for (int i = 0; i < 5; i++)
			listMock.add(new Client(1L + i, "Jose " + i, "Martinez " + i, "CC", 000001L + i, 10 + i % 2 == 0 ? 0 : 1,
					"Bucaramanga " + i));
		
		listDTOMock = new ArrayList<>();
		listMock.forEach(e -> listDTOMock.add(new ClientDTO(e.getId(), e.getName(), e.getSubname(), e.getType(),
				e.getDocument(), e.getAge(), e.getCityBirth())));

		Optional<Client> optionalMock = Optional.of(listMock.get(0));

		/**
		 * When
		 */
		// Mapper Client
		when(clientMapperMock.toDTO(listMock.get(0))).thenReturn(listDTOMock.get(0));
		when(clientMapperMock.toDTO(clientMock)).thenReturn(clientDTOMock);
		when(clientMapperMock.toDTOList(listMock)).thenReturn(listDTOMock);
		when(clientMapperMock.toEntity(clientDTOMock)).thenReturn(clientMock);
		when(clientMapperMock.toEntity(listDTOMock.get(0))).thenReturn(listMock.get(0));
		when(clientMapperMock.toEntityList(listDTOMock)).thenReturn(listMock);
		
		// Client
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
		ResponseEntity<ClientDTO> response = clientRest.findById(1L);
		assertEquals(1L, response.getBody().getId());
		assertThatNoException();
	}

	@Test
	void findByTypeAndDocument() {
		ResponseEntity<ClientDTO> response = clientRest.findByTypeAndDocument("CC", 000001L);
		assertEquals(1L, response.getBody().getId());
		assertThatNoException();
	}

	@Test
	void findByHigherOrEqualsAge() {
		ResponseEntity<List<ClientDTO>> response = clientRest.findByHigherOrEqualsAge(1);
		assertNotEquals(true, response.getBody().isEmpty());
		assertThatNoException();
	}

	@Test
	void findAll() {
		ResponseEntity<List<ClientDTO>> response = clientRest.findAll();
		assertNotEquals(true, response.getBody().isEmpty());
	}

	@Test
	void findByType() {
		ResponseEntity<List<ClientDTO>> response = clientRest.findByType("CC");
		assertNotEquals(true, response.getBody().isEmpty());
		assertThatNoException();
	}

	@Test
	void save() {
		ResponseEntity<ClientDTO> response = clientRest.save(clientDTOMock);
		assertNotNull(response.getBody());
		assertThatNoException();
	}

	@Test
	void update() {
		ResponseEntity<ClientDTO> response = clientRest.update(listDTOMock.get(0));
		assertNotNull(response.getBody());
		assertThatNoException();
	}
}
