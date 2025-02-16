package com.example.ArkaLayers.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EntityNotFoundExceptionHandler implements ExceptionHandlerStrategy<EntityNotFoundException> {

  @Override
  public ResponseEntity<Map<String, String>> handleException(EntityNotFoundException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Not Found");
    response.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @Override
  public Class<EntityNotFoundException> getExceptionType() {
    return EntityNotFoundException.class;
  }

}
