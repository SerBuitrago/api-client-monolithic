package com.pragma.repository;

import org.springframework.stereotype.Repository;

import com.pragma.entity.ImageMongoDB;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface ImageMongoDBRepository extends MongoRepository<ImageMongoDB, String>{
	
	@Query(value = "{idClient: ?0}")
	List<ImageMongoDB> findByClient(Long idClient);
}
