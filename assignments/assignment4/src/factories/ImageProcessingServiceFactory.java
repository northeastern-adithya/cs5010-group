package factories;

import model.memory.ImageMemory;
import services.ImageProcessingService;
import services.FileImageProcessingService;

/**
 * Factory class to create ImageProcessingService objects.
 */
public class ImageProcessingServiceFactory {

  private ImageProcessingServiceFactory() {
    // Private constructor to prevent instantiation.
  }

  /**
   * Creates an ImageProcessingService object to process images.
   *
   * @param memory the memory to store images
   * @return the ImageProcessingService object
   */
  public static ImageProcessingService createImageProcessor(ImageMemory memory) {
    return new FileImageProcessingService(memory);
  }
}
