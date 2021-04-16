package com.atobe.lpr.util;

import java.io.File;
import java.nio.file.Files;
import org.springframework.util.ResourceUtils;

public class FileUtil {


  public static byte[] loadPhoto(String filename) throws Exception {
    return loadFile("classpath:photos/" + filename);
  }

  private static byte[] loadFile(String filePath) throws Exception {
    File file = ResourceUtils.getFile(filePath);
   return Files.readAllBytes(file.toPath());
  }
}
