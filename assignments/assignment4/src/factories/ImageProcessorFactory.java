package factories;

import services.ImageProcessor;
import services.SimpleImageProcessor;

public class ImageProcessorFactory {

  public static ImageProcessor createImageProcessor() {
    return new SimpleImageProcessor();
  }
}
