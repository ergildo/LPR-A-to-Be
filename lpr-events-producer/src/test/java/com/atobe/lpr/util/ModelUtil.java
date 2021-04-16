package com.atobe.lpr.util;

import com.atobe.model.Event;
import com.atobe.model.Photo;

public class ModelUtil {

  public static Photo getPhoto() {
    return new Photo(1L, "fileName", "filePath");
  }

  public static Event getEvent() {
    Photo photo = getPhoto();
    return Event.builder().body(photo).key(photo.getPhotoId().intValue()).topic("photos-review-events").build();
  }
}
