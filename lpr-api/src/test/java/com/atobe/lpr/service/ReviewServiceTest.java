package com.atobe.lpr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static com.atobe.lpr.util.ModelUtil.*;


import com.atobe.lpr.domain.Review;
import com.atobe.lpr.domain.ReviewStatus;
import com.atobe.lpr.dto.ReviewResultDTO;
import com.atobe.lpr.exception.ResultNotFoundException;
import com.atobe.lpr.repository.ReviewRepository;
import com.atobe.lpr.util.ModelUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
@SpringBootTest(classes = {ReviewService.class})
@ActiveProfiles("test")
public class ReviewServiceTest {
  @Autowired ReviewService reviewService;
  @MockBean ReviewRepository reviewRepository;

  @Test
  public void reviewPhotoSuccess() {
    Long id = 1l;
    Review review = getReview();
    ReviewStatus inProgress = ReviewStatus.IN_PROGRESS;
    Mockito.when(reviewRepository.findById(id)).thenReturn(Optional.of(review));
    reviewService.reviewPhoto(id);
    verify(review, Mockito.times(1)).setReviewStatus(inProgress);
    verify(reviewRepository, Mockito.times(1)).save(any());
  }

  @Test
  public void reviewPhotoSuccessFail() {
    Long id = 1l;
    Review review = getReview();
    ReviewStatus inProgress = ReviewStatus.IN_PROGRESS;
    Mockito.when(reviewRepository.findById(id)).thenThrow(new ResultNotFoundException(""));

    Assertions.assertThrows(ResultNotFoundException.class, () -> reviewService.reviewPhoto(id));

    ;
    verify(review, never()).setReviewStatus(inProgress);
    verify(reviewRepository, never()).save(any());
  }

  @Test
  public void submitPhotoSuccess() {
    Long id = 1l;
    Review review = getReview();
    ReviewStatus completed = ReviewStatus.COMPLETED;

    ReviewResultDTO reviewResult =
        ReviewResultDTO.builder().reviewId(id).licensePlateNumber("ABC-123").build();

    Mockito.when(reviewRepository.findById(id)).thenReturn(Optional.of(review));
    reviewService.submitReview(reviewResult);
    verify(review, Mockito.times(1)).setReviewStatus(completed);
    verify(reviewRepository, Mockito.times(1)).save(any());
  }

  @Test
  public void submitPhotoSuccessFail() {
    Long id = 1l;
    Review review = getReview();
    ReviewStatus completed = ReviewStatus.COMPLETED;

    ReviewResultDTO reviewResult = Mockito.mock(ReviewResultDTO.class);

    Mockito.when(reviewRepository.findById(id)).thenThrow(new ResultNotFoundException(""));

    Assertions.assertThrows(
        ResultNotFoundException.class, () -> reviewService.submitReview(reviewResult));

    ;
    verify(review, never()).setReviewStatus(completed);
    verify(reviewRepository, never()).save(any());
  }

  @Test
  public void findByIdSuccess() {
    Long id = 1l;
    Review review = getReview();
    Mockito.when(reviewRepository.findById(id)).thenReturn(Optional.of(review));
    Review result = reviewService.findById(id);
    assertEquals(review, result);
  }

  @Test
  public void findByIdFail() {
    Long photoId = 1l;
    Review review = getReview();

    Mockito.when(reviewRepository.findById(photoId)).thenThrow(new ResultNotFoundException(""));

    Assertions.assertThrows(ResultNotFoundException.class, () -> reviewService.findById(photoId));
  }

  @Test
  public void findByReviewStatusSuccess() {

    ReviewStatus reviewStatus = ReviewStatus.COMPLETED;
    List<Review> reviews = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Review review = ModelUtil.getReview();
      review.setReviewStatus(reviewStatus);
      reviews.add(review);
    }

    Mockito.when(reviewRepository.findByReviewStatus(reviewStatus)).thenReturn(reviews);
    List<Review> byReviewStatus = reviewService.findByReviewStatus(reviewStatus);

    assertEquals(reviews.size(), byReviewStatus.size());

    org.assertj.core.api.Assertions.assertThat(byReviewStatus).hasSameElementsAs(reviews);
  }
}
