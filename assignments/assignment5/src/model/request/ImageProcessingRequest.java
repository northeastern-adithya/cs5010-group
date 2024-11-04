package model.request;

import java.util.Optional;

import exception.ImageProcessorException;
import utility.StringUtils;

/**
 * Represents a request to process an image.
 * The request can have the following parameters:
 * - The path of the image
 * - The name of the image
 * - The name of the destination image
 * - The factor to be used for the transformation
 * - The name of the red image
 * - The name of the green image
 * - The name of the blue image
 * - The percentage by which to compress the image
 */
public class ImageProcessingRequest {

  private final String imagePath;
  private final String imageName;
  private final String destinationImageName;
  private final Integer factor;
  private final String redImageName;
  private final String greenImageName;
  private final String blueImageName;
  private final Integer percentage;
  private final Levels levels;

  private ImageProcessingRequest(ImageProcessingRequestBuilder builder) {
    this.imagePath = builder.imagePath;
    this.imageName = builder.imageName;
    this.destinationImageName = builder.destinationImageName;
    this.factor = builder.factor;
    this.redImageName = builder.redImageName;
    this.greenImageName = builder.greenImageName;
    this.blueImageName = builder.blueImageName;
    this.percentage = builder.percentage;
    this.levels = builder.levels;
  }

  /**
   * Returns a new ImageProcessingRequestBuilder.
   *
   * @return a new ImageProcessingRequestBuilder
   */
  public static ImageProcessingRequestBuilder builder() {
    return new ImageProcessingRequestBuilder();
  }

  /**
   * Returns the name of the image.
   * This is a compulsory field and cannot be null or empty.
   *
   * @return the name of the image
   */
  public String getImageName() {
    return imageName;
  }


  /**
   * Returns the path of the image.
   * If the path is not provided, it returns an empty string.
   *
   * @return the path of the image
   */
  public String getImagePath() {
    return Optional.ofNullable(imagePath).orElse(StringUtils.EMPTY_STRING);
  }


  /**
   * Returns the name of the destination image.
   * If the destination image name is not provided, it returns an empty string.
   *
   * @return the name of the destination image
   */
  public String getDestinationImageName() {
    return Optional.ofNullable(destinationImageName)
            .orElse(StringUtils.EMPTY_STRING);
  }

  /**
   * Returns the factor to be used for calculating brightness.
   * If the factor is not provided, it returns an empty optional.
   *
   * @return the factor to be used for calculating brightness.
   */
  public Optional<Integer> getFactor() {
    return Optional.ofNullable(factor);
  }

  public Levels getLevels() {
    return levels;
  }

  /**
   * Returns the name of the red image.
   * If the red image name is not provided, it returns an empty string.
   *
   * @return the name of the red image
   */
  public String getRedImageName() {
    return Optional.ofNullable(redImageName)
            .orElse(StringUtils.EMPTY_STRING);
  }

  /**
   * Returns the name of the green image.
   * If the green image name is not provided, it returns an empty string.
   *
   * @return the name of the green image
   */
  public String getGreenImageName() {
    return Optional.ofNullable(greenImageName)
            .orElse(StringUtils.EMPTY_STRING);
  }

  /**
   * Returns the name of the blue image.
   * If the blue image name is not provided, it returns an empty string.
   *
   * @return the name of the blue image
   */
  public String getBlueImageName() {
    return Optional.ofNullable(blueImageName)
            .orElse(StringUtils.EMPTY_STRING);
  }

  /**
   * Returns the percentage by which to compress the image or the percentage
   * to combine the image.
   * If the percentage is not provided, it returns an empty optional.
   *
   * @return the percentage by which to compress the image
   */
  public Optional<Integer> getPercentage() {
    return Optional.ofNullable(percentage);
  }

  /**
   * Builder class to build an ImageProcessingRequest.
   */
  public static class ImageProcessingRequestBuilder {


    private String imageName;
    private String imagePath;
    private String destinationImageName;
    private Integer factor;
    private String redImageName;
    private String greenImageName;
    private String blueImageName;
    private Integer percentage;
    private Levels levels;

    private ImageProcessingRequestBuilder() {
    }

    /**
     * Sets the path of the image.
     *
     * @param imagePath the path of the image
     * @return the ImageProcessingRequestBuilder
     */
    public ImageProcessingRequestBuilder imagePath(String imagePath) {
      this.imagePath = imagePath;
      return this;
    }

    /**
     * Sets the name of the image.
     *
     * @param imageName the name of the image
     * @return the ImageProcessingRequestBuilder
     */
    public ImageProcessingRequestBuilder imageName(String imageName) {
      this.imageName = imageName;
      return this;
    }

    /**
     * Sets the name of the destination image.
     *
     * @param destinationImageName the name of the destination image
     * @return the ImageProcessingRequestBuilder
     */
    public ImageProcessingRequestBuilder destinationImageName(String destinationImageName) {
      this.destinationImageName = destinationImageName;
      return this;
    }

    /**
     * Sets the factor to be used for calculating brightness.
     *
     * @param factor the factor to be used for calculating brightness
     * @return the ImageProcessingRequestBuilder
     */
    public ImageProcessingRequestBuilder factor(Integer factor) {
      this.factor = factor;
      return this;
    }

    /**
     * Sets the name of the red image.
     *
     * @param redImageName the name of the red image
     * @return the ImageProcessingRequestBuilder
     */
    public ImageProcessingRequestBuilder redImageName(String redImageName) {
      this.redImageName = redImageName;
      return this;
    }

    /**
     * Sets the name of the green image.
     *
     * @param greenImageName the name of the green image
     * @return the ImageProcessingRequestBuilder
     */
    public ImageProcessingRequestBuilder greenImageName(String greenImageName) {
      this.greenImageName = greenImageName;
      return this;
    }

    /**
     * Sets the name of the blue image.
     *
     * @param blueImageName the name of the blue image
     * @return the ImageProcessingRequestBuilder
     */
    public ImageProcessingRequestBuilder blueImageName(String blueImageName) {
      this.blueImageName = blueImageName;
      return this;
    }

    /**
     * Sets the percentage by which to compress the image.
     *
     * @param percentage the percentage by which to compress the image
     * @return the ImageProcessingRequestBuilder
     */
    public ImageProcessingRequestBuilder percentage(Integer percentage) {
      this.percentage = percentage;
      return this;
    }

    public ImageProcessingRequestBuilder levels(int black, int mid, int white) {
      this.levels = new Levels(black, mid, white);
      return this;
    }

    /**
     * Builds an ImageProcessingRequest.
     *
     * @return the ImageProcessingRequest
     * @throws ImageProcessorException if the image name is null or empty
     */
    public ImageProcessingRequest build() throws ImageProcessorException {
      validateBuildParams();
      return new ImageProcessingRequest(this);
    }

    /**
     * Validates the build parameters.
     *
     * @throws ImageProcessorException if the image name is null or empty
     */
    private void validateBuildParams() throws ImageProcessorException {
      if (StringUtils.isNullOrEmpty(imageName)) {
        throw new ImageProcessorException("Image name cannot be null or empty");
      }
    }

  }

  /**
   * Represents the levels of the image.
   */
  public static class Levels {
    private final int black;
    private final int white;
    private final int mid;

    /**
     * Constructor to initialize the levels.
     *
     * @param black the black level
     * @param mid   the mid level
     * @param white the white level
     */
    public Levels(int black, int mid, int white) {
      this.black = black;
      this.white = white;
      this.mid = mid;
    }

    public int getBlack() {
      return black;
    }

    public int getWhite() {
      return white;
    }

    public int getMid() {
      return mid;
    }
  }
}