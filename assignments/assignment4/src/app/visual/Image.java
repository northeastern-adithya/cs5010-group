package app.visual;

import app.color.Pixel;
import app.color.RGB;


public class Image implements VisualRepresentation {
  private final Pixel[][] pixels;
  private final int width;
  private final int height;

  public Image(int width, int height, Pixel[][] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = pixels;
  }

  @Override
  public Pixel getPixel(int x, int y) {
    return pixels[x][y];
  }

  @Override
  public VisualRepresentation adjustImageBrightness(int factor) {

    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = getPixel(x, y).adjustBrightness(factor);
      }
    }
    return new Image(width, height, newPixelArray);
  }
}