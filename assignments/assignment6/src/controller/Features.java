// Features.java
package controller;

import exception.ImageProcessorException;

public interface Features {
  void loadImage() throws ImageProcessorException;
  void saveImage() throws ImageProcessorException;
  void applySepia() throws ImageProcessorException;
  void clearMemory() throws ImageProcessorException;
}