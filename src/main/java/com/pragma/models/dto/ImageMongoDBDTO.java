package com.pragma.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageMongoDBDTO {
	
	private String _id;
	private Long idClient;
	private String filename;
	private String fileType;
	private String fileSize;
	private byte[] file;
}
