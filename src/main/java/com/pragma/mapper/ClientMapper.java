package com.pragma.mapper;

import java.util.List;

import org.mapstruct.Mapper;


import com.pragma.models.dto.ClientDTO;
import com.pragma.models.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
	
	ClientDTO toDomain(Client client);
	
	List<ClientDTO> toDomainList(List<Client> clients);

	Client toEntity(ClientDTO clientDTO);
	
	List<Client> toEntityList(List<ClientDTO> clientDTOs);
}
