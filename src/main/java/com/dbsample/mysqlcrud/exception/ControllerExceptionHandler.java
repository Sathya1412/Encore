package com.dbsample.mysqlcrud.exception;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = {RequestException.class})
	public ResponseEntity<Object> handleStudentRequestException(RequestException e) {

		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		ExceptionDetails studentException = new ExceptionDetails(
				e.getMessage(), 
				badRequest,
				ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
		return new ResponseEntity<>(studentException, badRequest);
	}

}
