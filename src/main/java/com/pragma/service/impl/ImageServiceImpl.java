package com.pragma.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pragma.entity.Image;
import com.pragma.entity.validate.ImageValidate;
import com.pragma.repository.ImageRepository;
import com.pragma.service.ImageService;
import com.pragma.util.Pragma;
import com.pragma.util.exception.PragmaException;

@Service
public class ImageServiceImpl implements ImageService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);
	
	@Value("${pragma.image.save.path}")
	private String pathImage;
	
	@Autowired
	ImageRepository imageRepository;	

	public ImageServiceImpl(String pathImage, ImageRepository imageRepository) {
		this.pathImage = pathImage;
		this.imageRepository = imageRepository;
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
	public Image findByClient(Long idClient) {
		if(!Pragma.isLong(idClient))
			throw new PragmaException("El id del cliente de la imagen no es valido.");
		Image image = imageRepository.findByClient(idClient);
		if(image == null)
			throw new PragmaException("No se ha encontrado niguna imagen al cliente con el id "+idClient+".");
		return image;
	}
	
	@Override
	public String view(Long id) {
		Image image = findById(id);
		return "images/"+image.getPath();
	}

	@Override
	public List<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public Image save(Image image, MultipartFile multipartFile) {
		ImageValidate.message(image);
		if(!testClient(image.getIdClient()))
			throw new PragmaException("El cliente con el id "+image.getIdClient()+" ya tiene una imagen asignada.");
		image = image(image, multipartFile);
		image = imageRepository.save(image);
		if(image == null)
			throw new PragmaException("No se ha registrado la imagen.");
		return image;
	}

	@Override
	public Image update(Image image, MultipartFile multipartFile) {
		ImageValidate.message(image);
		findById(image.getId());
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
		try {
			findById(id);
		}catch (PragmaException e) {
			LOGGER.info("delete(Long id)", e);
		}
		throw new PragmaException("No se ha eliminado la imagen con el id "+id+".");
	}
	
	private boolean testClient(Long idClient) {
		Image image = null;
		try {
			image = findByClient(idClient);
		}catch (PragmaException e) {
			LOGGER.info("testClient(Long idClient)", e);
		}
		return image == null;
	}
	
	private Image image(Image image, MultipartFile multipartFile) {
		if(multipartFile == null || multipartFile.isEmpty())
			throw new PragmaException("No se ha recibido la imagen.");
		if(!Pragma.isString(pathImage))
			throw new PragmaException("No se ha recibido la ruta donde se va a guardar la imagen.");
		Path path = Paths.get(pathImage);
		String pathAbsolute = path.toFile().getAbsolutePath();
		image.setPath(null);
		try {
			byte [] byteImage = multipartFile.getBytes();
			Path pathComplete = Paths.get(pathAbsolute+"//"+multipartFile.getOriginalFilename());
			Files.write(pathComplete, byteImage);
			image.setPath(multipartFile.getOriginalFilename());
		} catch (IOException e) {
			LOGGER.error("image(Image image, MultipartFile multipartFile)", e);
		}finally {
			if(!Pragma.isString(image.getPath()))
				throw new PragmaException("No se ha podido procesar la imagen.");
		}
		return image;
	}
}
