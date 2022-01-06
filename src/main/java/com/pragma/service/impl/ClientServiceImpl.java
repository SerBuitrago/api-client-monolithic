package com.pragma.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.models.entity.Client;
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
	ImageMongoDBService imageMongoDBService;

	public ClientServiceImpl(ClientRepository clientEntityRepository) {
		this.clientEntityRepository = clientEntityRepository;
	}

	@Override
	public Client findById(Long id) {
		Optional<Client> optional = clientEntityRepository.findById(id);
		return optional
				.orElseThrow(() -> new PragmaException("No se ha encontrado ningun cliente con el id " + id + "."));
	}

	@Override
	public Client findByTypeAndDocument(String type, Long document) {
		Client client = clientEntityRepository.findByTypeAndDocument(type, document);
		if (client == null)
			throw new PragmaException("No se ha encontrado ningun cliente con el tipo de documento " + type
					+ " y documento " + document + ".");
		return client;
	}

	@Override
	public List<Client> findByHigherOrEqualsAge(int age) {
		return clientEntityRepository.findByHigherOrEqualsAge(age);
	}

	@Override
	public List<Client> findAll() {
		return clientEntityRepository.findAll();
	}

	@Override
	public List<Client> findByType(String type) {
		return clientEntityRepository.findByType(type);
	}

	@Override
	public Client save(Client cliente) {
		ClientValidate.message(cliente);
		cliente.setId(0L);
		if (!testTypeDocument(cliente.getType(), cliente.getDocument()))
			throw new PragmaException("Ya existe un cliente con ese documento y tipo de documento.");
		cliente = clientEntityRepository.save(cliente);
		if (cliente == null)
			throw new PragmaException("No se ha registrado el cliente.");
		return cliente;
	}

	@Override
	public Client update(Client cliente) {
		ClientValidate.message(cliente);
		Client aux = findById(cliente.getId());
		if (!aux.getType().equalsIgnoreCase(cliente.getType())
				|| !Objects.equals(aux.getDocument(), cliente.getDocument()))
			if (!testTypeDocument(cliente.getType(), cliente.getDocument()))
				throw new PragmaException("Ya existe un cliente con ese documento y tipo de documento.");
		cliente = clientEntityRepository.save(cliente);
		if (cliente == null)
			throw new PragmaException("No se ha actualizado el cliente.");
		return cliente;
	}

	@Override
	public boolean delete(String type, Long document) {
		Client client = findByTypeAndDocument(type, document);
		List<ImageMongoDBDTO> list = imageMongoDBService.findByClient(client.getId());
		if (Pragma.isList(list))
			throw new PragmaException("No se ha eliminado el cliente con tipo de documento " + type + " y documento "
					+ document + ", tiene asociado " + list.size() + " fotos.");
		clientEntityRepository.deleteById(client.getId());
		try {
			client = findByTypeAndDocument(type, document);
		} catch (PragmaException e) {
			LOGGER.error("delete(String type, Long document)", e);
			client = null;
		} finally {
			if (client == null)
				return true;
		}
		throw new PragmaException("No se ha eliminado el cliente.");
	}

	@Override
	public boolean delete(Long id) {
		Client client = findById(id);
		List<ImageMongoDBDTO> list = imageMongoDBService.findByClient(client.getId());
		if (Pragma.isList(list))
			throw new PragmaException(
					"No se ha eliminado el cliente con el id " + id + ", tiene asociado " + list.size() + " fotos.");
		clientEntityRepository.deleteById(client.getId());
		try {
			client = findById(id);
		} catch (PragmaException e) {
			LOGGER.error("delete(String type, Long document)", e);
			client = null;
		} finally {
			if (client == null)
				return true;
		}
		throw new PragmaException("No se ha eliminado el cliente.");
	}

	private boolean testTypeDocument(String type, Long document) {
		Client client = null;
		try {
			client = findByTypeAndDocument(type, document);
		} catch (PragmaException e) {
			LOGGER.error("testTypeDocument(String type, Long document)", e);
		}
		return client == null;
	}
}
