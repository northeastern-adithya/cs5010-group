package writer;

import exception.ImageProcessorException;
import model.visual.Image;

/**
 * Interface for writing an image to a file.
 */
public interface ImageWriter {
  /**
   * Writes the image to the file at the given path.
   *
   * @param image the image to write
   * @param path the path to write the image to
   * @throws ImageProcessorException if there is an error writing the image
   */
  void write(Image image, String path) throws ImageProcessorException;
}
