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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

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
	

	@Autowired
	ClientService clientService = new ClientServiceImpl(clientRepositoryMock);
	@Autowired
	ImageService imageService = new ImageServiceImpl("src//main//resources//static//images", imageRepositoryMock, clientService);

	@Autowired
	ImageRest imageRest = new ImageRest(imageService);

	Image imageMock = new Image(11L, 1L, null, null, null);
	MockMultipartFile multipartFileMock;
	List<Image> listMock = new ArrayList<>();

	@BeforeEach
	void setUp() {
		listMock.add(new Image(1L, 1L, "image01.jpg", "image01.jpg", "image01.jpg"));
		listMock.add(new Image(2L, 21L, "image02.jpg", "image02.jpg", "image02.jpg"));
		listMock.add(new Image(3L, 31L, "image03.jpg", "image03.jpg", "image03.jpg"));
		listMock.add(new Image(4L, 41L, "image04.jpg", "image04.jpg", "image04.jpg"));
		listMock.add(new Image(5L, 51L, "image05.jpg", "image05.jpg", "image05.jpg"));

		Optional<Image> optionalMock = Optional.of(listMock.get(0));
		Optional<Client> optionalClientMock = Optional.of(new Client(1L, "Jose", "Martinez", "CC", 000001L, 10, "Bucaramanga"));
		
		when(clientRepositoryMock.findById(1L)).thenReturn(optionalClientMock);

		when(imageRepositoryMock.findById(1L)).thenReturn(optionalMock);
		when(imageRepositoryMock.findByClient(11L)).thenReturn(listMock.get(0));
		when(imageRepositoryMock.findAll()).thenReturn(listMock);
		when(imageRepositoryMock.save(imageMock)).thenReturn(imageMock);
		when(imageRepositoryMock.save(listMock.get(0))).thenReturn(listMock.get(0));

		try {
			try (FileInputStream fis = new FileInputStream(
					"C:\\Users\\sergio.barrios\\Pictures\\Sergio Buitrago\\271187632_6982951485056258_370057779146542727_n.jpg")) {
				this.multipartFileMock = new MockMultipartFile("file", "271187632_6982951485056258_370057779146542727_n.jpg",
						MediaType.IMAGE_JPEG_VALUE, fis.readAllBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.multipartFileMock = null;
		}
	}

	@Test
	void findById() {
		ResponseEntity<Image> response = imageRest.findById(1L);
		assertEquals(1L, response.getBody().getId());
		assertThatNoException();
	}

	@Test
	void findByClient() {
		ResponseEntity<Image> response = imageRest.findByClient(11L);
		assertEquals(1L, response.getBody().getId());
	}

	@Test
	void findAll() {
		ResponseEntity<List<Image>> response = imageRest.findAll();
		assertNotEquals(true, response.getBody().isEmpty());
	}

	@Test
	void save() {
		ResponseEntity<Image> response = imageRest.save(imageMock, multipartFileMock);
		assertNotNull(response.getBody());
		assertThatNoException();
	}

	@Test
	void update() {
		ResponseEntity<Image> response = imageRest.update(listMock.get(0), multipartFileMock);
		assertNotNull(response.getBody());
		assertThatNoException();
	}

	@Test
	void delete() {
	}
}
