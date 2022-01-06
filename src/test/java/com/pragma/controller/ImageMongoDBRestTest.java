package com.pragma.controller;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.models.entity.Client;
import com.pragma.repository.ClientRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageMongoDBService;
import com.pragma.service.impl.ClientServiceImpl;
import com.pragma.service.impl.ImageMongoDBServiceImpl;

public class ImageMongoDBRestTest {
	
	@Autowired
    GridFsTemplate templateMock= mock(GridFsTemplate.class);
	@Autowired
	GridFsOperations operationsMock = mock(GridFsOperations.class);
	
	ClientRepository clientRepositoryMock = mock(ClientRepository.class);

	@Autowired
	ClientService clientService = new ClientServiceImpl(clientRepositoryMock);
	@Autowired
	ImageMongoDBService imageMongoDBService = new ImageMongoDBServiceImpl(templateMock, operationsMock, clientService);
	
	@Autowired
	ImageMongoDBRest imageMongoDBRest = new ImageMongoDBRest(imageMongoDBService);

	ImageMongoDBDTO imageMongoDB = new ImageMongoDBDTO("AAAAA1", 1L, null, null, null, new byte[2]);
	MockMultipartFile multipartFileMock;
	List<ImageMongoDBDTO> listMock = new ArrayList<>();
	List<GridFSFile> listFileMock = new ArrayList<>();

	@BeforeEach
	void setUp() {
		listMock.add(new ImageMongoDBDTO("1", 1L, "image01.jpg", "image01.jpg", "image01.jpg", new byte[2]));
		listMock.add(new ImageMongoDBDTO("2", 21L, "image02.jpg", "image02.jpg", "image02.jpg", new byte[2]));
		listMock.add(new ImageMongoDBDTO("3", 31L, "image03.jpg", "image03.jpg", "image03.jpg", new byte[2]));
		listMock.add(new ImageMongoDBDTO("4", 41L, "image04.jpg", "image04.jpg", "image04.jpg", new byte[2]));
		listMock.add(new ImageMongoDBDTO("5", 51L, "image05.jpg", "image05.jpg", "image05.jpg", new byte[2]));
		
		listFileMock.add(new GridFSFile(null, "image01", 0, 0, null, null));

		Optional<Client> optionalClientMock = Optional
				.of(new Client(1L, "Jose", "Martinez", "CC", 000001L, 10, "Bucaramanga"));

		when(clientRepositoryMock.findById(1L)).thenReturn(optionalClientMock);
		
		when(operationsMock.findOne(new Query(Criteria.where("_id").is("1")))).thenReturn(listFileMock.get(0));
		//when(imageRepositoryMock.findByClient(11L)).thenReturn(listMock.get(0));
		//when(imageRepositoryMock.findAll()).thenReturn(listMock);
		//when(imageRepositoryMock.save(imageMock)).thenReturn(imageMock);
		//when(imageRepositoryMock.save(listMock.get(0))).thenReturn(listMock.get(0));

	}
	
	@Test
	void findById() {
		ResponseEntity<ImageMongoDBDTO> response = imageMongoDBRest.findById("1");
		assertEquals("1", response.getBody().get_id());
		assertThatNoException();
		
	}
	
	@Test
	void findByClient() {}
	
	@Test
	void findAll() {}
	
	@Test
	void save() {}
	
	@Test
	void update() {}
	
	@Test
	void delete() {
	}

}
