package com.atobe.lpr.service;

import com.atobe.lpr.domain.Photo;
import com.atobe.lpr.domain.Review;
import com.atobe.lpr.exception.PhotoAlreadyExistsException;
import com.atobe.lpr.exception.ResultNotFoundException;
import com.atobe.lpr.repository.PhotoRepository;
import com.atobe.lpr.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PhotoService {

  @Autowired PhotoRepository photoRepository;
  @Autowired ReviewRepository reviewRepository;

  public Review importPhoto(Photo photo) {
    validatePhoto(photo);
    Photo savedPhoto = photoRepository.save(photo);
    Review review = new Review(savedPhoto);
    reviewRepository.save(review);
    return review;
  }

  private void validatePhoto(Photo photo) {
    photoRepository
        .findById(photo.getPhotoId())
        .ifPresent(
            (p) -> {
              throw new PhotoAlreadyExistsException(
                  String.format("Photo already exists. {%s}", photo.getPhotoId()));
            });
  }

  public Photo findById(Long photoId) {
    return photoRepository
        .findById(photoId)
        .orElseThrow(
            () ->
                new ResultNotFoundException(
                    String.format("Photo not found. photoId {%s}", photoId)));
  }
}
