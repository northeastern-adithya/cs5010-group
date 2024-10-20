package services;

import exception.ImageProcessorException;

public interface ImageProcessingService {

  void loadImage(String imagePath, String imageName) throws ImageProcessorException;

  void saveImage(String imagePath, String imageName) throws ImageProcessorException;

  void createRedComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void createGreenComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void createBlueComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void createValueComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void createLumaComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void createIntensityComponent(String imageName, String destinationImageName) throws ImageProcessorException;

  void horizontalFlip(String imageName, String destinationImageName) throws ImageProcessorException;

  void verticalFlip(String imageName, String destinationImageName) throws ImageProcessorException;

  void brighten(String imageName, String destinationImageName, int factor) throws ImageProcessorException;

  void rgbSplit(String imageName, String destinationImageNameRed, String destinationImageNameGreen, String destinationImageNameBlue) throws ImageProcessorException;

  void rgbCombine(String imageName, String redImageName, String greenImageName, String blueImageName) throws ImageProcessorException;


  void blurImage(String imageName, String destinationImageName) throws ImageProcessorException;

  void sharpenImage(String imageName, String destinationImageName) throws ImageProcessorException;

  void sepiaImage(String imageName, String destinationImageName) throws ImageProcessorException;

}
