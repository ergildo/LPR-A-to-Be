package com.atobe.lpr.controller.handle;


import com.atobe.lpr.controller.response.ErrorMessage;
import com.atobe.lpr.exception.DataAlreadyExistsException;
import com.atobe.lpr.exception.ResultNotFoundException;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  public static final String UNEXPECTED_SERVER_ERROR = "Unexpected server error";
  public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
  public static final String RESULT_NOT_FOUND = "Result not found";
  public static final String DATA_ALREADY_EXISTS = "Data Already exists";

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResultNotFoundException.class)
  public ErrorMessage resultNotFound(ResultNotFoundException e) {
    log.error(RESULT_NOT_FOUND, e);
    return errorMessage(RESULT_NOT_FOUND, e.getMessage());
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(DataAlreadyExistsException.class)
  public ErrorMessage dataAlreadyExists(DataAlreadyExistsException e) {
    log.error(DATA_ALREADY_EXISTS, e);
    return errorMessage(DATA_ALREADY_EXISTS, e.getMessage());
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorMessage notValid(MethodArgumentNotValidException e) {
    var invalidFieldMessage = new HashMap<String, String>();
    e.getBindingResult()
        .getFieldErrors()
        .forEach(error -> invalidFieldMessage.put(error.getField(), error.getDefaultMessage()));
    var error = "Invalid Request";
    var message = "Invalid Field(s)";
    log.warn(message, e);
    return errorMessage(error, message, invalidFieldMessage);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorMessage exception(Exception e) {
    log.error(UNEXPECTED_SERVER_ERROR, e);
    return errorMessage(INTERNAL_SERVER_ERROR, UNEXPECTED_SERVER_ERROR);
  }

  private static ErrorMessage errorMessage(String error, String message) {
    return errorMessage(error, message, null);
  }

  private static ErrorMessage errorMessage(String error, String message, Object detail) {
    return ErrorMessage.builder().error(error).message(message).detail(detail).build();
  }
}
