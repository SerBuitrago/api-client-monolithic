package com.pragma.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pragma.mapper.ClientMapper;
import com.pragma.models.dto.ClientDTO;
import com.pragma.repository.ClientRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageMongoDBService;
import com.pragma.service.ImageService;
import com.pragma.util.exception.PragmaException;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ClientServiceImpl implements ClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	ClientRepository clientEntityRepository;

	@Autowired
	ClientMapper clientMapper;

	@Autowired
	ImageMongoDBService imageMongoDBService;
	@Autowired
	ImageService imageService;


	public ClientServiceImpl(ClientRepository clientEntityRepository) {
		this.clientEntityRepository = clientEntityRepository;
	}

	public ClientServiceImpl(ClientRepository clientEntityRepository, ClientMapper clientMapper) {
		this.clientEntityRepository = clientEntityRepository;
		this.clientMapper = clientMapper;
	}

	public ClientServiceImpl(ClientRepository clientEntityRepository, ClientMapper clientMapper,
			ImageMongoDBService imageMongoDBService, ImageService imageService) {
		this.clientEntityRepository = clientEntityRepository;
		this.clientMapper = clientMapper;
		this.imageMongoDBService = imageMongoDBService;
		this.imageService = imageService;
	}

	@Override
	public ClientDTO findById(Long id) {
		return clientMapper.toDTO(clientEntityRepository.findById(id)
				.orElseThrow(() -> new PragmaException("No se ha encontrado ningun cliente con el id " + id + ".")));
	}
	
	@Override
	public ClientDTO findClientAndImageById(Long id) {
		ClientDTO clientDTO = findById(id);
		testImage(id, clientDTO);
		return clientDTO;
	}

	@Override
	public ClientDTO findByTypeAndDocument(String type, Long document) {
		ClientDTO client = clientMapper.toDTO(clientEntityRepository.findByTypeAndDocument(type, document));
		if (client == null)
			throw new PragmaException("No se ha encontrado ningun cliente con el tipo de documento " + type
					+ " y documento " + document + ".");
		return client;
	}

	@Override
	public List<ClientDTO> findByHigherOrEqualsAge(int age) {
		return clientMapper.toDTOList(clientEntityRepository.findByHigherOrEqualsAge(age));
	}

	@Override
	public List<ClientDTO> findAll() {
		return clientMapper.toDTOList(clientEntityRepository.findAll());
	}

	@Override
	public List<ClientDTO> findByType(String type) {
		return clientMapper.toDTOList(clientEntityRepository.findByType(type));
	}

	@Override
	public ClientDTO save(ClientDTO cliente) {
		cliente.setId(0L);
		if (!testTypeDocument(cliente.getType(), cliente.getDocument()))
			throw new PragmaException("Ya existe un cliente con ese documento y tipo de documento.");
		cliente = clientMapper.toDTO(clientEntityRepository.save(clientMapper.toEntity(cliente)));
		if (cliente == null)
			throw new PragmaException("No se ha registrado el cliente.");
		return cliente;
	}

	@Override
	public ClientDTO update(ClientDTO cliente) {
		ClientDTO aux = findById(cliente.getId());
		if (!aux.getType().equalsIgnoreCase(cliente.getType())
				|| !Objects.equals(aux.getDocument(), cliente.getDocument()))
			if (!testTypeDocument(cliente.getType(), cliente.getDocument()))
				throw new PragmaException("Ya existe un cliente con ese documento y tipo de documento.");
		cliente = clientMapper.toDTO(clientEntityRepository.save(clientMapper.toEntity(cliente)));
		if (cliente == null)
			throw new PragmaException("No se ha actualizado el cliente.");
		return cliente;
	}

	@Override
	public boolean delete(Long id) {
		ClientDTO client = findById(id);
		if (!testImage(client.getId(), client))
			throw new PragmaException("No se ha eliminado el cliente con el id " + id + ", tiene asociado una imagen.");
		clientEntityRepository.deleteById(client.getId());
		return true;
	}

	private boolean testTypeDocument(String type, Long document) {
		ClientDTO client = null;
		try {
			client = findByTypeAndDocument(type, document);
		} catch (PragmaException e) {
			LOGGER.error("testTypeDocument(String type, Long document)", e);
		}
		return client == null;
	}

	private boolean testImage(Long id, ClientDTO clientDTO) {
		try {
			clientDTO.setImageMongoDBDTO(imageMongoDBService.findByClient(id));
		} catch (PragmaException e) {
			LOGGER.error("testImage(Long id): MongoDB", e);
		}
		try {
			clientDTO.setImageDTO(imageService.findByClient(id));
		} catch (PragmaException e) {
			LOGGER.error("testImage(Long id): MySQL", e);
		}
		return clientDTO.getImageDTO() == null && clientDTO.getImageMongoDBDTO() == null;
	}
}
