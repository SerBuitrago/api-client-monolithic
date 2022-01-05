package com.pragma.service.impl;

import java.io.IOException;
import java.util.List;

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
import com.pragma.entity.ImageMongoDB;
import com.pragma.service.ImageMongoDBService;
import com.pragma.util.Pragma;
import com.pragma.util.exception.PragmaException;

@Service
public class ImageMongoDBServiceImpl implements ImageMongoDBService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageMongoDBServiceImpl.class);

	@Autowired
	private GridFsTemplate template;

	@Autowired
	private GridFsOperations operations;

	@Override
	public ImageMongoDB findById(String id) {
		if (!Pragma.isString(id))
			throw new PragmaException("No se ha encontrado niguna imagen con el id " + id + ".");
		GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));
		ImageMongoDB imageMongoDB = new ImageMongoDB();
		if (gridFSFile == null || gridFSFile.getMetadata() == null)
			throw new PragmaException("No se ha encontrado niguna imagen con el id " + id + ".");
		imageMongoDB.set_id(id);
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
				throw new PragmaException("Se ha presentado un error.");
		}
		return imageMongoDB;
	}

	@Override
	public ImageMongoDB findByClient(Long idClient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImageMongoDB> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageMongoDB save(ImageMongoDB imageMongoDB, MultipartFile multipartFile) {

		DBObject metadata = new BasicDBObject();
		metadata.put("fileSize", multipartFile.getSize());
		metadata.put("idClient", imageMongoDB.getIdClient());

		Object fileID = null;
		try {
			fileID = template.store(multipartFile.getInputStream(), multipartFile.getOriginalFilename(),
					multipartFile.getContentType(), metadata);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ImageMongoDB update(ImageMongoDB imageMongoDB, MultipartFile multipartFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
