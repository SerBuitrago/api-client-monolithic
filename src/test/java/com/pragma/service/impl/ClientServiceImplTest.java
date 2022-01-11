package com.pragma.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pragma.mapper.ClientMapper;
import com.pragma.mapper.ImageMapper;
import com.pragma.mapper.ImageMongoDBMapper;
import com.pragma.models.dto.ClientDTO;
import com.pragma.models.entity.Client;
import com.pragma.repository.ClientRepository;
import com.pragma.repository.ImageMongoDBRepository;
import com.pragma.repository.ImageRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageMongoDBService;
import com.pragma.service.ImageService;
import com.pragma.util.exception.PragmaException;

public class ClientServiceImplTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImplTest.class);
	
	@Autowired
	ClientRepository clientRepositoryMock = mock(ClientRepository.class);
	@Autowired
	ImageRepository imageRepositoryMock = mock(ImageRepository.class);
	@Autowired
	ImageMongoDBRepository imageMongoDBRepositoryMock = mock(ImageMongoDBRepository.class);

	@Autowired
	ClientMapper clientMapperMock = mock(ClientMapper.class);
	@Autowired
	ImageMapper imageMapperMock = mock(ImageMapper.class);
	@Autowired
	ImageMongoDBMapper imageMongoDBMapperMock = mock(ImageMongoDBMapper.class);

	@Autowired
	ImageService imageService = new ImageServiceImpl(imageRepositoryMock, imageMapperMock, null);
	@Autowired
	ImageMongoDBService imageMongoDBService = new ImageMongoDBServiceImpl(imageMongoDBRepositoryMock, imageMongoDBMapperMock, null);
	@Autowired
	ClientService clientService = new ClientServiceImpl(clientRepositoryMock, clientMapperMock);
	
	
	List<Client> listMock;
	List<ClientDTO> listDTOMock;
	
	@BeforeEach
	void setUp() {
		clientService = new ClientServiceImpl(clientRepositoryMock, clientMapperMock, imageMongoDBService, imageService);
		
		listMock = new ArrayList<>();
		for (int i = 0; i < 5; i++)
			listMock.add(new Client(1L + i, "Jose " + i, "Martinez " + i, "CC", 000001L + i, 10 + i % 2 == 0 ? 0 : 1,
					"Bucaramanga " + i, null, null));
		
		listDTOMock = new ArrayList<>();
		listMock.forEach(e -> listDTOMock.add(new ClientDTO(e.getId(), e.getName(), e.getSubname(), e.getType(),
				e.getDocument(), e.getAge(), e.getCityBirth(), null, null)));
		/**
		 * When
		 */
		// Mapper
		when(clientMapperMock.toDTO(listMock.get(0))).thenReturn(listDTOMock.get(0));
		when(clientMapperMock.toEntity(listDTOMock.get(0))).thenReturn(listMock.get(0));
		// Repository
		when(clientRepositoryMock.findById(1L)).thenReturn(Optional.of(listMock.get(0)));
		when(clientRepositoryMock.findAll()).thenReturn(listMock);
		when(clientRepositoryMock.findByTypeAndDocument("CC", 000001L)).thenReturn(listMock.get(0));
		when(clientRepositoryMock.save(listMock.get(0))).thenReturn(listMock.get(0));
	}
	
	@Test
	void findById() {
		ClientDTO response = null;
		try {
			response = clientService.findById(-1L);
		}catch (PragmaException e) {
			LOGGER.error("findById()", e);
		}
		assertEquals(null, response);
	}
	
	@Test
	void findClientAndImageById() {
		ClientDTO response = null;
		try {
			response = clientService.findClientAndImageById(-1L);
		}catch (PragmaException e) {
			LOGGER.error("findClientAndImageById()", e);
		}
		assertEquals(null, response);
	}
	
	@Test
	void findByTypeAndDocument() {
		ClientDTO response = null;
		try {
			response = clientService.findByTypeAndDocument("None", -1L);
		}catch (PragmaException e) {
			LOGGER.error("findByTypeAndDocument()", e);
		}
		assertEquals(null, response);
	}
	
	@Test
	void save() {
		ClientDTO response = null;
		try {
			response = clientService.save(listDTOMock.get(0));
		}catch (PragmaException e) {
			LOGGER.error("save()", e);
		}
		assertEquals(null, response);
	}
	
	@Test
	void update() {
		ClientDTO response = clientService.update(listDTOMock.get(0));
		assertEquals(1L, response.getId());
	}
}
