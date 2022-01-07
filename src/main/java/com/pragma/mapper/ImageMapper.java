package com.pragma.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.pragma.models.dto.ImageDTO;
import com.pragma.models.entity.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

	ImageDTO toDTO(Image image);
	
	List<ImageDTO> toDTOList(List<Image> images);

	Image toEntity(ImageDTO imageDTO);
	
	List<Image> toEntityList(List<ImageDTO> imageDTOs);
}
