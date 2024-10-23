package reader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import exception.ImageProcessorException;
import factories.ImageFactory;
import factories.PixelFactory;
import model.PixelType;
import model.pixels.Pixel;
import model.visual.Image;

public abstract class AbstractImageReader implements ImageReader {

  protected AbstractImageReader() {
  }

  @Override
  public Image read(String path) throws ImageProcessorException {
    try {
      File file = new File(path);
      BufferedImage image = ImageIO.read(file);
      int width = image.getWidth();
      int height = image.getHeight();
      Pixel[][] pixelArray = new Pixel[width][height];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          pixelArray[x][y] = PixelFactory.createPixel(image.getRGB(x, y), PixelType.fromBufferedImageType(image.getType()));
        }
      }
      return ImageFactory.createImage(pixelArray);
    } catch (IOException e) {
      throw new ImageProcessorException("Error loading the image file", e);
    }
  }
}
