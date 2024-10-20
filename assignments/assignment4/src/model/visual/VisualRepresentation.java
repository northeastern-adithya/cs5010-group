package model.visual;

import model.color.Pixel;

public interface VisualRepresentation {
  /**
   * Gets the pixel at the specified coordinates.
   *
   * @param x the x-coordinate of the pixel
   * @param y the y-coordinate of the pixel
   * @return the pixel at the specified coordinates
   */
  Pixel getPixel(int x, int y);

  VisualRepresentation adjustImageBrightness(int factor);
}
