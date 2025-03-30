package com.ihechi.jobTrek.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ihechi.jobTrek.enums.UserRole;
import com.ihechi.jobTrek.exception.ResourceNotFoundException;
import com.ihechi.jobTrek.exception.UnauthorizedAccessException;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handle validation errors from DTOs (e.g., @Valid annotations).
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage()); // Field name -> Error message
    }

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle IllegalArgumentException (e.g., validation failures, bad input).
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle IllegalArgumentException (e.g., validation failures, bad input).
   */
  @ExceptionHandler(UnauthorizedAccessException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(UnauthorizedAccessException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle Resource Not Found (e.g., job not found, user not found).
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle invalid JSON input (e.g., invalid enum values).
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    Throwable cause = ex.getCause();
    if (cause instanceof InvalidFormatException invalidFormatException) {
      return handleInvalidFormatException(invalidFormatException);
    }

    Map<String, String> response = new HashMap<>();
    response.put("error", "Invalid request format. Please check your input.");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle invalid enum values (e.g., UserRole).
   */
  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<Map<String, String>> handleInvalidFormatException(InvalidFormatException ex) {
    if (ex.getTargetType().equals(UserRole.class)) {
      List<String> validValues = Stream.of(UserRole.values())
          .map(Enum::name)
          .collect(Collectors.toList());

      String errorMessage = String.format(
          "Invalid role provided: '%s'. Accepted values are: %s",
          ex.getValue(),
          validValues
      );

      Map<String, String> response = new HashMap<>();
      response.put("error", errorMessage);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    Map<String, String> response = new HashMap<>();
    response.put("error", "Invalid input format.");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle Generic Exception (for unexpected server errors).
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "An unexpected error occurred: " + ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}