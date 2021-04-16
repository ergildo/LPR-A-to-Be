package com.atobe.service;

import com.atobe.model.Event;
import com.atobe.model.Photo;
import com.atobe.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhotoEventsService {
  @Value("${events.topics.events-photo-review}")
  String photoReviewTopic;
  @Autowired
  EventProducer eventProducer;

  public void send(Photo photo){
    Event photoEvent = Event.builder().body(photo).key(photo.getPhotoId().intValue()).topic(photoReviewTopic).build();

eventProducer.send(photoEvent);
  }
}
