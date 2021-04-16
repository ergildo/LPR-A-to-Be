package com.atobe.lpr.service;

import com.atobe.lpr.domain.Review;
import com.atobe.lpr.domain.ReviewStatus;
import com.atobe.lpr.dto.ReviewResultDTO;
import com.atobe.lpr.exception.ResultNotFoundException;
import com.atobe.lpr.repository.ReviewRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {
  @Autowired ReviewRepository reviewRepository;

  public Review save(Review review) {
    return reviewRepository.save(review);
  }

  public Review findById(Long reviewId) {
    return reviewRepository
        .findById(reviewId)
        .orElseThrow(
            () ->
                new ResultNotFoundException(
                    String.format("Review not found. reviewId {%s}", reviewId)));
  }

  public List<Review> findByReviewStatus(ReviewStatus reviewStatus) {
    return reviewRepository.findByReviewStatus(reviewStatus);
  }

  public Review reviewPhoto(Long reviewId) {
    Review review = findById(reviewId);
    review.setReviewStatus(ReviewStatus.IN_PROGRESS);
    return save(review);
  }

  public Review submitReview(ReviewResultDTO reviewResult) {
    Review review = findById(reviewResult.getReviewId());
    review.setLicensePlateNumber(reviewResult.getLicensePlateNumber());
    review.setReviewStatus(ReviewStatus.COMPLETED);
    return save(review);
  }
}
