package com.example.fileSystem.exception;

import lombok.Data;

@Data
public class LocationBasedException extends RuntimeException {

	 public LocationBasedException(String message) {
	        super(message);
	    }

	    public LocationBasedException(String message, Throwable cause) {
	        super(message, cause);
	    }
	
}
