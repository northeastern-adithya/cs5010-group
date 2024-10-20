package factories;

import services.ImageProcessingService;
import services.FileImageProcessingService;

public class ImageProcessingServiceFactory {
  public static ImageProcessingService createImageProcessor() {
    return new FileImageProcessingService();
  }
}
