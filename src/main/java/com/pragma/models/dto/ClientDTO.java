package com.pragma.models.dto;

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
	private String name;
	private String subname;
	private String type;
	private Long document;
	private int age;
	private String cityBirth;
	
	private ImageDTO imageDTO;
	private ImageMongoDBDTO imageMongoDBDTO;
}
