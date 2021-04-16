package com.atobe.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ErrorMessage {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final String error;

  @NonNull private final String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final Object detail;
}
