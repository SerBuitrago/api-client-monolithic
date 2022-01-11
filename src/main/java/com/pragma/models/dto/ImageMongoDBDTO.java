package com.pragma.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageMongoDBDTO {
	
	private String _id;
	private Long idClient;
	private String contentType;
	private String filename;
	private String image;
}
