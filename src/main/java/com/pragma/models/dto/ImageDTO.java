package com.pragma.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
	private Long id;
	private Long idClient;
	private String contentType;
	private String filename;
	private String photo;
}
