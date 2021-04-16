package com.atobe.lpr.repository;

import com.atobe.lpr.domain.Review;
import com.atobe.lpr.domain.ReviewStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByReviewStatus(ReviewStatus reviewStatus);
}
