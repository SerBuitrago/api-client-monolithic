package com.pragma.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageMongoDB {
	
	private String _id;
	private Long idClient;
	private String filename;
	private String fileType;
	private String fileSize;
	private byte[] file;
}
