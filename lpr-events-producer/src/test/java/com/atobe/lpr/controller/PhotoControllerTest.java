package com.atobe.lpr.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.atobe.controller.PhotoEventsController;
import com.atobe.exception.PublishEventException;
import com.atobe.lpr.util.JsonUtil;
import com.atobe.lpr.util.TestUtil;
import com.atobe.model.Photo;
import com.atobe.service.PhotoEventsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({PhotoEventsController.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PhotoControllerTest {

  @MockBean
  PhotoEventsService photoEventsService;
  @Autowired
  private MockMvc mvc;

  @SneakyThrows
  @Test
  public void publishPhotoEventSuccess() {

    String request = TestUtil.loadRequest("publish-photo-event");
    String expectedResponse = TestUtil.loadResponse("send-event-success");
    Photo photo = JsonUtil.parseJsonString(request, Photo.class);

    mvc.perform(post("/events/photos/review")
        .contentType("application/json")
        .content(request)

    ).andExpect(status().isAccepted()).andExpect(content().json(expectedResponse, true));
    verify(photoEventsService, times(1)).send(photo);
  }

  @SneakyThrows
  @Test
  public void publishPhotoEventFail() {

    String request = TestUtil.loadRequest("publish-photo-event");
    Photo photo = JsonUtil.parseJsonString(request, Photo.class);
    String expectedResponse = TestUtil.loadResponse("send-event-fail");
    doThrow(PublishEventException.class).when(photoEventsService).send(photo);

    mvc.perform(post("/events/photos/review")
        .contentType("application/json")
        .content(request)

    ).andExpect(status().isInternalServerError()).andExpect(content().json(expectedResponse));
    verify(photoEventsService, times(1)).send(photo);
  }


}
