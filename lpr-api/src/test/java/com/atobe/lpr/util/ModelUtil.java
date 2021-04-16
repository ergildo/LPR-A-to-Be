package com.atobe.lpr.util;

import com.atobe.lpr.domain.Photo;
import com.atobe.lpr.domain.Review;

public class ModelUtil {

  public static Photo getPhoto() {
    return new Photo(1L, "fileName", "filePath");
  }

  public static Review getReview() {
    return new Review(getPhoto());
  }
}
