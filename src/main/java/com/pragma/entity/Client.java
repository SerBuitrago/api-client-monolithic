package com.pragma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
