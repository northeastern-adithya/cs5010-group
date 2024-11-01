package compressors;

import exception.ImageProcessorException;
import model.visual.Image;

public interface Compression {

  Image compress(Image image, int percentage) throws ImageProcessorException;
}
