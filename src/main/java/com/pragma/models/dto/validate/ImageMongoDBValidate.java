package com.pragma.models.dto.validate;

import com.pragma.models.dto.ImageMongoDBDTO;
import com.pragma.util.Pragma;
import com.pragma.util.exception.PragmaException;

public class ImageMongoDBValidate {
	public static void message(ImageMongoDBDTO imageMongoDB) {
		if(imageMongoDB == null)
			throw new PragmaException("No se ha validado la imagen.");
		if(!Pragma.isLong(imageMongoDB.getIdClient()))
			throw new PragmaException("No se ha validado el id del cliente de la imagen.");
	}
}
