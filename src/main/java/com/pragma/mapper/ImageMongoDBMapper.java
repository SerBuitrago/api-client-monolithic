package com.pragma.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.models.entity.ImageMongoDB;

@Mapper(componentModel = "spring")
public interface ImageMongoDBMapper {
	
	ImageMongoDBDTO toDTO(ImageMongoDB image);
	
	List<ImageMongoDBDTO> toDTOList(List<ImageMongoDB> images);

	ImageMongoDB toEntity(ImageMongoDBDTO imageDTO);
	
	List<ImageMongoDB> toEntityList(List<ImageMongoDBDTO> imageDTOs);
}
