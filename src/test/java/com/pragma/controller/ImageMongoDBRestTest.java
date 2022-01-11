package com.pragma.controller;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.pragma.mapper.ClientMapper;
import com.pragma.mapper.ImageMongoDBMapper;
import com.pragma.models.dto.ClientDTO;
import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.models.entity.Client;
import com.pragma.models.entity.ImageMongoDB;
import com.pragma.repository.ClientRepository;
import com.pragma.repository.ImageMongoDBRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageMongoDBService;
import com.pragma.service.impl.ClientServiceImpl;
import com.pragma.service.impl.ImageMongoDBServiceImpl;

public class ImageMongoDBRestTest {
	
	ImageMongoDBRepository imageMongoDBRepositoryMock = mock(ImageMongoDBRepository.class);
	ClientRepository clientRepositoryMock = mock(ClientRepository.class);

	ImageMongoDBMapper imageMongoDBMapperMock = mock(ImageMongoDBMapper.class);
	ClientMapper clientMapper = mock(ClientMapper.class);

	ClientService clientService = new ClientServiceImpl(clientRepositoryMock, clientMapper);
	ImageMongoDBService imageMongoDBService = new ImageMongoDBServiceImpl(imageMongoDBRepositoryMock, imageMongoDBMapperMock, clientService);

	ImageMongoDBRest imageMongoDBRest = new ImageMongoDBRest(imageMongoDBService);

	/**
	 * Other
	 */
	ImageMongoDB imageMongoDBMock;
	ImageMongoDBDTO imageMongoDBDTOMock;
	Client clientMock;
	ClientDTO clientDTOMock;

	MockMultipartFile multipartFileMock;

	List<ImageMongoDB> listMock;
	List<ImageMongoDBDTO> listDTOMock;

	@BeforeEach
	void setUp() {
		/**
		 * Fill
		 */
		imageMongoDBMock = new ImageMongoDB("11", 1L, "ImageMongoDB01.jpg", "ImageMongoDB01.jpg", "ImageMongoDB01.jpg");
		imageMongoDBDTOMock = new ImageMongoDBDTO(imageMongoDBMock.get_id(), imageMongoDBMock.getIdClient(), imageMongoDBMock.getContentType(),
				imageMongoDBMock.getFilename(), imageMongoDBMock.getImage());

		clientMock = new Client(1L, "Jose", "Martinez", "CC", 000001L, 10, "Bucaramanga", null, null);
		clientDTOMock = new ClientDTO(clientMock.getId(), clientMock.getName(), clientMock.getSubname(),
				clientMock.getType(), clientMock.getDocument(), clientMock.getAge(), clientMock.getCityBirth(), null, null);
		// ImageMongoDB
		listMock = new ArrayList<>();
		for (int i = 0; i < 5; i++)
			listMock.add(new ImageMongoDB(""+ (1 + i), 1L + (i + 11), "ImageMongoDB/jpg", "ImageMongoDB0" + i + ".jpg", "AAAAAAA" + i));
		// ImageMongoDB DTO
		listDTOMock = new ArrayList<>();
		listMock.forEach(e -> listDTOMock
				.add(new ImageMongoDBDTO(e.get_id(), e.getIdClient(), e.getContentType(), e.getFilename(), e.getImage())));
		// Optional
		Optional<ImageMongoDB> optionalMock = Optional.of(listMock.get(0));
		Optional<Client> optionalClientMock = Optional.of(clientMock);
		
		/**
		 * When
		 */
		// Client
		when(clientRepositoryMock.findById(1L)).thenReturn(optionalClientMock);
		
		// Mapper ImageMongoDB
		when(imageMongoDBMapperMock.toDTO(listMock.get(0))).thenReturn(listDTOMock.get(0));
		when(imageMongoDBMapperMock.toDTO(imageMongoDBMock)).thenReturn(imageMongoDBDTOMock);
		when(clientMapper.toDTO(clientMock)).thenReturn(clientDTOMock);
		when(imageMongoDBMapperMock.toDTOList(listMock)).thenReturn(listDTOMock);
		when(imageMongoDBMapperMock.toEntity(imageMongoDBDTOMock)).thenReturn(imageMongoDBMock);
		when(imageMongoDBMapperMock.toEntity(listDTOMock.get(0))).thenReturn(listMock.get(0));
		
		// ImageMongoDB CRUD
		when(imageMongoDBRepositoryMock.findById("1")).thenReturn(optionalMock);
		when(imageMongoDBRepositoryMock.findByClient(11L)).thenReturn(listMock.get(0));
		when(imageMongoDBRepositoryMock.findAll()).thenReturn(listMock);
		when(imageMongoDBRepositoryMock.save(imageMongoDBMock)).thenReturn(imageMongoDBMock);
		when(imageMongoDBRepositoryMock.save(listMock.get(0))).thenReturn(listMock.get(0));
		/**
		 * Find ImageMongoDB
		 */
		String path = "C:\\Users\\sergio.barrios\\Pictures\\Sergio Buitrago\\271187632_6982951485056258_370057779146542727_n.jpg";
		try {
			try (FileInputStream fis = new FileInputStream(path)) {
				this.multipartFileMock = new MockMultipartFile("file",
						"271187632_6982951485056258_370057779146542727_n.jpg", MediaType.IMAGE_JPEG_VALUE,
						fis.readAllBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.multipartFileMock = null;
		}
	}

	@Test
	void findById() {
		ResponseEntity<ImageMongoDBDTO> response = imageMongoDBRest.findById("1");
		assertEquals("1", response.getBody().get_id());
		assertThatNoException();
	}

	@Test
	void findByClient() {
		ResponseEntity<ImageMongoDBDTO> response = imageMongoDBRest.findByClient(11L);
		assertEquals("1", response.getBody().get_id());
	}

	@Test
	void findAll() {
		ResponseEntity<List<ImageMongoDBDTO>> response = imageMongoDBRest.findAll();
		assertNotEquals(true, response.getBody().isEmpty());
	}

	@Test
	void save() {
		ResponseEntity<ImageMongoDBDTO> response = imageMongoDBRest.save(imageMongoDBDTOMock,
		multipartFileMock);
		assertNotNull(response.getBody());
		assertThatNoException();
	}

	@Test
	void update() {
		ResponseEntity<ImageMongoDBDTO> response = imageMongoDBRest.update(listDTOMock.get(0),
		multipartFileMock);
		assertNotNull(response.getBody());
		assertThatNoException();
	}
}
