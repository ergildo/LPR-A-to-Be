package com.atobe.lpr.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.atobe.lpr.controller.request.ReviewRequest;
import com.atobe.lpr.domain.Photo;
import com.atobe.lpr.domain.Review;
import com.atobe.lpr.domain.ReviewStatus;
import com.atobe.lpr.dto.ReviewResultDTO;
import com.atobe.lpr.exception.ResultNotFoundException;
import com.atobe.lpr.service.PhotoService;
import com.atobe.lpr.service.ReviewService;
import com.atobe.lpr.util.FileUtil;
import com.atobe.lpr.util.JsonUtil;
import com.atobe.lpr.util.TestUtil;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({ReviewsController.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReviewsControllerTest {

  @Autowired private MockMvc mvc;

@MockBean
  PhotoService photoService;

  @MockBean
  ReviewService reviewService;

  @SneakyThrows
  @Test
  public void reviewPhotoPhotoSuccess(){

    String importedReview = TestUtil.loadResponse("import-photos-success");
    String expectedResponse = TestUtil.loadResponse("review-photos-success");
    Review review = JsonUtil.parseJsonString(importedReview, Review.class);
    review.setReviewStatus(ReviewStatus.IN_PROGRESS);

    long reviewId = 1L;
    when(reviewService.reviewPhoto(reviewId)).thenReturn(review);


    mvc.perform(patch("/reviews/1")
        .contentType("application/json")
    ).andExpect(status().isOk()).andExpect(content().json(expectedResponse, true));
    verify(reviewService, times(1)).reviewPhoto(reviewId);

  }

  @SneakyThrows
  @Test
  public void reviewPhotoPhotoNotFound(){

    String importedReview = TestUtil.loadResponse("import-photos-success");
    String expectedResponse = TestUtil.loadResponse("get-by-reviews-result-not-found");
    Review review = JsonUtil.parseJsonString(importedReview, Review.class);
    review.setReviewStatus(ReviewStatus.IN_PROGRESS);

    long reviewId = 1L;
    when(reviewService.reviewPhoto(reviewId)).thenThrow(throwsReviewNotFoundException(reviewId) );


    mvc.perform(patch("/reviews/1")
        .contentType("application/json")
    ).andExpect(status().isNotFound()).andExpect(content().json(expectedResponse, true));
    verify(reviewService, times(1)).reviewPhoto(reviewId);

  }

  @SneakyThrows
  @Test
  public void submitPhotoPhotoSuccess(){

    String request = TestUtil.loadRequest("submit-photos-success");
    String reviewExpected = TestUtil.loadResponse("import-photos-success");
    Review review = JsonUtil.parseJsonString(reviewExpected, Review.class);
    review.setReviewStatus(ReviewStatus.COMPLETED);
    Long reviewId = 1L;
    ReviewResultDTO reviewResultDTO = ReviewResultDTO.builder().reviewId(reviewId).licensePlateNumber("123-ABC").build();


    when(reviewService.submitReview(reviewResultDTO)).thenReturn(review);


    mvc.perform(post("/reviews/1")
        .contentType("application/json")
        .content(request)
    ).andExpect(status().isOk());

    verify(reviewService, times(1)).submitReview(reviewResultDTO);

  }

  @SneakyThrows
  @Test
  public void submitPhotoPhotoNotFound(){

    String expectedResponse = TestUtil.loadResponse("get-by-reviews-result-not-found");



    String request = TestUtil.loadRequest("submit-photos-success");
    String reviewExpected = TestUtil.loadResponse("import-photos-success");
    Review review = JsonUtil.parseJsonString(reviewExpected, Review.class);
    review.setReviewStatus(ReviewStatus.COMPLETED);
    Long reviewId = 1L;
    ReviewResultDTO reviewResultDTO = ReviewResultDTO.builder().reviewId(reviewId).licensePlateNumber("123-ABC").build();

    when(reviewService.submitReview(reviewResultDTO)).thenThrow(throwsReviewNotFoundException(reviewId) );


    mvc.perform(post("/reviews/1")
        .contentType("application/json")
        .content(request)
    ).andExpect(status().isNotFound()).andExpect(content().json(expectedResponse, true));
    verify(reviewService, times(1)).submitReview(reviewResultDTO);

  }


  @SneakyThrows
  @Test
  public void findByIdSuccess(){


    String response = TestUtil.loadResponse("import-photos-success");
    Review review = JsonUtil.parseJsonString(response, Review.class);

    long reviewId = 1L;
    when(reviewService.findById(reviewId)).thenReturn(review);


    mvc.perform(get("/reviews/1")
        .contentType("application/json")
            ).andExpect(status().isOk()).andExpect(content().json(response, true));
    verify(reviewService, times(1)).findById(reviewId);
  }

  @SneakyThrows
  @Test
  public void findByStatusSuccess(){


    String importedReview = TestUtil.loadResponse("import-photos-success");
    Review review = JsonUtil.parseJsonString(importedReview, Review.class);

    String expectedResponse = TestUtil.loadResponse("get-by-status-reviews-success");

    ReviewStatus reviewStatus = ReviewStatus.CREATED;
    when(reviewService.findByReviewStatus(reviewStatus)).thenReturn(List.of(review));


    mvc.perform(get(String.format("/reviews?reviewStatus=%s", reviewStatus))
        .contentType("application/json")
    ).andExpect(status().isOk()).andExpect(content().json(expectedResponse, true));
    verify(reviewService, times(1)).findByReviewStatus(reviewStatus);
  }

  @SneakyThrows
  @Test
  public void findByStatusNoContent(){


    String importedReview = TestUtil.loadResponse("import-photos-success");
    Review review = JsonUtil.parseJsonString(importedReview, Review.class);


    ReviewStatus reviewStatus = ReviewStatus.CREATED;
    when(reviewService.findByReviewStatus(reviewStatus)).thenReturn(List.of());


    mvc.perform(get(String.format("/reviews?reviewStatus=%s", reviewStatus))
        .contentType("application/json")
    ).andExpect(status().isOk()).andExpect(content().json("[]", true));
    verify(reviewService, times(1)).findByReviewStatus(reviewStatus);
  }

  @SneakyThrows
  @Test
  public void findByIdResultNotFound(){


    long reviewId = 1L;
    when(reviewService.findById(reviewId)).thenThrow(throwsReviewNotFoundException(reviewId));

    String response = TestUtil.loadResponse("get-by-reviews-result-not-found");



    mvc.perform(get("/reviews/1")
        .contentType("application/json")
    ).andExpect(status().isNotFound()).andExpect(content().json(response, true));
    verify(reviewService, times(1)).findById(reviewId);
  }
    private ResultNotFoundException throwsReviewNotFoundException(long reviewId) {
    return throwsResultNotFoundException(reviewId, "Review not found. reviewId {%s}");
  }
  private ResultNotFoundException throwsPhotoNotFoundException(long photoId) {
    return throwsResultNotFoundException(photoId, "Photo not found. photoId {%s}");
  }

  private ResultNotFoundException throwsResultNotFoundException(long photoId, String s) {
    return new ResultNotFoundException(
        String.format(s, photoId));
  }




}
