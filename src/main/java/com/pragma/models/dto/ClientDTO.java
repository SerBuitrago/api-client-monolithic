package com.pragma.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
	
	private Long id;
	
	@NotBlank(message = "El campo nombre es obligatorio.")
	private String name;
	private String subname;
	
	@NotBlank(message = "El campo tipo documento es obligatorio.")
	private String type;
	
	@NotNull(message = "El campo documento es obligatorio.")
	private Long document;
	
	@Min(value = 18, message = "La edad minima es de 18 años.")
	@Max(value = 100, message = "La edad maxima es de 100 años.")
	private int age;
	
	@NotBlank(message = "El campo ciudad nacimiento es obligatorio.")
	private String cityBirth;
	
	private ImageDTO imageDTO;
	private ImageMongoDBDTO imageMongoDBDTO;
}
