package com.pragma.models.dto.validate;

import com.pragma.models.entity.Image;
import com.pragma.util.Pragma;
import com.pragma.util.exception.PragmaException;

public class ImageValidate {
	
	public static void message(Image image) {
		if(image == null)
			throw new PragmaException("No se ha validado la imagen.");
		if(!Pragma.isLong(image.getIdClient()))
			throw new PragmaException("No se ha validado el id del cliente de la imagen.");
	}
}
