package model.visual;

import java.util.Objects;
import java.util.function.Function;

import factories.ImageFactory;
import model.pixels.Pixel;

public class RenderedImage implements Image {
  private final Pixel[][] pixels;

  public RenderedImage(Pixel[][] pixels) {
    Objects.requireNonNull(pixels, "Pixel array cannot be null");
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

  @Override
  public int getHeight() {
    return pixels[0].length;
  }

  @Override
  public Image createRedComponent() {
    return transformImage(Pixel::createRedComponent);
  }

  @Override
  public Image createGreenComponent() {
    return transformImage(Pixel::createGreenComponent);
  }

  @Override
  public Image createBlueComponent() {
    return transformImage(Pixel::createBlueComponent);
  }

  @Override
  public Image adjustImageBrightness(int factor) {
    return transformImage(pixel -> pixel.adjustBrightness(factor));
  }

  @Override
  public Image getLuma() {
    return transformImage(Pixel::getLuma);
  }

  @Override
  public Image getSepia() {
    return transformImage(Pixel::getSepia);
  }

  @Override
  public Image getIntensity() {
    return transformImage(Pixel::getIntensity);
  }

  @Override
  public Image getValue() {
    return transformImage(Pixel::getValue);
  }

  @Override
  public Image horizontalFlip() {
    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = this.getPixel(width - x - 1, y);
      }
    }
    return ImageFactory.createImage(newPixelArray);
  }

  @Override
  public Image verticalFlip() {
    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = this.getPixel(x, height - y - 1);
      }
    }
    return ImageFactory.createImage(newPixelArray);
  }

  private Image transformImage(Function<Pixel, Pixel> transformation) {
    int height = this.getHeight();
    int width = this.getWidth();
    Pixel[][] newPixelArray = new Pixel[width][height];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        newPixelArray[x][y] = transformation.apply(this.getPixel(x, y));
      }
    }
    return new RenderedImage(newPixelArray);
  }
}