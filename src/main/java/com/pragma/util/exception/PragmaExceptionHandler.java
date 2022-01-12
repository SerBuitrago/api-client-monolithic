package com.pragma.util.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<Map<String, PragmaExceptionModel>> AllExceptions(MethodArgumentNotValidException ex) {
		Map<String, PragmaExceptionModel> errors = new HashMap<>();
		Integer code = HttpStatus.NOT_FOUND.value();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			PragmaExceptionModel pragma = new PragmaExceptionModel(errorMessage, ex.getClass().getSimpleName(),
					"Validate DTO", code);
			errors.put(fieldName, pragma);
		});
		ResponseEntity<Map<String, PragmaExceptionModel>> result = new ResponseEntity<>(errors,
				HttpStatus.valueOf(code));
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
