package com.atobe.lpr.events.consumer.service;

import com.atobe.lpr.events.consumer.model.Photo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PhotoReviewEventsService {
  @Autowired
  PhotoApiClientService photoApiClientService;
  public void process(Photo photo){
    log.info("Processing photo: ", photo);
    photoApiClientService.addPhoto(photo);
  }
}
