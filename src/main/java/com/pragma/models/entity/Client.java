package com.pragma.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	private String name;
	private String subname;
	private String type;
	private Long document;
	private int age;
	
	@Column(name = "city_birth", nullable = false, length = 100)
	private String cityBirth;
	
	@Transient
	private ImageMongoDB imageMongoDB;
	@Transient
	private Image image;	
}
