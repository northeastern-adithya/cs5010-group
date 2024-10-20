package factories;

import model.color.Pixel;
import model.visual.Image;
import model.visual.RenderedImage;

public class ImageFactory {

  private ImageFactory() {
  }

  public static Image createImage(Pixel[][] pixels) {
    return new RenderedImage(pixels);
  }
}
