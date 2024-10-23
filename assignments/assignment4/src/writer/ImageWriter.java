package writer;

import exception.ImageProcessorException;
import model.visual.Image;

public interface ImageWriter {
  void write(Image image, String path) throws ImageProcessorException;
}
