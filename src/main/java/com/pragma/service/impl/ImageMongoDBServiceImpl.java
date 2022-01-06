package com.pragma.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.models.dto.validate.ImageMongoDBValidate;
import com.pragma.service.ClientService;
import com.pragma.service.ImageMongoDBService;
import com.pragma.util.exception.PragmaException;

@Service
public class ImageMongoDBServiceImpl implements ImageMongoDBService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageMongoDBServiceImpl.class);

	@Autowired
	private GridFsTemplate template;

	@Autowired
	private GridFsOperations operations;

	@Autowired
	private ClientService clientService;
	

	public ImageMongoDBServiceImpl(GridFsTemplate template, GridFsOperations operations, ClientService clientService) {
		this.template = template;
		this.operations = operations;
		this.clientService = clientService;
	}

	@Override
	public ImageMongoDBDTO findById(String id) {
		GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));
		if (gridFSFile == null || gridFSFile.getMetadata() == null)
			throw new PragmaException("No se ha encontrado niguna imagen con el id " + id + ".");
		return builder(gridFSFile);
	}
	
	@Override
	public List<ImageMongoDBDTO> findAll() {
		List<ImageMongoDBDTO> list = new ArrayList<>();
		template.find(new Query()).forEach((Consumer<GridFSFile>) g -> list.add(builder(g)));
		return list;
	}

	@Override
	public List<ImageMongoDBDTO> findByClient(Long idClient) {
		List<ImageMongoDBDTO> list = new ArrayList<>();
		template.find(new Query(Criteria.where("metadata.idClient").is(idClient)))
				.forEach((Consumer<GridFSFile>) g -> list.add(builder(g)));
		return list;
	}

	@Override
	public ImageMongoDBDTO save(ImageMongoDBDTO imageMongoDB, MultipartFile multipartFile) {
		if (multipartFile == null || multipartFile.isEmpty())
			throw new PragmaException("No se ha recibido la imagen.");
		ImageMongoDBValidate.message(imageMongoDB);
		clientService.findById(imageMongoDB.getIdClient());
		DBObject metadata = new BasicDBObject();
		metadata.put("fileSize", multipartFile.getSize());
		metadata.put("idClient", imageMongoDB.getIdClient());
		Object fileID = null;
		try {
			fileID = template.store(multipartFile.getInputStream(), multipartFile.getOriginalFilename(),
					multipartFile.getContentType(), metadata);
		} catch (IOException e) {
			LOGGER.error("save(ImageMongoDB imageMongoDB, MultipartFile multipartFile)", e);
		} finally {
			if (fileID == null)
				throw new PragmaException(
						"No se registrado la imagen al cliente con id " + imageMongoDB.getIdClient() + ".");
		}

		return findById(fileID.toString());
	}

	@Override
	public ImageMongoDBDTO update(ImageMongoDBDTO imageMongoDB, MultipartFile multipartFile) {
		if (multipartFile == null || multipartFile.isEmpty())
			throw new PragmaException("No se ha recibido la imagen.");
		ImageMongoDBValidate.message(imageMongoDB);
		return null;
	}

	@Override
	public boolean delete(String id) {
		findById(id);
		template.delete(new Query(Criteria.where("_id").is(id)));
		try {
			findById(id);
		}catch (PragmaException e) {
			LOGGER.info("delete(String id)", e);
			return true;
		}
		throw new PragmaException("No se ha eliminado la imagen con el id "+id+".");
	}

	private ImageMongoDBDTO builder(GridFSFile gridFSFile) {
		ImageMongoDBDTO imageMongoDB = new ImageMongoDBDTO();
		imageMongoDB.set_id(gridFSFile.getObjectId().toString());
		imageMongoDB.setFilename(gridFSFile.getFilename());
		imageMongoDB.setIdClient(Long.parseLong(gridFSFile.getMetadata().get("idClient").toString()));
		imageMongoDB.setFileType(gridFSFile.getMetadata().get("_contentType").toString());
		imageMongoDB.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());
		boolean isError = false;
		try {
			imageMongoDB.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
		} catch (IllegalStateException e) {
			isError = true;
			LOGGER.error("findById(String id)", e);
		} catch (IOException e) {
			isError = true;
			LOGGER.error("findById(String id)", e);
		} finally {
			if (isError)
				throw new PragmaException("Se ha presentado un error al procesar la imagen.");
		}
		return imageMongoDB;
	}
}
