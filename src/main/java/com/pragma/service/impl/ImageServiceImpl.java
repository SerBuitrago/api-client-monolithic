package com.pragma.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pragma.models.entity.Image;
import com.pragma.models.entity.validate.ImageValidate;
import com.pragma.repository.ImageRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageService;
import com.pragma.util.exception.PragmaException;

@Service
public class ImageServiceImpl implements ImageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ClientService clientService;

	public ImageServiceImpl() {
	}

	public ImageServiceImpl(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public ImageServiceImpl(ImageRepository imageRepository, ClientService clientService) {
		this.imageRepository = imageRepository;
		this.clientService = clientService;
	}

	@Override
	public Image findById(Long id) {
		return imageRepository.findById(id)
				.orElseThrow(() -> new PragmaException("No se ha encontrado ninguna imagen con el id " + id + "."));
	}

	@Override
	public Image findByClient(Long idClient) {
		Image image = imageRepository.findByClient(idClient);
		if (image == null)
			throw new PragmaException("No se ha encontrado niguna imagen al cliente con el id " + idClient + ".");
		return image;
	}

	@Override
	public List<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public Image save(Image image, MultipartFile multipartFile) {
		System.out.println(image);
		ImageValidate.message(image);
		if (!testClient(image.getIdClient()))
			throw new PragmaException("El cliente con el id " + image.getIdClient() + " ya tiene una imagen asignada.");
		System.out.println("Pasop");
		System.out.println(image.getIdClient());
		clientService.findById(image.getIdClient());
		System.out.println("Paso cliente");
		return saveToUpdate(image, multipartFile, true);
	}

	@Override
	public Image update(Image image, MultipartFile multipartFile) {
		ImageValidate.message(image);
		Image aux = findById(image.getId());
		if (aux.getIdClient() != image.getIdClient()) {
			clientService.findById(image.getIdClient());
			if (!testClient(image.getIdClient()))
				throw new PragmaException(
						"El cliente con el id " + image.getIdClient() + " ya tiene una imagen asignada.");
		}
		return saveToUpdate(image, multipartFile, true);
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		imageRepository.deleteById(id);
		return true;
	}

	private boolean testClient(Long idClient) {
		Image image = null;
		try {
			image = findByClient(idClient);
		} catch (PragmaException e) {
			LOGGER.info("testClient(Long idClient)", e);
		}
		return image == null;
	}

	private Image image(Image image, MultipartFile multipartFile) {
		if (multipartFile == null || multipartFile.isEmpty())
			throw new PragmaException("No se ha recibido la imagen.");
		try {
			byte[] bytes = multipartFile.getBytes();
			String encoder = Base64.getEncoder().encodeToString(bytes);
			image.setContentType(multipartFile.getContentType());
			image.setFilename(multipartFile.getOriginalFilename());
			image.setPhoto(encoder);
		} catch (IOException e) {
			LOGGER.error("image(Image image, MultipartFile multipartFile)", e);
		}
		return image;
	}
	
	private Image saveToUpdate(Image image, MultipartFile multipartFile, boolean isRegister) {
		image = image(image, multipartFile);
		image = imageRepository.save(image);
		if (image == null)
			throw new PragmaException(isRegister ? "No se ha registrado la imagen.": "No se ha actualizado la imagen.");
		System.out.println("Return: "+image);
		return image;
	}
}
