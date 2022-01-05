package com.pragma.util.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PragmaExceptionHandler {

	private static final Map<String, Integer> STATUS = new HashMap<>();
	
	@ExceptionHandler(PragmaException.class)
	public final ResponseEntity<PragmaExceptionModel> AllExceptions(HttpServletRequest request, Exception exception) {
		Integer code = getStatus(exception);
		code = (code == null) ? HttpStatus.INTERNAL_SERVER_ERROR.value() : code;
		PragmaExceptionModel error = new PragmaExceptionModel(exception.getMessage(),
				exception.getClass().getSimpleName(), request.getRequestURI(), code);
		ResponseEntity<PragmaExceptionModel> result = new ResponseEntity<>(error, HttpStatus.valueOf(code));
		exception.printStackTrace();
		return result;
	}

	private Integer getStatus(Exception e) {
		if (e instanceof PragmaException) {
			PragmaException ex = (PragmaException) e;
			if (ex.getHttpStatus() != null)
				return ex.getHttpStatus().value();
		}
		return STATUS.get(e.getClass().getSimpleName());
	}
}
