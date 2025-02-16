package com.example.ArkaLayers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MethodArgumentNotValidExceptionHandler implements ExceptionHandlerStrategy<MethodArgumentNotValidException> {

  @Override
  public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new HashMap<>();
    errors.put("error", "Validation Failed");

    List<Map<String, String>> errorDetails = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(error -> {
              Map<String, String> fieldError = new HashMap<>();
              fieldError.put("field", error.getField());
              fieldError.put("message", error.getDefaultMessage());
              return fieldError;
            })
            .collect(Collectors.toList());

    errors.put("details", errorDetails);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @Override
  public Class<MethodArgumentNotValidException> getExceptionType() {
    return MethodArgumentNotValidException.class;
  }
}
