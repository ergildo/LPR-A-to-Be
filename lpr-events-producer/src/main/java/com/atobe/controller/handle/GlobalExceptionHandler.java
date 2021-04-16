package com.atobe.controller.handle;


import com.atobe.controller.response.ErrorMessage;
import com.atobe.exception.PublishEventException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  public static final String UNEXPECTED_SERVER_ERROR = "Unexpected server error";
  public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
  public static final String PUBLISH_EVENT_ERROR = "Publish Event Error";


  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(PublishEventException.class)
  public ErrorMessage publishEventException(PublishEventException e) {
    log.error(UNEXPECTED_SERVER_ERROR, e);
    return errorMessage(INTERNAL_SERVER_ERROR, UNEXPECTED_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorMessage exception(Exception e) {
    log.error(PUBLISH_EVENT_ERROR, e);
    return errorMessage(PUBLISH_EVENT_ERROR, e.getMessage());
  }

  private static ErrorMessage errorMessage(String error, String message) {
    return errorMessage(error, message, null);
  }

  private static ErrorMessage errorMessage(String error, String message, Object detail) {
    return ErrorMessage.builder().error(error).message(message).detail(detail).build();
  }
}
