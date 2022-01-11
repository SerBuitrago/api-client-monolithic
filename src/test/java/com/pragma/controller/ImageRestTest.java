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
import com.pragma.mapper.ImageMapper;
import com.pragma.models.dto.ClientDTO;
import com.pragma.models.dto.ImageDTO;
import com.pragma.models.entity.Client;
import com.pragma.models.entity.Image;
import com.pragma.repository.ClientRepository;
import com.pragma.repository.ImageRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageService;
import com.pragma.service.impl.ClientServiceImpl;
import com.pragma.service.impl.ImageServiceImpl;

public class ImageRestTest {

	ImageRepository imageRepositoryMock = mock(ImageRepository.class);
	ClientRepository clientRepositoryMock = mock(ClientRepository.class);

	ImageMapper imageMapperMock = mock(ImageMapper.class);
	ClientMapper clientMapper = mock(ClientMapper.class);

	ClientService clientService = new ClientServiceImpl(clientRepositoryMock, clientMapper);
	ImageService imageService = new ImageServiceImpl(imageRepositoryMock, imageMapperMock, clientService);

	ImageRest imageRest = new ImageRest(imageService);

	/**
	 * Other
	 */
	Image imageMock;
	ImageDTO imageDTOMock;
	Client clientMock;
	ClientDTO clientDTOMock;

	MockMultipartFile multipartFileMock;

	List<Image> listMock;
	List<ImageDTO> listDTOMock;

	@BeforeEach
	void setUp() {
		/**
		 * Fill
		 */
		imageMock = new Image(11L, 1L, "image01.jpg", "image01.jpg", "image01.jpg");
		imageDTOMock = new ImageDTO(imageMock.getId(), imageMock.getIdClient(), imageMock.getContentType(),
				imageMock.getFilename(), imageMock.getImage());

		clientMock = new Client(1L, "Jose", "Martinez", "CC", 000001L, 10, "Bucaramanga");
		clientDTOMock = new ClientDTO(clientMock.getId(), clientMock.getName(), clientMock.getSubname(),
				clientMock.getType(), clientMock.getDocument(), clientMock.getAge(), clientMock.getCityBirth());
		// Image
		listMock = new ArrayList<>();
		for (int i = 0; i < 5; i++)
			listMock.add(new Image(1L + i, 1L + (i + 10), "image/jpg", "image0" + i + ".jpg", "AAAAAAA" + i));
		// Image DTO
		listDTOMock = new ArrayList<>();
		listMock.forEach(e -> listDTOMock
				.add(new ImageDTO(e.getId(), e.getIdClient(), e.getContentType(), e.getFilename(), e.getImage())));
		// Optional
		Optional<Image> optionalMock = Optional.of(listMock.get(0));
		Optional<Client> optionalClientMock = Optional.of(clientMock);
		/**
		 * When
		 */
		// Client
		when(clientRepositoryMock.findById(1L)).thenReturn(optionalClientMock);
		
		// Mapper Image
		when(imageMapperMock.toDTO(listMock.get(0))).thenReturn(listDTOMock.get(0));
		when(imageMapperMock.toDTO(imageMock)).thenReturn(imageDTOMock);
		when(clientMapper.toDTO(clientMock)).thenReturn(clientDTOMock);
		when(imageMapperMock.toDTOList(listMock)).thenReturn(listDTOMock);
		when(imageMapperMock.toEntity(imageDTOMock)).thenReturn(imageMock);
		when(imageMapperMock.toEntity(listDTOMock.get(0))).thenReturn(listMock.get(0));
		
		// Image CRUD
		when(imageRepositoryMock.findById(1L)).thenReturn(optionalMock);
		when(imageRepositoryMock.findByClient(11L)).thenReturn(listMock.get(0));
		when(imageRepositoryMock.findAll()).thenReturn(listMock);
		when(imageRepositoryMock.save(imageMock)).thenReturn(imageMock);
		when(imageRepositoryMock.save(listMock.get(0))).thenReturn(listMock.get(0));
		
		/**
		 * Find Image
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
		ResponseEntity<ImageDTO> response = imageRest.findById(1L);
		assertEquals(1L, response.getBody().getId());
		assertThatNoException();
	}

	@Test
	void findByClient() {
		ResponseEntity<ImageDTO> response = imageRest.findByClient(11L);
		assertEquals(1L, response.getBody().getId());
	}

	@Test
	void findAll() {
		ResponseEntity<List<ImageDTO>> response = imageRest.findAll();
		assertNotEquals(true, response.getBody().isEmpty());
	}

	@Test
	void save() {
		ResponseEntity<ImageDTO> response = imageRest.save(imageDTOMock,
		multipartFileMock);
		assertNotNull(response.getBody());
		assertThatNoException();
	}

	@Test
	void update() {
		ResponseEntity<ImageDTO> response = imageRest.update(listDTOMock.get(0),
		multipartFileMock);
		assertNotNull(response.getBody());
		assertThatNoException();
	}
}
