package com.pragma.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pragma.models.dto.ImageDTO;
import com.pragma.models.dto.validate.ImageValidate;
import com.pragma.models.entity.Image;
import com.pragma.repository.ImageRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageService;
import com.pragma.util.Pragma;
import com.pragma.util.exception.PragmaException;

@Service
public class ImageServiceImpl implements ImageService{
	
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
		if(!Pragma.isLong(id))
			throw new PragmaException("El id de la imagen no es valido.");
		Optional<Image> optional = imageRepository.findById(id);
		if(!optional.isPresent())
			throw new PragmaException("No se ha encontrado ninguna imagen con el id "+id+".");
		return optional.get();
	}

	@Override
	public ImageDTO findByClient(Long idClient) {
		ImageDTO image = imageRepository.findByClient(idClient);
		if(image == null)
			throw new PragmaException("No se ha encontrado niguna imagen al cliente con el id "+idClient+".");
		return image;
	}

	@Override
	public List<ImageDTO> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public ImageDTO save(ImageDTO image, MultipartFile multipartFile) {
		ImageValidate.message(image);
		if(!testClient(image.getIdClient()))
			throw new PragmaException("El cliente con el id "+image.getIdClient()+" ya tiene una imagen asignada.");
		clientService.findById(image.getIdClient());
		image = image(image, multipartFile);
		image = imageRepository.save(image);
		if(image == null)
			throw new PragmaException("No se ha registrado la imagen.");
		return image;
	}

	@Override
	public ImageDTO update(ImageDTO image, MultipartFile multipartFile) {
		ImageValidate.message(image);
		Image aux = findById(image.getId());
		if(aux.getIdClient() != image.getIdClient()) {
			clientService.findById(image.getIdClient());
			if(!testClient(image.getIdClient()))
				throw new PragmaException("El cliente con el id "+image.getIdClient()+" ya tiene una imagen asignada.");
		}	
		image = image(image, multipartFile);
		image = imageRepository.save(image);
		if(image == null)
			throw new PragmaException("No se ha actualizado la imagen.");
		return image;
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
		}catch (PragmaException e) {
			LOGGER.info("testClient(Long idClient)", e);
		}
		return image == null;
	}
	
	private ImageDTO image(ImageDTO image, MultipartFile multipartFile) {
		if(multipartFile == null || multipartFile.isEmpty())
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
}
