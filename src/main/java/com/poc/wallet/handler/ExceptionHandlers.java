package com.poc.wallet.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.poc.wallet.exception.PlatformException;
import com.poc.wallet.model.ResponseData;

@ControllerAdvice
public class ExceptionHandlers {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);
	
	@ExceptionHandler(value = PlatformException.class)
	public ResponseEntity<ResponseData<String>> handlePlatformException(PlatformException exception){
		log.error("Got PlatformException : {}",exception);
		ResponseData<String> response = new ResponseData<>();
		response.setMessage(exception.getMessage());
		return new ResponseEntity<ResponseData<String>>(response,exception.getStatus());
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseData<String>> handleException(Exception exception){
		log.error("Got Exception : {}",exception);
		ResponseData<String> response = new ResponseData<>();
		response.setMessage(exception.getMessage());
		return new ResponseEntity<ResponseData<String>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ResponseData<String>> handleMethodNotFound(HttpRequestMethodNotSupportedException exception){
		log.error("Got MethodNotFoundException : {}",exception);
		ResponseData<String> response = new ResponseData<>();
		response.setMessage(exception.getMessage());
		return new ResponseEntity<ResponseData<String>>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseData<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		log.error("Got MethodArgumentNotValidException : {}",exception);
		ResponseData<String> response = new ResponseData<>();
		response.setMessage(exception.getMessage());
		return new ResponseEntity<ResponseData<String>>(response,HttpStatus.BAD_REQUEST);
	}
}


