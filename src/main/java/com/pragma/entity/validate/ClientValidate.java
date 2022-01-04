package com.pragma.entity.validate;

import com.pragma.entity.Client;
import com.pragma.util.Pragma;
import com.pragma.util.exception.PragmaException;

public class ClientValidate {

	public static void message(Client client) {
		if(client == null)
			throw new PragmaException("No se ha podido validar el cliente.");
		if(!Pragma.isString(client.getName()))
			throw new PragmaException("No se ha podido validar el nombre del cliente.");
		if(!Pragma.isLong(client.getDocument()))
			throw new PragmaException("No se ha podido validar el documento del cliente.");
		if(!Pragma.isInteger(client.getAge()))
			throw new PragmaException("No se ha podido validar la edad del cliente.");
		if(!Pragma.isString(client.getCityBirth()))
			throw new PragmaException("No se ha podido validarla ciudad de nacimiento del cliente.");
	}
}
