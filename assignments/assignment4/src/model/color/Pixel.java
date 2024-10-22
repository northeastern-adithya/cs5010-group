package model.color;

public interface Pixel {
  /**
   * Gets the value of the pixel.
   *
   * @return the value of the pixel
   */
  int getValue();

  /**
   * Gets the luma of the pixel.
   *
   * @return the luma of the pixel
   */
  double getLuma();

  /**
   * Gets the intensity of the pixel.
   *
   * @return the intensity of the pixel
   */
  double getIntensity();

  /**
   * Adjusts the brightness of the pixel.
   *
   * @param factor the factor by which to adjust the brightness (positive to brighten, negative to darken)
   * @return a new Pixel with adjusted brightness
   */
  Pixel adjustBrightness(int factor);


  Pixel createRedComponent();

  Pixel createGreenComponent();

  Pixel createBlueComponent();

  int getRed();

  int getGreen();

  int getBlue();

}
