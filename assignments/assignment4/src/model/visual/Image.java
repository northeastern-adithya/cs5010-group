package model.visual;

import model.pixels.Pixel;

public interface Image {
  Pixel getPixel(int x, int y);

  Image adjustImageBrightness(int factor);

  int getWidth();

  int getHeight();

  Image createRedComponent();

  Image createGreenComponent();

  Image createBlueComponent();

  Image getLuma();

  Image getSepia();

  Image getIntensity();

  Image getValue();

  Image horizontalFlip();

  Image verticalFlip();
}
