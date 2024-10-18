package services;

import exception.ImageProcessorException;

public class SimpleImageProcessor implements ImageProcessor{


  public SimpleImageProcessor() {
  }

  @Override
  public void loadImage(String imagePath, String imageName) throws ImageProcessorException {

  }

  @Override
  public void saveImage(String imagePath, String imageName) {

  }

  @Override
  public void redComponent(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void greenComponent(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void blueComponent(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void valueComponent(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void lumaComponent(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void intensityComponent(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void horizontalFlip(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void verticalFlip(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void brighten(String imageName, String destinationImageName, int factor) throws ImageProcessorException {

  }

  @Override
  public void rgbSplit(String imageName, String destinationImageNameRed, String destinationImageNameGreen, String destinationImageNameBlue) throws ImageProcessorException {

  }

  @Override
  public void rgbCombine(String imageName, String greenImageName, String blueImageName) throws ImageProcessorException {

  }

  @Override
  public void blurImage(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void sharpenImage(String imageName, String destinationImageName) throws ImageProcessorException {

  }

  @Override
  public void sepiaImage(String imageName, String destinationImageName) throws ImageProcessorException {

  }
}
