package model.visual;

import model.color.Pixel;


public class RenderedImage implements Image {
  private final Pixel[][] pixels;
  private final int width;
  private final int height;

  public RenderedImage(int width, int height, Pixel[][] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = pixels;
  }

  @Override
  public Pixel getPixel(int x, int y) {
    return pixels[x][y];
  }

  @Override
  public Image adjustImageBrightness(int factor) {

    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = getPixel(x, y).adjustBrightness(factor);
      }
    }
    return new RenderedImage(width, height, newPixelArray);
  }
}