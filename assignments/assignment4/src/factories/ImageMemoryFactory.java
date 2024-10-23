package factories;

import model.memory.HashMapMemory;
import model.memory.ImageMemory;

/**
 * Factory class to create ImageMemory objects.
 */
public class ImageMemoryFactory {


  private ImageMemoryFactory() {
    // Private constructor to prevent instantiation.
  }


  /**
   * Creates an ImageMemory object to store the
   * images in memory.
   * Images are stored in memory based on their implementation.
   *
   * @return the ImageMemory object
   */
  public static ImageMemory getImageMemory() {
    return new HashMapMemory();
  }
}
