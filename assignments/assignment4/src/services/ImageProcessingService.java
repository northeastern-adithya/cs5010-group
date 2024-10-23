package services;

import exception.ImageProcessorException;

/**
 * Service class to help with
 * all image processing operations.
 */
public interface ImageProcessingService {

  /**
   * Loads an image into memory using given name
   * from the given path.
   *
   * @param imagePath the path to the image.
   * @param imageName the name of the image.
   * @throws ImageProcessorException if the image cannot be loaded.
   */
  void loadImage(String imagePath, String imageName) throws ImageProcessorException;

  /**
   * Saves an image from memory to the given path.
   *
   * @param imagePath the path to save the image.
   * @param imageName the name of the image.
   * @throws ImageProcessorException if the image cannot be saved.
   */
  void saveImage(String imagePath, String imageName) throws ImageProcessorException;

  /**
   * Creates the red component of the image.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the red component cannot be created.
   */
  void createRedComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Creates the green component of the image.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the green component cannot be created.
   */
  void createGreenComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Creates the blue component of the image.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the blue component cannot be created.
   */
  void createBlueComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Creates the value component of the image.
   * This is the maximum value of the three components for each pixel of the image.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the value component cannot be created.
   */
  void createValueComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Creates the luma component of the image.
   * Luma has weights of 0.2126 for red, 0.7152 for green, and 0.0722 for blue.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the luma component cannot be created.
   */
  void createLumaComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Creates the intensity component of the image.
   * This is the average of the three components for each pixel in an image.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the intensity component cannot be created.
   */
  void createIntensityComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Flips the image horizontally.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the image cannot be flipped vertically.
   */
  void horizontalFlip(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Flips the image vertically.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the image cannot be flipped vertically.
   */
  void verticalFlip(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Brightens the image by applying a factor to the image.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @param factor               the factor to brighten the image.
   *                             (positive values increase brightness, negative values decrease brightness)
   * @throws ImageProcessorException if the image cannot be brightened.
   */
  void brighten(String imageName, String destinationImageName, int factor) throws ImageProcessorException;

  /**
   * Split the RGB components of the image.
   *
   * @param imageName                 the name of the image.
   * @param destinationImageNameRed   the name of the red image.
   * @param destinationImageNameGreen the name of the green image.
   * @param destinationImageNameBlue  the name of the blue image.
   * @throws ImageProcessorException if the RGB components cannot be split.
   */
  void rgbSplit(String imageName, String destinationImageNameRed, String destinationImageNameGreen, String destinationImageNameBlue) throws ImageProcessorException;

  /**
   * Combines the RGB components of the image.
   *
   * @param imageName      the name of the image.
   * @param redImageName   the name of the red image.
   * @param greenImageName the name of the green image.
   * @param blueImageName  the name of the blue image.
   * @throws ImageProcessorException if the RGB components cannot be combined.
   */
  void rgbCombine(String imageName, String redImageName, String greenImageName, String blueImageName) throws ImageProcessorException;


  /**
   * Blurs the image by applying blurring filters.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the image cannot be blurred.
   */
  void blurImage(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Sharpens the image by applying sharpening filters.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the image cannot be sharpened.
   */
  void sharpenImage(String imageName, String destinationImageName) throws ImageProcessorException;

  /**
   * Creates the sepia component of the image.
   * Photographs taken in the 19th and early 20th century had a characteristic
   * reddish brown tone. This is referred to as a sepia tone.
   *
   * @param imageName            the name of the image.
   * @param destinationImageName the name of the destination image.
   * @throws ImageProcessorException if the sepia component cannot be created.
   */
  void sepiaImage(String imageName, String destinationImageName) throws ImageProcessorException;

}
