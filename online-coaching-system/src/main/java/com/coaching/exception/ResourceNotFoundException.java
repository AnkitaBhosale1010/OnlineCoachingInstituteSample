package com.coaching.exception;

public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public ResourceNotFoundException(String message) {
		 
		 throw new ResourceNotFoundException("Batch Not Found");
	        
	    }

}
