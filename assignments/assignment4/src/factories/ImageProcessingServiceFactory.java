package factories;

import model.memory.ImageMemory;
import services.ImageProcessingService;
import services.FileImageProcessingService;

public class ImageProcessingServiceFactory {
  public static ImageProcessingService createImageProcessor(ImageMemory memory) {
    return new FileImageProcessingService(memory);
  }
}
