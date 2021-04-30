package com.danilopeixoto.api.util;

import com.danilopeixoto.api.model.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice
public class APIExceptionHandler {
  @ExceptionHandler(ServerWebInputException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(ServerWebInputException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(new ErrorResponse(
        exception instanceof WebExchangeBindException ?
          Objects.requireNonNull(
            ((WebExchangeBindException) exception)
              .getFieldError())
            .getDefaultMessage() :
          "Bad request."));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleUnprocessableEntity(DataIntegrityViolationException exception) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(new ErrorResponse("Resource already exists."));
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException exception) {
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(new ErrorResponse("Resource not found."));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException exception) {
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(new ErrorResponse("Internal server error."));
  }
}
