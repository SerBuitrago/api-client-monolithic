package com.pragma.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pragma.mapper.ImageMongoDBMapper;
import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.models.entity.validate.ImageMongoDBValidate;
import com.pragma.repository.ImageMongoDBRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageMongoDBService;
import com.pragma.util.exception.PragmaException;

@Service
public class ImageMongoDBServiceImpl implements ImageMongoDBService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageMongoDBServiceImpl.class);

	@Autowired
	ImageMongoDBRepository imageMongoDBRepository;
	@Autowired
	ImageMongoDBMapper imageMongoDBMapper;

	@Autowired
	private ClientService clientService;
	
	public ImageMongoDBServiceImpl() {
	}

	public ImageMongoDBServiceImpl(ImageMongoDBRepository imageMongoDBRepository) {
		this.imageMongoDBRepository = imageMongoDBRepository;
	}

	public ImageMongoDBServiceImpl(ImageMongoDBRepository imageMongoDBRepository, ImageMongoDBMapper imageMongoDBMapper,
			ClientService clientService) {
		this.imageMongoDBRepository = imageMongoDBRepository;
		this.imageMongoDBMapper = imageMongoDBMapper;
		this.clientService = clientService;
	}

	@Override
	public ImageMongoDBDTO findById(String id) {
		return imageMongoDBMapper.toDTO(imageMongoDBRepository.findById(id)
				.orElseThrow(() -> new PragmaException("No se ha encontrado ninguna imagen con el id " + id + ".")));
	}

	@Override
	public ImageMongoDBDTO findByClient(Long idClient) {
		ImageMongoDBDTO image = imageMongoDBMapper.toDTO(imageMongoDBRepository.findByClient(idClient));
		if (image == null)
			throw new PragmaException("No se ha encontrado niguna imagen al cliente con el id " + idClient + ".");
		return image;
	}

	@Override
	public List<ImageMongoDBDTO> findAll() {
		return imageMongoDBMapper.toDTOList(imageMongoDBRepository.findAll());
	}

	@Override
	public ImageMongoDBDTO save(ImageMongoDBDTO imageMongoDB, MultipartFile multipartFile) {
		ImageMongoDBValidate.message(imageMongoDB);
		if (!testClient(imageMongoDB.getIdClient()))
			throw new PragmaException(
					"El cliente con el id " + imageMongoDB.getIdClient() + " ya tiene una imagen asignada.");
		clientService.findById(imageMongoDB.getIdClient());
		return saveToUpdate(imageMongoDB, multipartFile, true);
	}

	@Override
	public ImageMongoDBDTO update(ImageMongoDBDTO imageMongoDB, MultipartFile multipartFile) {
		ImageMongoDBValidate.message(imageMongoDB);
		ImageMongoDBDTO aux = findById(imageMongoDB.get_id());
		if (aux.getIdClient() != imageMongoDB.getIdClient()) {
			clientService.findById(imageMongoDB.getIdClient());
			if (!testClient(imageMongoDB.getIdClient()))
				throw new PragmaException(
						"El cliente con el id " + imageMongoDB.getIdClient() + " ya tiene una imagen asignada.");
		}
		return saveToUpdate(imageMongoDB, multipartFile, true);
	}

	@Override
	public boolean delete(String id) {
		findById(id);
		imageMongoDBRepository.deleteById(id);
		return true;
	}

	private boolean testClient(Long idClient) {
		ImageMongoDBDTO image = null;
		try {
			image = findByClient(idClient);
		} catch (PragmaException e) {
			LOGGER.info("testClient(Long idClient)", e);
		}
		return image == null;
	}

	private ImageMongoDBDTO image(ImageMongoDBDTO image, MultipartFile multipartFile) {
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

	private ImageMongoDBDTO saveToUpdate(ImageMongoDBDTO image, MultipartFile multipartFile, boolean isRegister) {
		image = image(image, multipartFile);
		image = imageMongoDBMapper.toDTO(imageMongoDBRepository.save(imageMongoDBMapper.toEntity(image)));
		if (image == null)
			throw new PragmaException(
					isRegister ? "No se ha registrado la imagen." : "No se ha actualizado la imagen.");
		return image;
	}
}
