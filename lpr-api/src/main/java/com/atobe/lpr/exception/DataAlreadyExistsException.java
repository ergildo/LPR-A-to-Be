package com.atobe.lpr.exception;

public class DataAlreadyExistsException extends RuntimeException {
  public DataAlreadyExistsException(String message) {
    super(message);
  }
}
