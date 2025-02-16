package com.example.ArkaLayers.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Map<Class<? extends Exception>, ExceptionHandlerStrategy<?>> handlers;

  @Autowired
  public GlobalExceptionHandler(List<ExceptionHandlerStrategy<?>> handlerStrategies) {
    handlers = handlerStrategies.stream()
            .collect(Collectors.toMap(ExceptionHandlerStrategy::getExceptionType, strategy -> strategy));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex) {
    ExceptionHandlerStrategy handler = handlers.get(ex.getClass());

    if (handler != null) {
      return handler.handleException(ex);
    }

    Map<String, String> response = new HashMap<>();
    response.put("error", "Internal Server Error");
    response.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

}
