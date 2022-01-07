package com.pragma.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pragma.models.entity.ImageMongoDB;

@Repository
public interface ImageMongoDBRepository extends MongoRepository<ImageMongoDB, String>{
	
	@Query(value = "{idClient: ?0}")
	ImageMongoDB findByClient(@Param("idClient") Long idClient);
}
