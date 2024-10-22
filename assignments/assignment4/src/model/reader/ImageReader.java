package model.reader;

import exception.ImageProcessorException;
import model.visual.Image;

public interface ImageReader {
  Image read(String path) throws ImageProcessorException;
}
