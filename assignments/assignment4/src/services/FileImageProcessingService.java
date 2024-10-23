package services;

import java.util.Objects;

import exception.ImageProcessorException;
import factories.FilterFactory;
import factories.ImageFactory;
import factories.ImageReaderFactory;
import factories.ImageWriterFactory;
import filters.FilterOptions;
import model.ImageType;
import model.memory.ImageMemory;
import model.visual.Image;
import utility.ImageUtility;
import utility.StringUtils;

public class FileImageProcessingService implements ImageProcessingService {

  private final ImageMemory memory;

  public FileImageProcessingService(ImageMemory memory) {
    Objects.requireNonNull(memory, "Memory cannot be null");
    this.memory = memory;
  }

  @Override
  public void loadImage(String imagePath, String imageName) throws ImageProcessorException {
    validateStringParams(imagePath, imageName);
    ImageType imageType = ImageUtility.getExtensionFromPath(imagePath);
    Image imageToLoad = ImageReaderFactory.createImageReader(imageType).read(imagePath);
    memory.addImage(imageName, imageToLoad);
  }

  @Override
  public void saveImage(String imagePath, String imageName) throws ImageProcessorException {
    validateStringParams(imagePath, imageName);
    Image imageToSave = memory.getImage(imageName);
    ImageType imageType = ImageUtility.getExtensionFromPath(imagePath);
    ImageWriterFactory.createImageWriter(imageType).write(imageToSave, imagePath);
  }

  @Override
  public void createRedComponent(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.createRedComponent());
  }

  @Override
  public void createGreenComponent(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.createGreenComponent());
  }

  @Override
  public void createBlueComponent(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.createBlueComponent());
  }

  @Override
  public void createValueComponent(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.getValue());
  }

  @Override
  public void createLumaComponent(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.getLuma());
  }

  @Override
  public void createIntensityComponent(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.getIntensity());
  }

  @Override
  public void horizontalFlip(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.horizontalFlip());
  }


  @Override
  public void verticalFlip(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.verticalFlip());
  }

  @Override
  public void brighten(String imageName, String destinationImageName, int factor) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.adjustImageBrightness(factor));
  }

  @Override
  public void rgbSplit(String imageName, String destinationImageNameRed, String destinationImageNameGreen, String destinationImageNameBlue) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageNameRed, destinationImageNameGreen, destinationImageNameBlue);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageNameRed, image.createRedComponent());
    memory.addImage(destinationImageNameGreen, image.createGreenComponent());
    memory.addImage(destinationImageNameBlue, image.createBlueComponent());
  }

  @Override
  public void rgbCombine(String imageName, String redImageName, String greenImageName, String blueImageName) throws ImageProcessorException {
    validateStringParams(imageName, redImageName, greenImageName, blueImageName);
    Image redImage = memory.getImage(redImageName);
    Image greenImage = memory.getImage(greenImageName);
    Image blueImage = memory.getImage(blueImageName);
    Image combinedImage = ImageFactory.combineRGBComponents(redImage, greenImage, blueImage);
    memory.addImage(imageName, combinedImage);
  }

  @Override
  public void blurImage(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    Image filteredImage = FilterFactory.getFilter(FilterOptions.GAUSSIAN_BLUR).applyFilter(image);
    memory.addImage(destinationImageName, filteredImage);
  }

  @Override
  public void sharpenImage(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    Image filteredImage = FilterFactory.getFilter(FilterOptions.SHARPEN).applyFilter(image);
    memory.addImage(destinationImageName, filteredImage);
  }

  @Override
  public void sepiaImage(String imageName, String destinationImageName) throws ImageProcessorException {
    validateStringParams(imageName, destinationImageName);
    Image image = memory.getImage(imageName);
    memory.addImage(destinationImageName, image.getSepia());
  }

  private void validateStringParams(String... strings) throws ImageProcessorException {
    if (StringUtils.isNullOrEmpty(strings)) {
      throw new ImageProcessorException("Received input as null or empty");
    }
  }
}
