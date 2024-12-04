package model;

public interface ImageModelV2 extends ImageModel {

  /**
   * Applies a dithering effect to the image.
   * This operation breaks down an image that has many colors into
   * an image that is made of dots from just a few colors.
   */
  void applyDithering();

  /**
   * Applies a dithering effect to a predetermined proportion of the image, starting from the left
   * edge and ending at the predetermined percentage width.
   *
   * @param p the percentage of the image width to apply the dithering effect to
   */
  void applyDithering(double p);
}
