package com.pragma.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
