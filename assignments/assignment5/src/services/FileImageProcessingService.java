package services;

import java.util.Objects;

import exception.ImageProcessorException;
import factories.Factory;
import model.enumeration.CompressionType;
import model.enumeration.FilterOption;
import model.enumeration.ImageType;
import model.memory.ImageMemory;
import model.request.ImageProcessingRequest;
import model.visual.Image;
import utility.ExtractUtility;
import utility.FilterUtils;
import utility.IOUtils;
import utility.StringUtils;

/**
 * FileImageProcessingService class that implements the
 * ImageProcessingService interface
 * and provides the implementation for the methods to process images.
 * It uses the ImageMemory object to store and retrieve images.
 */
public class FileImageProcessingService implements ImageProcessingService {

  /**
   * ImageMemory object to store and retrieve images.
   */
  private final ImageMemory memory;

  /**
   * Constructor to initialize the FileImageProcessingService.
   *
   * @param memory ImageMemory object
   * @throws NullPointerException if memory is null
   */
  public FileImageProcessingService(ImageMemory memory) {
    Objects.requireNonNull(memory, "Memory cannot be null");
    this.memory = memory;
  }

  @Override
  public void loadImage(ImageProcessingRequest request) throws ImageProcessorException {
    validateStringParams(request.getImagePath(), request.getImageName());
    ImageType imageType =
            ImageType.getImageTypeFromPath(request.getImagePath());
    Image imageToLoad = IOUtils.read(request.getImagePath(), imageType);
    memory.addImage(request.getImageName(), imageToLoad);
  }

  @Override
  public void saveImage(ImageProcessingRequest request) throws ImageProcessorException {
    validateStringParams(request.getImagePath(), request.getImageName());
    Image imageToSave = memory.getImage(request.getImageName());
    ImageType imageType =
            ImageType.getImageTypeFromPath(request.getImagePath());
    IOUtils.write(imageToSave, request.getImagePath(), imageType);
  }

  @Override
  public void createRedComponent(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    Image finalImage = image.createRedComponent();
    if (request.getPercentage().isPresent()) {
      finalImage = Factory.combineImage(image, finalImage,
              request.getPercentage().get());
    }
    memory.addImage(request.getDestinationImageName(), finalImage);
  }

  @Override
  public void createGreenComponent(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    Image finalImage = image.createGreenComponent();
    if (request.getPercentage().isPresent()) {
      finalImage = Factory.combineImage(image, finalImage,
              request.getPercentage().get());
    }
    memory.addImage(request.getDestinationImageName(), finalImage);
  }

  @Override
  public void createBlueComponent(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    Image finalImage = image.createBlueComponent();
    if (request.getPercentage().isPresent()) {
      finalImage = Factory.combineImage(image, finalImage,
              request.getPercentage().get());
    }
    memory.addImage(request.getDestinationImageName(), finalImage);
  }

  @Override
  public void createValueComponent(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getDestinationImageName(), image.getValue());
  }

  @Override
  public void createLumaComponent(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getDestinationImageName(), image.getLuma());
  }

  @Override
  public void createIntensityComponent(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getDestinationImageName(), image.getIntensity());
  }

  @Override
  public void horizontalFlip(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getDestinationImageName(), image.horizontalFlip());
  }


  @Override
  public void verticalFlip(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getDestinationImageName(), image.verticalFlip());
  }

  @Override
  public void brighten(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getDestinationImageName(),
            image.adjustImageBrightness(request.getFactor().orElse(0)));
  }

  @Override
  public void rgbSplit(ImageProcessingRequest request) throws ImageProcessorException {
    validateStringParams(
            request.getImageName(),
            request.getRedImageName(),
            request.getGreenImageName(),
            request.getBlueImageName()
    );
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getRedImageName(), image.createRedComponent());
    memory.addImage(request.getGreenImageName(), image.createGreenComponent());
    memory.addImage(request.getBlueImageName(), image.createBlueComponent());
  }

  @Override
  public void rgbCombine(ImageProcessingRequest request) throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getRedImageName(),
            request.getGreenImageName(),
            request.getBlueImageName());
    Image redImage = memory.getImage(request.getRedImageName());
    Image greenImage = memory.getImage(request.getGreenImageName());
    Image blueImage = memory.getImage(request.getBlueImageName());
    Image combinedImage = Factory.combineRGBComponents(redImage, greenImage,
            blueImage);
    memory.addImage(request.getImageName(), combinedImage);
  }

  @Override
  public void blurImage(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    Image filteredImage = FilterUtils.applyFilter(image,
            FilterOption.GAUSSIAN_BLUR);
    Image imageAfterCombining = filteredImage;
    if (request.getPercentage().isPresent()) {
      imageAfterCombining = Factory.combineImage(image, filteredImage,
              request.getPercentage().get());
    }
    memory.addImage(request.getDestinationImageName(), imageAfterCombining);
  }

  @Override
  public void sharpenImage(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    Image filteredImage = FilterUtils.applyFilter(image,
            FilterOption.SHARPEN);
    Image imageAfterCombining = filteredImage;
    if (request.getPercentage().isPresent()) {
      imageAfterCombining = Factory.combineImage(image, filteredImage,
              request.getPercentage().get());
    }
    memory.addImage(request.getDestinationImageName(), imageAfterCombining);
  }

  @Override
  public void sepiaImage(ImageProcessingRequest request)
          throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    Image imageAfterCombining = image.getSepia();
    if (request.getPercentage().isPresent()) {
      imageAfterCombining = Factory.combineImage(image, imageAfterCombining,
              request.getPercentage().get());
    }
    memory.addImage(request.getDestinationImageName(), imageAfterCombining);
  }

  @Override
  public void compressImage(ImageProcessingRequest request) throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getDestinationImageName(),
            Factory.createCompression(CompressionType.HAAR)
                    .compress(image, request.getPercentage().orElse(0)));
  }

  @Override
  public void histogram(ImageProcessingRequest request) throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    memory.addImage(request.getDestinationImageName(),
            ExtractUtility.createHistogram(image));
  }

  @Override
  public void colorCorrect(ImageProcessingRequest request) throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    Image imageAfterCombining = image.colorCorrect();
    if (request.getPercentage().isPresent()) {
      imageAfterCombining = Factory.combineImage(image, imageAfterCombining,
              request.getPercentage().get());
    }
    memory.addImage(request.getDestinationImageName(), imageAfterCombining);
  }

  @Override
  public void levelsAdjust(ImageProcessingRequest request) throws ImageProcessorException {
    validateStringParams(request.getImageName(),
            request.getDestinationImageName());
    Image image = memory.getImage(request.getImageName());
    ImageProcessingRequest.Levels levels = request.getLevels().orElseThrow(
            () -> new ImageProcessorException("Levels not provided")
    );
    int black = levels.getBlack();
    int white = levels.getWhite();
    int mid = levels.getMid();
    Image imageAfterCombining = image.levelsAdjust(black, mid, white);
    if (request.getPercentage().isPresent()) {
      imageAfterCombining = Factory.combineImage(image, imageAfterCombining,
              request.getPercentage().get());
    }
    memory.addImage(request.getDestinationImageName(), imageAfterCombining);
  }

  /**
   * Validates the input string parameters.
   *
   * @param strings the input string parameters
   * @throws ImageProcessorException if the input string parameters are null
   *                                 or empty
   */
  private void validateStringParams(String... strings)
          throws ImageProcessorException {
    if (StringUtils.isNullOrEmpty(strings)) {
      throw new ImageProcessorException("Received input as null or empty");
    }
  }
}
