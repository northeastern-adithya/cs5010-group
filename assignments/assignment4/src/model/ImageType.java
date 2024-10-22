package model;

import java.util.Arrays;
import java.util.Optional;

import exception.NotImplementedException;

public enum ImageType {

  PNG("png"),
  PPM("ppm"),
  JPG("jpg"),
  JPEG("jpeg");

  private final String extension;

  ImageType(String extension) {
    this.extension = extension;
  }

  public static ImageType fromExtension(String extension) {
    return Arrays.stream(ImageType.values()).filter(imageType -> imageType.getExtension().equals(extension)).findFirst()
            .orElseThrow(() -> new NotImplementedException(String.format("Image type with extension %s not supported", extension)));
  }

  public String getExtension() {
    return extension;
  }
}
