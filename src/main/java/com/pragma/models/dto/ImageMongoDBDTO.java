package com.pragma.models.dto;

import javax.validation.constraints.NotNull;

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
	
	@NotNull(message = "El campo id cliente es obligatorio.")
	private Long idClient;
	private String contentType;
	private String filename;
	private String image;
}
