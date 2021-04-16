package com.atobe.lpr.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtil {

  @SneakyThrows
  public static <T> T parseJsonString(String jsonString, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return mapper.readValue(jsonString, clazz);
  }

  @SneakyThrows
  public static String toJsonString(Object object) {
    return new ObjectMapper().writeValueAsString(object);
  }
}
