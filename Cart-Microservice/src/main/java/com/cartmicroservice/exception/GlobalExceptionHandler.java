package com.cartmicroservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandlerForAllExceptions(Exception ex)
	{
		
		return ResponseEntity.status(404).body(new ExceptionResponse(ex.getMessage(),ex.getLocalizedMessage()));
	}
	
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<?> exceptionHandlerForAllExceptions(InvalidUserException ex)
	{
		
		return ResponseEntity.status(404).body(new ExceptionResponse(ex.getMessage(),ex.getLocalizedMessage()));
	}
}
