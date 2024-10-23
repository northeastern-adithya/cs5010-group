package reader;

import exception.ImageProcessorException;
import model.visual.Image;

/**
 * Interface for reading an image from a file.
 */
public interface ImageReader {
  /**
   * Reads an image from a file.
   * @param path the path to the file.
   * @return the image read from the file.
   * @throws ImageProcessorException if the image cannot be read.
   */
  Image read(String path) throws ImageProcessorException;
}
