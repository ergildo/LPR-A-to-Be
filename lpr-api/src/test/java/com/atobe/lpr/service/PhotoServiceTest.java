package com.atobe.lpr.service;

import static com.atobe.lpr.util.ModelUtil.getPhoto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.atobe.lpr.domain.Photo;
import com.atobe.lpr.domain.Review;
import com.atobe.lpr.exception.PhotoAlreadyExistsException;
import com.atobe.lpr.exception.ResultNotFoundException;
import com.atobe.lpr.repository.PhotoRepository;
import com.atobe.lpr.repository.ReviewRepository;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PhotoService.class})
@ActiveProfiles("test")
public class PhotoServiceTest {
  @Autowired PhotoService photoService;
  @MockBean PhotoRepository photoRepository;
  @MockBean ReviewRepository reviewRepository;

  @SneakyThrows
  @Test
  public void importPhotoSuccess() {
    Long photoId = 1l;
    Photo photo = getPhoto();
    Mockito.when(photoRepository.findById(photoId)).thenReturn(Optional.empty());
    Mockito.when(photoRepository.save(photo)).thenReturn(photo);
    photoService.importPhoto(photo);
    verify(photoRepository, Mockito.times(1)).save(any(Photo.class));
    verify(reviewRepository, Mockito.times(1)).save(any(Review.class));
  }

  @Test
  public void importPhotoAlreadyExists() {
    Photo photo = getPhoto();
    Mockito.when(photoRepository.findById(photo.getPhotoId())).thenReturn(Optional.of(photo));
    Assertions.assertThrows(
        PhotoAlreadyExistsException.class, () -> photoService.importPhoto(photo));
    verify(photoRepository, never()).save(any(Photo.class));
    verify(reviewRepository, never()).save(any(Review.class));
  }

  @Test
  public void findByIdSuccess() {
    Long photoId = 1l;
    Photo photo = getPhoto();
    Mockito.when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));
    Photo result = photoService.findById(photoId);
    assertEquals(photo, result);
  }

  @Test
  public void findByIdFail() {
    Long photoId = 1l;
    Mockito.when(photoRepository.findById(photoId)).thenThrow(new ResultNotFoundException(""));

    Assertions.assertThrows(ResultNotFoundException.class, () -> photoService.findById(photoId));
  }

}
