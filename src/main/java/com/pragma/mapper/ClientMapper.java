package com.pragma.mapper;

import java.util.List;

import org.mapstruct.Mapper;


import com.pragma.models.dto.ClientDTO;
import com.pragma.models.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
	
	ClientDTO toDTO(Client client);
	
	List<ClientDTO> toDTOList(List<Client> clients);

	Client toEntity(ClientDTO clientDTO);
	
	List<Client> toEntityList(List<ClientDTO> clientDTOs);
}
