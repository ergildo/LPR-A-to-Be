package com.atobe.exception;

public class PublishEventException extends RuntimeException {
  public PublishEventException(Exception e) {
    super( e);
  }
  public PublishEventException(String message) {
    super(message);
  }
}
