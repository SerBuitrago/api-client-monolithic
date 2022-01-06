package com.pragma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pragma.models.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Client findByTypeAndDocument(String type, Long document);
	
	List<Client> findByType(String type);
	
	@Query(nativeQuery = false, value = "SELECT c FROM Client c WHERE c.age >= :age")
	List<Client> findByHigherOrEqualsAge(@Param("age") int age);
}
