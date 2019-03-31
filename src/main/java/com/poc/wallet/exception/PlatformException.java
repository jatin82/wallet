package com.poc.wallet.exception;

import org.springframework.http.HttpStatus;

public class PlatformException extends Exception{
	
	private HttpStatus status;
	
	private String message;
	
	public PlatformException(HttpStatus status,String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
	
}
