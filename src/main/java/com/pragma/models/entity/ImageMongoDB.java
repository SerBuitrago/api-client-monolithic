package com.pragma.models.entity;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "image")
public class ImageMongoDB {
	
	@Id
	private String _id;
	
	private Long idClient;
	private String contentType;
	private String filename;
	
	@Lob
	private String image;
}
