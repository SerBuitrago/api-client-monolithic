package com.pragma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "id_client", nullable = false)
	private Long idClient;
	
	@Column(name = "contentType", nullable = false)
	private String contentType;
	
	@Column(name = "filename", nullable = false)
	private String filename;
	
	@Lob
	@Column(name = "photo", nullable = false)
	private String photo;
}
