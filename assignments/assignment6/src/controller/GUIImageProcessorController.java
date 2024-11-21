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
  private final ImageMemory<String> imageToDisplay;

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
          ImageProcessingService imageProcessingService,
          ImageMemory<String> imageToDisplay) {

    Objects.requireNonNull(userInput, "UserInput cannot be null");
    Objects.requireNonNull(userOutput, "UserOutput cannot be null");
    Objects.requireNonNull(imageProcessingService, "ImageProcessingService "
            + "cannot be null");
    Objects.requireNonNull(imageToDisplay, "ImageToDisplay cannot be null");
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
                    UserCommand.VERTICAL_FLIP,
                    UserCommand.HORIZONTAL_FLIP,
                    UserCommand.LUMA_COMPONENT,
                    UserCommand.COLOR_CORRECT,
                    UserCommand.LEVELS_ADJUST,
                    UserCommand.RESET
            )
    );
    this.imageToDisplay = imageToDisplay;
    this.userOutput.addFeatures(this);
  }

  /**
   * Loads an image from a file system path into the application.
   * Validates that no unsaved image is currently loaded before proceeding.
   * Updates the display with the newly loaded image.
   */
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

  /**
   * Saves the currently displayed image to a specified file system location.
   * Clears the current image from memory after saving.
   */
  @Override
  public void saveImage() {
    executeImageOperation(
            () -> {
              validateImageLoaded();
              String destinationImagePath =
                      userInput.interactiveImageSavePathInput();
              imageProcessingService.saveImage(ImageProcessingRequest.builder()
                      .imagePath(destinationImagePath)
                      .imageName(getImageToDisplay())
                      .build());
              this.reset();
            }
    );
  }

  /**
   * Applies a sepia tone filter to the current image.
   * Shows a split view preview of the effect before applying it.
   */
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

  /**
   * Clears the current image and associated data from memory.
   * Resets the display to its initial state.
   */
  @Override
  public void reset() {
    executeImageOperation(
            () -> {
              imageProcessingService.clearMemory();
              clearImage();
            }
    );
  }

  /**
   * Extracts and displays the blue color component of the current image.
   * Shows a split view preview of the effect before applying it.
   */
  @Override
  public void blueComponent() {
    executeImageOperation(
            () -> {
              createComponent(UserCommand.BLUE_COMPONENT);
            }
    );
  }

  /**
   * Applies a Gaussian blur filter to the current image.
   * Shows a split view preview of the effect before applying it.
   */
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

  /**
   * Applies a sharpening filter to the current image.
   * Shows a split view preview of the effect before applying it.
   */
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

  /**
   * Compresses the current image by a specified percentage.
   * Presents a slider interface for selecting the compression level.
   */
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
  public void closeWindow() {
    executeImageOperation(
            () -> {
              if (isImageLoaded()) {
                displayMessage("Save the current image before closing the "
                        + "window", DisplayMessageType.ERROR);
                userOutput.doNotCloseWindow();
              } else {
                userOutput.closeWindow();
              }
            }
    );

  }

  /**
   * Flips the current image vertically around its horizontal axis.
   */
  @Override
  public void verticalFlip() {
    executeImageOperation(
            () -> {
            validateImageLoaded();
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

  /**
   * Flips the current image horizontally around its vertical axis.
   */
  @Override
  public void horizontalFlip() {
    executeImageOperation(
            () -> {
            validateImageLoaded();
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

  /**
   * Extracts and displays the luma component of the current image.
   */
  @Override
  public void getLuma() {
    executeImageOperation(
            () -> {
            validateImageLoaded();
              showSplitView(
                      percentage -> executeSplitViewCommand(percentage,
                              UserCommand.LUMA_COMPONENT)
              );
            }
    );
  }

  /**
   * Color corrects the current image.
   * Shows a split view preview of the effect before applying it.
   */
  @Override
  public void colorCorrect() {
    executeImageOperation(
            () -> {
              showSplitView(
                      percentage -> executeSplitViewCommand(percentage,
                              UserCommand.COLOR_CORRECT)
              );
            }
    );
  }

  /**
   * Adjusts the levels of the current image.
   * Shows a split view preview of the effect before applying it.
   */
  @Override
  public void levelsAdjust() {
    executeImageOperation(
            () -> {
              validateImageLoaded();
              ImageProcessingRequest.Levels levels =
                      userInput.interactiveThreeLevelInput();
              int blackLevel = levels.getBlack();
              int midLevel = levels.getMid();
              int whiteLevel = levels.getWhite();
              showSplitView(
                      percentage -> handleLevelsAdjustment(percentage,
                              blackLevel, midLevel, whiteLevel)
              );
            }
    );
  }

  /**
   * Handles the levels adjustment operation.
   *
   * @param percentage the percentage of the image to be displayed
   * @param blackLevel the black level
   * @param midLevel   the middle level
   * @param whiteLevel the white level
   * @return the name of the image after applying the levels adjustment
   * @throws ImageProcessorException if there is an error applying the levels
   *                                 adjustment
   */
  private String handleLevelsAdjustment(int percentage, int blackLevel,
                                        int midLevel, int whiteLevel) throws
          ImageProcessorException {
    String levelsImageName = createDestinationImageName(getImageToDisplay(),
            UserCommand.LEVELS_ADJUST);

    ImageProcessingRequest request = ImageProcessingRequest.builder()
            .imageName(getImageToDisplay())
            .destinationImageName(levelsImageName)
            .levels(blackLevel, midLevel, whiteLevel)
            .percentage(percentage)
            .build();
    imageProcessingService.levelsAdjust(request);
    return levelsImageName;
  }

  /**
   * Runs the split view command.
   *
   * @param percentage the percentage of the image to be displayed
   * @param command    the command to be applied
   * @return the name of the image after applying the split view command
   * @throws ImageProcessorException if there is an error applying the command
   */
  private String executeSplitViewCommand(Integer percentage,
                                         UserCommand command) throws
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
      case LUMA_COMPONENT:
        imageProcessingService.createLumaComponent(request);
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
      case COLOR_CORRECT:
        imageProcessingService.colorCorrect(request);
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


  /**
   * Creates the red component of the image.
   * Shows a split view preview of the effect before applying it.
   */
  @Override
  public void redComponent() {
    executeImageOperation(
            () -> {
              createComponent(UserCommand.RED_COMPONENT);
            }
    );
  }

  private void createComponent(UserCommand command) throws
          ImageProcessorException {
    String componentImageName = createDestinationImageName(
            getImageToDisplay(), command);
    ImageProcessingRequest request = ImageProcessingRequest.builder()
            .imageName(getImageToDisplay())
            .destinationImageName(componentImageName)
            .build();
    switch (command) {
      case RED_COMPONENT:
        imageProcessingService.createRedComponent(request);
        break;
      case BLUE_COMPONENT:
        imageProcessingService.createBlueComponent(request);
        break;
      case GREEN_COMPONENT:
        imageProcessingService.createGreenComponent(request);
        break;
      default:
        throw new ImageProcessorException(
                String.format("Invalid command for component: %s", command));
    }
    updateImageToDisplay(componentImageName);
  }

  /**
   * Creates the green component of the image.
   * Shows a split view preview of the effect before applying it.
   */
  @Override
  public void greenComponent() {
    executeImageOperation(
            () -> {
              createComponent(UserCommand.GREEN_COMPONENT);
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
