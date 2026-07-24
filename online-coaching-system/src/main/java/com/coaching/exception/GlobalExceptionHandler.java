package com.coaching.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Resource Not Found Exception
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex,HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }


    // Duplicate Resource Exception
	@ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex,HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    // Unauthorized Exception
	@ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex,HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

	 @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<ErrorResponse> handleBadRequest(
	            BadRequestException ex,
	            HttpServletRequest request) {

	        ErrorResponse response = new ErrorResponse(
	                LocalDateTime.now(),
	                HttpStatus.BAD_REQUEST.value(),
	                "Bad Request",
	                ex.getMessage(),
	                request.getRequestURI());
	        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

	    }

	    @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<ErrorResponse> handleBadCredentials(
	            BadCredentialsException ex,
	            HttpServletRequest request) {

	        ErrorResponse response = new ErrorResponse(
	                LocalDateTime.now(),
	                HttpStatus.UNAUTHORIZED.value(),
	                "Unauthorized",
	                "Invalid Email or Password",
	                request.getRequestURI());
	        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);

	    }


    // Any Other Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex,HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
