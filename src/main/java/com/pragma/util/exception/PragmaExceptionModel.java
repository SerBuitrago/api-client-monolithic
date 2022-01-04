package com.pragma.util.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PragmaExceptionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private String exception;
	private String path;
	private int statu;
	
	public static PragmaExceptionModel builder(Exception e, int code) {
		return new PragmaExceptionModel(e.getMessage(), e.getClass().getSimpleName(), e.getLocalizedMessage(), code);
	}
}
