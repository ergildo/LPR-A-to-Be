package com.atobe.lpr.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.atobe.lpr.domain.Photo;
import com.atobe.lpr.domain.Review;
import com.atobe.lpr.exception.PhotoAlreadyExistsException;
import com.atobe.lpr.exception.ResultNotFoundException;
import com.atobe.lpr.service.PhotoService;
import com.atobe.lpr.util.FileUtil;
import com.atobe.lpr.util.TestUtil;
import com.atobe.lpr.util.JsonUtil;
import java.io.InputStream;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

@WebMvcTest({PhotosController.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PhotoControllerTest {

  @Autowired private MockMvc mvc;

@MockBean
  PhotoService photoService;

  @SneakyThrows
  @Test
  public void importPhotoSuccess(){

    String requst = TestUtil.loadRequest("import-photos-success");
    String response = TestUtil.loadResponse("import-photos-success");
    Photo photo = JsonUtil.parseJsonString(requst, Photo.class);

    when(photoService.importPhoto(photo)).thenReturn(new Review(photo));


 mvc.perform(post("/photos/import")
        .contentType("application/json")
        .content(requst)

    ).andExpect(status().isCreated()).andExpect(content().json(response, true));
verify(photoService, times(1)).importPhoto(photo);
  }

  @SneakyThrows
  @Test
  public void importPhotoAlreadyExists(){

    String requst = TestUtil.loadRequest("import-photos-success");
    String response = TestUtil.loadResponse("import-photos-photo-already-exists");
    Photo photo = JsonUtil.parseJsonString(requst, Photo.class);

    when(photoService.importPhoto(photo)).thenThrow( new PhotoAlreadyExistsException(
        String.format("Photo already exists. {%s}", photo.getPhotoId())));


    mvc.perform(post("/photos/import")
        .contentType("application/json")
        .content(requst)

    ).andExpect(status().isConflict()).andExpect(content().json(response, true));
    verify(photoService, times(1)).importPhoto(photo);
  }

  @SneakyThrows
  @Test
  public void findByIdSuccess(){

    String requst = TestUtil.loadRequest("import-photos-success");
    String response = TestUtil.loadResponse("get-by-photos-success");
    Photo photo = JsonUtil.parseJsonString(requst, Photo.class);

    long photoId = 1L;
    when(photoService.findById(photoId)).thenReturn(photo);


    mvc.perform(get("/photos/1")
        .contentType("application/json")
            ).andExpect(status().isOk()).andExpect(content().json(response, true));
    verify(photoService, times(1)).findById(photoId);
  }

  @SneakyThrows
  @Test
  public void findByIdResultNotFound(){

    String requst = TestUtil.loadRequest("import-photos-success");
    String response = TestUtil.loadResponse("get-by-photos-result-not-found");
    Photo photo = JsonUtil.parseJsonString(requst, Photo.class);

    long photoId = 1L;
    when(photoService.findById(photoId)).thenThrow(new ResultNotFoundException(
        String.format("Photo not found. photoId {%s}", photoId)));

    mvc.perform(get("/photos/1")
        .contentType("application/json")
    ).andExpect(status().isNotFound()).andExpect(content().json(response, true));
    verify(photoService, times(1)).findById(photoId);
  }

  @SneakyThrows
  @Test
  public void downloadSuccess(){

    String requst = TestUtil.loadRequest("import-photos-success");
    Photo photo = JsonUtil.parseJsonString(requst, Photo.class);

    long photoId = 1L;
    when(photoService.findById(photoId)).thenReturn(photo);

    byte[] bytes = FileUtil.loadPhoto("fake-licence-plate.png");


    mvc.perform(get("/photos/1/download")
        .contentType("application/json")
    ).andExpect(status().isOk()).andExpect(content().bytes(bytes));
    verify(photoService, times(1)).findById(photoId);
  }

  @SneakyThrows
  @Test
  public void downloadResultNotFound(){

    String requst = TestUtil.loadRequest("import-photos-success");
    String response = TestUtil.loadResponse("get-by-photos-result-not-found");
    Photo photo = JsonUtil.parseJsonString(requst, Photo.class);

    long photoId = 1L;
    when(photoService.findById(photoId)).thenThrow(new ResultNotFoundException(
        String.format("Photo not found. photoId {%s}", photoId)));

    mvc.perform(get("/photos/1/download")
        .contentType("application/json")
    ).andExpect(status().isNotFound()).andExpect(content().json(response, true));
    verify(photoService, times(1)).findById(photoId);
  }


}
