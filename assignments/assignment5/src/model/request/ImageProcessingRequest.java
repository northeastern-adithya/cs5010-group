package model.request;

import java.util.Optional;

import exception.ImageProcessorException;
import utility.StringUtils;

public class ImageProcessingRequest {

  private final String imagePath;
  private final String imageName;
  private final String destinationImageName;
  private final Integer factor;
  private final String redImageName;
  private final String greenImageName;
  private final String blueImageName;
  private final Integer percentage;

  private ImageProcessingRequest(ImageProcessingRequestBuilder builder) {
    this.imagePath = builder.imagePath;
    this.imageName = builder.imageName;
    this.destinationImageName = builder.destinationImageName;
    this.factor = builder.factor;
    this.redImageName = builder.redImageName;
    this.greenImageName = builder.greenImageName;
    this.blueImageName = builder.blueImageName;
    this.percentage = builder.percentage;
  }

  public static ImageProcessingRequestBuilder builder() {
    return new ImageProcessingRequestBuilder();
  }
  public String getImageName() {
    return imageName;
  }


  public String getImagePath() {
    return Optional.ofNullable(imagePath).orElse(StringUtils.EMPTY_STRING);
  }


  public String getDestinationImageName() {
    return Optional.ofNullable(destinationImageName)
            .orElse(StringUtils.EMPTY_STRING);
  }

  public Optional<Integer> getFactor() {
    return Optional.ofNullable(factor);
  }

  public String getRedImageName() {
    return Optional.ofNullable(redImageName)
            .orElse(StringUtils.EMPTY_STRING);
  }

  public String getGreenImageName() {
    return Optional.ofNullable(greenImageName)
            .orElse(StringUtils.EMPTY_STRING);
  }

  public String getBlueImageName() {
    return Optional.ofNullable(blueImageName)
            .orElse(StringUtils.EMPTY_STRING);
  }

  public Optional<Integer> getPercentage() {
    return Optional.ofNullable(percentage);
  }

  public static class ImageProcessingRequestBuilder {


    private String imageName;
    private String imagePath;
    private String destinationImageName;
    private Integer factor;
    private String redImageName;
    private String greenImageName;
    private String blueImageName;
    private Integer percentage;

    private ImageProcessingRequestBuilder() {
    }

    public ImageProcessingRequestBuilder imagePath(String imagePath) {
      this.imagePath = imagePath;
      return this;
    }

    public ImageProcessingRequestBuilder imageName(String imageName) {
      this.imageName = imageName;
      return this;
    }

    public ImageProcessingRequestBuilder destinationImageName(String destinationImageName) {
      this.destinationImageName = destinationImageName;
      return this;
    }

    public ImageProcessingRequestBuilder factor(Integer factor) {
      this.factor = factor;
      return this;
    }

    public ImageProcessingRequestBuilder redImageName(String redImageName) {
      this.redImageName = redImageName;
      return this;
    }

    public ImageProcessingRequestBuilder greenImageName(String greenImageName) {
      this.greenImageName = greenImageName;
      return this;
    }

    public ImageProcessingRequestBuilder blueImageName(String blueImageName) {
      this.blueImageName = blueImageName;
      return this;
    }

    public ImageProcessingRequestBuilder percentage(Integer percentage) {
      this.percentage = percentage;
      return this;
    }

    public ImageProcessingRequest build() throws ImageProcessorException {
      validateBuildParams();
      return new ImageProcessingRequest(this);
    }

    private void validateBuildParams() throws ImageProcessorException {
      if (StringUtils.isNullOrEmpty(imageName)) {
        throw new ImageProcessorException("Image name cannot be null or empty");
      }
    }

  }


}
