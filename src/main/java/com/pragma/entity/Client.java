package com.pragma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "subname", nullable = true, length = 100)
	private String subname;
	
	@Column(name = "type", nullable = false, length = 100)
	private String type;
	
	@Column(name = "document", nullable = false)
	private Long document;
	
	@Column(name = "age", nullable = false)
	private int age;
	
	@Column(name = "city_birth", nullable = false, length = 100)
	private String cityBirth;
}
