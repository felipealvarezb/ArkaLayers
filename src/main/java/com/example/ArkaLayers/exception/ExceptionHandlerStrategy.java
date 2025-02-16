package com.example.ArkaLayers.exception;

import org.springframework.http.ResponseEntity;

public interface ExceptionHandlerStrategy<T extends Exception> {

  ResponseEntity<?> handleException(T exception);

  Class<T> getExceptionType();
}
