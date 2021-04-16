package com.atobe.lpr.events.consumer.service;

import com.atobe.lpr.events.consumer.model.Photo;
import com.atobe.lpr.events.consumer.model.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "lpr-api-client", url = "${lpr-api.url}")
public interface PhotoApiClientService {
  @PostMapping("/photos/import")
  public Review addPhoto(Photo photo);
}
