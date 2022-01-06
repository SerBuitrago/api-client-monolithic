package com.pragma.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pragma.mapper.ClientMapper;
import com.pragma.models.dto.ClientDTO;
import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.models.entity.validate.ClientValidate;
import com.pragma.repository.ClientRepository;
import com.pragma.service.ClientService;
import com.pragma.service.ImageMongoDBService;
import com.pragma.util.Pragma;
import com.pragma.util.exception.PragmaException;

@Service
public class ClientServiceImpl implements ClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	ClientRepository clientEntityRepository;

	@Autowired
	ClientMapper clientMapper;

	@Autowired
	ImageMongoDBService imageMongoDBService;

	public ClientServiceImpl(ClientRepository clientEntityRepository, ClientMapper clientMapper,
			ImageMongoDBService imageMongoDBService) {
		this.clientEntityRepository = clientEntityRepository;
		this.clientMapper = clientMapper;
		this.imageMongoDBService = imageMongoDBService;
	}

	@Override
	public ClientDTO findById(Long id) {
		return clientMapper.toDomain(clientEntityRepository.findById(id)
				.orElseThrow(() -> new PragmaException("No se ha encontrado ningun cliente con el id " + id + ".")));
	}

	@Override
	public ClientDTO findByTypeAndDocument(String type, Long document) {
		ClientDTO client = clientMapper.toDomain(clientEntityRepository.findByTypeAndDocument(type, document));
		if (client == null)
			throw new PragmaException("No se ha encontrado ningun cliente con el tipo de documento " + type
					+ " y documento " + document + ".");
		return client;
	}

	@Override
	public List<ClientDTO> findByHigherOrEqualsAge(int age) {
		return clientMapper.toDomainList(clientEntityRepository.findByHigherOrEqualsAge(age));
	}

	@Override
	public List<ClientDTO> findAll() {
		return clientMapper.toDomainList(clientEntityRepository.findAll());
	}

	@Override
	public List<ClientDTO> findByType(String type) {
		return clientMapper.toDomainList(clientEntityRepository.findByType(type));
	}

	@Override
	public ClientDTO save(ClientDTO cliente) {
		ClientValidate.message(cliente);
		cliente.setId(0L);
		if (!testTypeDocument(cliente.getType(), cliente.getDocument()))
			throw new PragmaException("Ya existe un cliente con ese documento y tipo de documento.");
		cliente = clientMapper.toDomain(clientEntityRepository.save(clientMapper.toEntity(cliente)));
		if (cliente == null)
			throw new PragmaException("No se ha registrado el cliente.");
		return cliente;
	}

	@Override
	public ClientDTO update(ClientDTO cliente) {
		ClientValidate.message(cliente);
		ClientDTO aux = findById(cliente.getId());
		if (!aux.getType().equalsIgnoreCase(cliente.getType())
				|| !Objects.equals(aux.getDocument(), cliente.getDocument()))
			if (!testTypeDocument(cliente.getType(), cliente.getDocument()))
				throw new PragmaException("Ya existe un cliente con ese documento y tipo de documento.");
		cliente = clientMapper.toDomain(clientEntityRepository.save(clientMapper.toEntity(cliente)));
		if (cliente == null)
			throw new PragmaException("No se ha actualizado el cliente.");
		return cliente;
	}

	@Override
	public boolean delete(String type, Long document) {
		ClientDTO client = findByTypeAndDocument(type, document);
		List<ImageMongoDBDTO> list = imageMongoDBService.findByClient(client.getId());
		if (Pragma.isList(list))
			throw new PragmaException("No se ha eliminado el cliente con tipo de documento " + type + " y documento "
					+ document + ", tiene asociado " + list.size() + " fotos.");
		clientEntityRepository.deleteById(client.getId());
		return true;
	}

	@Override
	public boolean delete(Long id) {
		ClientDTO client = findById(id);
		List<ImageMongoDBDTO> list = imageMongoDBService.findByClient(client.getId());
		if (Pragma.isList(list))
			throw new PragmaException(
					"No se ha eliminado el cliente con el id " + id + ", tiene asociado " + list.size() + " fotos.");
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
}
