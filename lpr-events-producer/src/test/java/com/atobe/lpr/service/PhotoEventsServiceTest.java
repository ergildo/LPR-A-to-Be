package com.atobe.lpr.service;

import static com.atobe.lpr.util.ModelUtil.getEvent;
import static com.atobe.lpr.util.ModelUtil.getPhoto;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;


import com.atobe.model.Event;
import com.atobe.model.Photo;
import com.atobe.producer.EventProducer;
import com.atobe.service.PhotoEventsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PhotoEventsService.class})
@ActiveProfiles("test")
public class PhotoEventsServiceTest {
  @Autowired
  PhotoEventsService photoEventService;

  @MockBean
  EventProducer eventProducer;


  @SneakyThrows
  @Test
  public void sendSuccess() {
    Photo photo = getPhoto();
    Event event = getEvent();
    photoEventService.send(photo);
    verify(eventProducer).send(event);
  }

  @SneakyThrows
  @Test
  public void sendFail() {
    Photo photo = getPhoto();
    Event event = getEvent();
    doThrow(new RuntimeException()).when(eventProducer).send(event);
    Assertions.assertThrows(RuntimeException.class, () -> photoEventService.send(photo));
    verify(eventProducer).send(event);
  }


}
