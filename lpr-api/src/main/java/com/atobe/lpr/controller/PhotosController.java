/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen Do not edit the class manually.
 */
package com.atobe.lpr.controller;

import com.atobe.lpr.domain.Photo;
import com.atobe.lpr.domain.Review;
import com.atobe.lpr.service.PhotoService;
import com.atobe.lpr.util.FileUtil;
import java.io.InputStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/photos")
public class PhotosController {

  @Autowired PhotoService photoService;

  @PostMapping("/import")
  @ResponseStatus(value = HttpStatus.CREATED)
  @ResponseBody
  Review addPhoto(@RequestBody Photo photo) {
    return photoService.importPhoto(photo);
  }

  @SneakyThrows
  @GetMapping("/{photoId}/download")
  @ResponseBody
  byte[] downloadPhoto(@PathVariable("photoId") Long photoId) {
    Photo photo = photoService.findById(photoId);
    //It will return always a fake licence plate
   return FileUtil.loadPhoto("fake-licence-plate.png");
  }

  @GetMapping("/{photoId}")
  @ResponseBody
  Photo getPhotoById(@PathVariable("photoId") Long photoId) {
    return photoService.findById(photoId);
  }
}
