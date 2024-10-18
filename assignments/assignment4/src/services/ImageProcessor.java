package services;

import exception.ImageProcessorException;

public interface ImageProcessor {

  void loadImage(String imagePath, String imageName) throws ImageProcessorException;

  void saveImage(String imagePath,String imageName) throws ImageProcessorException;

  void redComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void greenComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void blueComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void valueComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void lumaComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void intensityComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void horizontalFlip(String imageName, String destinationImageName) throws ImageProcessorException;

  void verticalFlip(String imageName, String destinationImageName) throws ImageProcessorException;

  void brighten(String imageName, String destinationImageName, int factor) throws ImageProcessorException;

  void rgbSplit(String imageName, String destinationImageNameRed,String destinationImageNameGreen,String destinationImageNameBlue) throws ImageProcessorException;

  void rgbCombine(String imageName,String greenImageName,String blueImageName) throws ImageProcessorException;


  void blurImage(String imageName, String destinationImageName) throws ImageProcessorException;

  void sharpenImage(String imageName, String destinationImageName) throws ImageProcessorException;

  void sepiaImage(String imageName, String destinationImageName) throws ImageProcessorException;

}
