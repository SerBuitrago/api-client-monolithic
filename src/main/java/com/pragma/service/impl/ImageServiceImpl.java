package com.pragma.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pragma.mapper.ImageMapper;
import com.pragma.models.dto.ImageDTO;
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
	ImageMapper imageMapper;

	@Autowired
	ClientService clientService;

	public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper, ClientService clientService) {
		this.imageRepository = imageRepository;
		this.imageMapper = imageMapper;
		this.clientService = clientService;
	}

	@Override
	public ImageDTO findById(Long id) {
		return imageMapper.toDTO(imageRepository.findById(id)
				.orElseThrow(() -> new PragmaException("No se ha encontrado ninguna imagen con el id " + id + ".")));
	}

	@Override
	public ImageDTO findByClient(Long idClient) {
		ImageDTO image = imageMapper.toDTO(imageRepository.findByClient(idClient));
		if (image == null)
			throw new PragmaException("No se ha encontrado niguna imagen al cliente con el id " + idClient + ".");
		return image;
	}

	@Override
	public List<ImageDTO> findAll() {
		return imageMapper.toDTOList(imageRepository.findAll());
	}

	@Override
	public ImageDTO save(ImageDTO image, MultipartFile multipartFile) {
		ImageValidate.message(image);
		if (!testClient(image.getIdClient()))
			throw new PragmaException("El cliente con el id " + image.getIdClient() + " ya tiene una imagen asignada.");
		clientService.findById(image.getIdClient());
		return saveToUpdate(image, multipartFile, true);
	}

	@Override
	public ImageDTO update(ImageDTO image, MultipartFile multipartFile) {
		ImageValidate.message(image);
		ImageDTO aux = findById(image.getId());
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
		ImageDTO image = null;
		try {
			image = findByClient(idClient);
		} catch (PragmaException e) {
			LOGGER.info("testClient(Long idClient)", e);
		}
		return image == null;
	}

	private ImageDTO image(ImageDTO image, MultipartFile multipartFile) {
		if (multipartFile == null || multipartFile.isEmpty())
			throw new PragmaException("No se ha recibido la imagen.");
		try {
			byte[] bytes = multipartFile.getBytes();
			image.setContentType(multipartFile.getContentType());
			image.setFilename(multipartFile.getOriginalFilename());
			image.setImage(Base64.getEncoder().encodeToString(bytes));
		} catch (IOException e) {
			LOGGER.error("image(Image image, MultipartFile multipartFile)", e);
		}
		return image;
	}

	private ImageDTO saveToUpdate(ImageDTO image, MultipartFile multipartFile, boolean isRegister) {
		image = image(image, multipartFile);
		image = imageMapper.toDTO(imageRepository.save(imageMapper.toEntity(image)));
		if (image == null)
			throw new PragmaException(
					isRegister ? "No se ha registrado la imagen." : "No se ha actualizado la imagen.");
		return image;
	}
}
