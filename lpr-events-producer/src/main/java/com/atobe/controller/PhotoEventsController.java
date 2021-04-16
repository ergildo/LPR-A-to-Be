package com.atobe.controller;

import com.atobe.model.Photo;
import com.atobe.service.PhotoEventsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events/photos")
@Slf4j
public class PhotoEventsController {

  @Autowired
  PhotoEventsService photoEventService;

  @PostMapping("/review")
  public ResponseEntity<Photo> publishPhotoEvent(@RequestBody Photo photo) throws JsonProcessingException, ExecutionException, InterruptedException {
    photoEventService.send(photo);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(photo);
  }

}
