package model.visual;

import model.color.Pixel;


public class RenderedImage implements Image {
  private final Pixel[][] pixels;

  public RenderedImage(Pixel[][] pixels) {
    this.pixels = pixels;
  }

  @Override
  public Pixel getPixel(int x, int y) {
    return pixels[x][y];
  }


  @Override
  public int getWidth() {
    return pixels.length;
  }

  public int getHeight() {
    return pixels[0].length;
  }

  @Override
  public Image adjustImageBrightness(int factor) {
    int height = getHeight();
    int width = getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = getPixel(x, y).adjustBrightness(factor);
      }
    }
    return new RenderedImage(newPixelArray);
  }
}