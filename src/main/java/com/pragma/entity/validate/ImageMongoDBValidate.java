package com.pragma.entity.validate;

import com.pragma.entity.ImageMongoDB;
import com.pragma.util.Pragma;
import com.pragma.util.exception.PragmaException;

public class ImageMongoDBValidate {
	public static void message(ImageMongoDB imageMongoDB) {
		if(imageMongoDB == null)
			throw new PragmaException("No se ha validado la imagen.");
		if(!Pragma.isLong(imageMongoDB.getIdClient()))
			throw new PragmaException("No se ha validado el id del cliente de la imagen.");
	}
}
