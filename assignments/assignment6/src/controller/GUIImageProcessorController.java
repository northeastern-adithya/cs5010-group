package controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import model.enumeration.UserCommand;
import model.memory.ImageMemory;
import model.memory.StringMemory;
import model.request.ImageProcessingRequest;
import controller.services.ImageProcessingService;
import model.visual.Image;
import view.input.UserInput;
import view.output.DisplayMessageType;
import view.output.UserOutput;
import utility.IOUtils;
import utility.StringUtils;

/**
 * Represents the controller for the image processor application.
 * This controller is responsible for handling all the user commands
 * coming from GUI view and processing them.
 */
public class GUIImageProcessorController implements ImageProcessorController,
        Features {

  private final UserInput userInput;
  private final UserOutput userOutput;
  private final ImageProcessingService imageProcessingService;
  private final ImageMemory<String> imageToDisplay = new StringMemory();

  /**
   * Constructs a GUIImageProcessorController object with the given
   * UserOutput and ImageProcessingService.
   *
   * @param userInput              the input from the user.
   * @param userOutput             the output to be displayed to user.
   * @param imageProcessingService the image processing service.
   */
  public GUIImageProcessorController(

          UserInput userInput, UserOutput userOutput,
          ImageProcessingService imageProcessingService) {

    Objects.requireNonNull(userInput, "UserInput cannot be null");
    Objects.requireNonNull(userOutput, "UserOutput cannot be null");
    Objects.requireNonNull(imageProcessingService, "ImageProcessingService "
            + "cannot be null");
    this.imageProcessingService = imageProcessingService;

    this.userInput = userInput;
    this.userOutput = userOutput;
    this.userOutput.displayCommands(
            List.of(
                    UserCommand.LOAD,
                    UserCommand.SAVE,
                    UserCommand.SEPIA,
                    UserCommand.RED_COMPONENT,
                    UserCommand.GREEN_COMPONENT,
                    UserCommand.BLUE_COMPONENT,
                    UserCommand.BLUR,
                    UserCommand.SHARPEN,
                    UserCommand.COMPRESS,
                    UserCommand.CLEAR
                    UserCommand.CLEAR,
                    UserCommand.VERTICAL_FLIP,
                    UserCommand.HORIZONTAL_FLIP,
                    UserCommand.LUMA_COMPONENT,
                    UserCommand.COLOR_CORRECT
            )
    );
    this.userOutput.addFeatures(this);
  }

  @Override
  public void loadImage() {
    executeImageOperation(
            () -> {
              if (isImageLoaded()) {
                throw new ImageProcessorException("Save the current image "
                        + "before loading a new one");
            }
            String imagePath = userInput.interactiveImageLoadPathInput();
            String imageName = IOUtils.getImageNameFromPath(imagePath);
            imageProcessingService.loadImage(ImageProcessingRequest.builder()
                      .imagePath(imagePath)
                      .imageName(imageName)
                      .build());
            updateImageToDisplay(imageName);
        }
    );
  }

  /**
   * Validates if an image is loaded in memory.
   *
   * @throws ImageProcessorException if no image is loaded
   */
  private void validateImageLoaded() throws
          ImageProcessorException {
    if (!isImageLoaded()) {
      throw new ImageProcessorException.NotFoundException("No image loaded");
    }
  }

  /**
   * Checks if an image is loaded.
   *
   * @return true if an image is loaded, false otherwise
   */
  private boolean isImageLoaded() throws
          ImageProcessorException {
    String imageName;
    try {
      imageName = getImageToDisplay();
    } catch (ImageProcessorException.NotFoundException e) {
      return false;
    }
    return StringUtils.isNotNullOrEmpty(imageName);
  }

  @Override
  public void saveImage() {
    executeImageOperation(
        () -> {
            validateImageLoaded();
            String destinationImagePath = userInput.interactiveImageSavePathInput();
            imageProcessingService.saveImage(ImageProcessingRequest.builder()
                      .imagePath(destinationImagePath)
                      .imageName(getImageToDisplay())
                      .build());
            this.clearMemory();
        }
    );
  }

  @Override
  public void applySepia() {
    executeImageOperation(
            () -> {
              showSplitView(
                      percentage -> executeSplitViewCommand(percentage,
                              UserCommand.SEPIA)
              );
            }
    );
  }

  @Override
  public void clearMemory() {
    executeImageOperation(
            () -> {
              imageProcessingService.clearMemory();
              clearImage();
            }
    );
  }

  @Override
  public void blueComponent() {
    executeImageOperation(
            () -> {
              showSplitView(
                      percentage -> executeSplitViewCommand(percentage,
                              UserCommand.BLUE_COMPONENT)
              );
            }
    );
  }

  @Override
  public void blurImage() {
    executeImageOperation(
            () -> {
              showSplitView(
                      percentage -> executeSplitViewCommand(percentage,
                              UserCommand.BLUR)
              );
            }
    );
  }

  @Override
  public void sharpenImage() {
    executeImageOperation(
            () -> {
              showSplitView(
                      percentage -> executeSplitViewCommand(percentage,
                              UserCommand.SHARPEN)
              );
            }
    );
  }

  @Override
  public void compressImage() {
    executeImageOperation(
            () -> {
              Optional<Integer> percentage = userInput.getSliderInput();
              if (percentage.isPresent()) {
                compressImage(percentage.get());
              }
            }
    );

  }

  @Override
  public void verticalFlip() {
    executeImageOperation(
        () -> {
            String verticalFlipImageName = createDestinationImageName(
                    getImageToDisplay(), UserCommand.VERTICAL_FLIP);
            ImageProcessingRequest request = ImageProcessingRequest.builder()
                    .imageName(getImageToDisplay())
                    .destinationImageName(verticalFlipImageName)
                    .build();
            imageProcessingService.verticalFlip(request);
            updateImageToDisplay(verticalFlipImageName);
        }
    );
  }

  @Override
  public void horizontalFlip() {
    executeImageOperation(
        () -> {
            String horizontalFlipImageName = createDestinationImageName(
                    getImageToDisplay(), UserCommand.HORIZONTAL_FLIP);
            ImageProcessingRequest request = ImageProcessingRequest.builder()
                    .imageName(getImageToDisplay())
                    .destinationImageName(horizontalFlipImageName)
                    .build();
            imageProcessingService.horizontalFlip(request);
            updateImageToDisplay(horizontalFlipImageName);
        }
    );
  }

  @Override
  public void getLuma() {
    executeImageOperation(
        () -> {
            String lumaImageName = createDestinationImageName(
                    getImageToDisplay(), UserCommand.LUMA_COMPONENT);
            ImageProcessingRequest request = ImageProcessingRequest.builder()
                    .imageName(getImageToDisplay())
                    .destinationImageName(lumaImageName)
                    .build();
            imageProcessingService.createLumaComponent(request);
            updateImageToDisplay(lumaImageName);
        }
    );
  }

  @Override
  public void colorCorrect() {
    executeImageOperation(
        () -> {
          showSplitView(this::handleColorCorrectCommand);
        }
    );
  }

  private String handleColorCorrectCommand(Integer percentage) throws
          ImageProcessorException {
    String colorCorrectImageName = createDestinationImageName(
            getImageToDisplay(), UserCommand.COLOR_CORRECT);
    ImageProcessingRequest request = ImageProcessingRequest.builder()
            .imageName(getImageToDisplay())
            .destinationImageName(colorCorrectImageName)
            .percentage(percentage)
            .build();
    imageProcessingService.colorCorrect(request);
    return colorCorrectImageName;
  }

  /**
   * Runs the split view command.
   *
   * @param percentage the percentage of the image to be displayed
   * @param command    the command to be applied
   * @return the name of the image after applying the split view command
   * @throws ImageProcessorException if there is an error applying the command
   */
  private String executeSplitViewCommand(Integer percentage, UserCommand command) throws
          ImageProcessorException {
    validateImageLoaded();
    String imageName = createDestinationImageName(getImageToDisplay(),
            command);
    ImageProcessingRequest request = ImageProcessingRequest.builder()
            .imageName(getImageToDisplay())
            .destinationImageName(imageName)
            .percentage(percentage)
            .build();
    switch (command) {
      case RED_COMPONENT:
        imageProcessingService.createRedComponent(request);
        break;
      case GREEN_COMPONENT:
        imageProcessingService.createGreenComponent(request);
        break;
      case BLUE_COMPONENT:
        imageProcessingService.createBlueComponent(request);
        break;
      case SEPIA:
        imageProcessingService.sepiaImage(request);
        break;
      case BLUR:
        imageProcessingService.blurImage(request);
        break;
      case SHARPEN:
        imageProcessingService.sharpenImage(request);
        break;
      default:
        throw new ImageProcessorException(
                String.format("Invalid command for split view: %s", command));
    }
    return imageName;
  }

  /**
   * Compresses the image by the given percentage.
   *
   * @param percentage the percentage by which the image is to be compressed
   * @throws ImageProcessorException if there is an error compressing the image
   */
  private void compressImage(Integer percentage) throws
          ImageProcessorException {
    validateImageLoaded();
    String compressImageName = createDestinationImageName(getImageToDisplay(),
            UserCommand.COMPRESS);
    ImageProcessingRequest request = ImageProcessingRequest.builder()
            .imageName(getImageToDisplay())
            .destinationImageName(compressImageName)
            .percentage(percentage)
            .build();
    imageProcessingService.compressImage(request);
    updateImageToDisplay(compressImageName);
  }


  @Override
  public void redComponent() {
    executeImageOperation(
            () -> {
              showSplitView(
                      percentage -> executeSplitViewCommand(percentage,
                              UserCommand.RED_COMPONENT)
              );
            }
    );
  }

  @Override
  public void greenComponent() {
    executeImageOperation(
            () -> {
              showSplitView(
                      percentage -> executeSplitViewCommand(percentage,
                              UserCommand.GREEN_COMPONENT)
              );
            }
    );
  }


  /**
   * Updates the image view only.
   * This method is used to update the image view without updating the
   * histogram or the local image name.
   * If imageName is null or empty, it does not do anything.
   *
   * @param imageName the name of the image to be displayed
   * @throws ImageProcessorException if there is an error displaying the image
   */
  private void updateImageViewOnly(String imageName) throws
          ImageProcessorException {
    if (StringUtils.isNullOrEmpty(imageName)) {
      return;
    }
    Image image = imageProcessingService.getImage(imageName);
    userOutput.displayImage(image, null);
  }

  /**
   * Updates the image to display.
   * This method is used to update the local memory and the image view
   * with histogram.
   * If imageName is null or empty, it does not do anything.
   *
   * @param imageName the name of the image to be displayed
   * @throws ImageProcessorException if there is an error displaying the image
   */
  private void updateImageToDisplay(String imageName) throws
          ImageProcessorException {
    if (StringUtils.isNullOrEmpty(imageName)) {
      return;
    }
    imageToDisplay.addImage(imageName, null);
    Image image = imageProcessingService.getImage(imageName);
    userOutput.displayImage(image, image.histogram());
  }


  /**
   * Clears the image from the view and local memory.
   */
  private void clearImage() {
    imageToDisplay.clearMemory();
    userOutput.clearImage();
  }

  /**
   * Shows the split view of the image.
   *
   * @param splitView the split view operation to be applied
   * @throws ImageProcessorException if there is an error displaying the split
   *                                 view
   */
  private void showSplitView(SplitView splitView) throws
          ImageProcessorException {
    validateImageLoaded();
    String splitImageName = splitView.run(100);
    updateImageViewOnly(splitImageName);
    boolean confirmSplitView = userInput.confirmSplitView(
            percentage -> {
              try {
                String imageName = splitView.run(percentage);
                updateImageViewOnly(imageName);
              } catch (ImageProcessorException e) {
                displayMessage(e.getMessage(), DisplayMessageType.ERROR);
              }
            }
    );
    if (confirmSplitView) {
      String imageName = splitView.run(100);
      updateImageToDisplay(imageName);
    } else {
      updateImageToDisplay(this.getImageToDisplay());
    }
  }

  /**
   * Creates the destination image name.
   *
   * @param imageName the name of the image
   * @param command   the command to be applied
   * @return the destination image name
   */
  private String createDestinationImageName(String imageName,
                                            UserCommand command) {
    return command.getCommand() + "_" + imageName;
  }


  @Override
  public void processCommands() throws
          ImageProcessingRunTimeException.QuitException {
    throw new ImageProcessingRunTimeException.QuitException("Not required for"
            + " GUI based application");
  }

  /**
   * Displays the message to the user.
   *
   * @param message     the message to be displayed
   * @param messageType the type of the message
   */
  private void displayMessage(String message, DisplayMessageType messageType) {
    if (StringUtils.isNotNullOrEmpty(message)) {
      this.userOutput.displayMessage(
              message, messageType
      );
    }
  }

  /**
   * Executes the image operation.
   *
   * @param operation the image operation to be applied
   */
  private void executeImageOperation(ImageOperation operation) {
    try {
      operation.run();
    } catch (ImageProcessorException e) {
      displayMessage(e.getMessage(), DisplayMessageType.ERROR);
    }
  }


  /**
   * Gets the image to be displayed.
   * Here image name is empty since for string memory, image name is not
   * required.
   *
   * @return the image to be displayed
   * @throws ImageProcessorException if there is an error getting the image
   */
  private String getImageToDisplay() throws
          ImageProcessorException {
    return imageToDisplay.getImage("");
  }


  /**
   * Represents the image operation to be applied to an image.
   */
  @FunctionalInterface
  private interface ImageOperation {
    /**
     * Runs the image operation.
     *
     * @throws ImageProcessorException if there is an error applying the
     *                                 operation
     */
    void run() throws
            ImageProcessorException;
  }


  /**
   * Represents the split view operation to be applied to an image.
   */
  @FunctionalInterface
  private interface SplitView {
    /**
     * Runs the split view operation.
     *
     * @param percentage the percentage of the image to be displayed
     * @return the name of the image after doing split view operation
     * @throws ImageProcessorException if there is an error while applying
     *                                 the operation
     */
    String run(Integer percentage) throws
            ImageProcessorException;
  }

}
