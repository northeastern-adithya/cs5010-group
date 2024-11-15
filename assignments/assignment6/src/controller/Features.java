package controller;

/**
 * Features interface that represents the features provided by the image
 * processing application.
 */
public interface Features {
  /**
   * Loads an image into memory from disk.
   */
  void loadImage();

  /**
   * Saves the image to the disk.
   */
  void saveImage();

  /**
   * Creates the sepia component of the image.
   * Photographs taken in the 19th and early 20th century had a characteristic
   * reddish brown tone. This is referred to as a sepia tone.
   */
  void applySepia();

  /**
   * Clears the memory of the image processing application.
   */
  void clearMemory();
}