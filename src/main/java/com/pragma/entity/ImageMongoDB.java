package com.pragma.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Contact")
public class ImageMongoDB {
	
	@Id
	private String _id;
	
	private Long idClient;
	
	private String image;
}
