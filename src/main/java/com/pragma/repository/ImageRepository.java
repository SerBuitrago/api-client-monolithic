package com.pragma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pragma.models.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	
	@Query(nativeQuery = false, value = "SELECT i FROM Image i WHERE i.idClient = :idClient")
	Image findByClient(@Param("idClient") Long idClient);
}
