package com.pragma.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
	
	private Long id;
	private Long idClient;
	private String contentType;
	private String filename;
	private String image;
}
