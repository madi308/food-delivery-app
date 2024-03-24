package com.example.fooddeliveryapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles custom exceptions. New custom exceptions should be added to the @ExceptionHandler.
     * @param exception The exception to be handled.
     * @return JSON response containing the exception's message.
     */
    @ExceptionHandler (value = { NoObservationFoundException.class, UnsupportedVehicleException.class, CityNotFoundException.class, NotAFloatException.class })
    public ResponseEntity<Object> handleCustomExcpetions(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("{\"error\": \"" + exception.getMessage() + "\"}");
    }

}
