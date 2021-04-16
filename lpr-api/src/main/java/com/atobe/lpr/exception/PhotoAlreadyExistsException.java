package com.atobe.lpr.exception;

public class PhotoAlreadyExistsException extends DataAlreadyExistsException {
  public PhotoAlreadyExistsException(String message) {
    super(message);
  }
}
