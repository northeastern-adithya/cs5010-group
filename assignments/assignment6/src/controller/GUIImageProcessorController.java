package controller;

import java.io.File;
import java.util.List;
import java.util.Objects;


import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.filechooser.FileNameExtensionFilter;

import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import model.enumeration.ImageType;
import model.enumeration.UserCommand;
import model.memory.ImageMemory;
import model.memory.StringMemory;
import model.request.ImageProcessingRequest;
import controller.services.ImageProcessingService;
import model.visual.Image;
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

  private final UserOutput userOutput;
  private final ImageProcessingService imageProcessingService;
  private final ImageMemory<String> imageToDisplay = new StringMemory();

  /**
   * Constructs a GUIImageProcessorController object with the given
   * UserOutput and ImageProcessingService.
   *
   * @param userOutput             the output to be displayed to user.
   * @param imageProcessingService the image processing service.
   */
  public GUIImageProcessorController(UserOutput userOutput,
                                     ImageProcessingService imageProcessingService) {

    Objects.requireNonNull(userOutput, "UserOutput cannot be null");
    Objects.requireNonNull(imageProcessingService, "ImageProcessingService "
            + "cannot be null");
    this.imageProcessingService = imageProcessingService;
    this.userOutput = userOutput;
    this.userOutput.displayCommands(
            List.of(
                    UserCommand.LOAD,
                    UserCommand.SAVE,
                    UserCommand.SEPIA,
                    UserCommand.CLEAR
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
            JFileChooser fileChooser = createFileChooseWithFilter();
            int returnState = fileChooser.showOpenDialog(null);
            if (returnState == JFileChooser.APPROVE_OPTION) {
              File f = fileChooser.getSelectedFile();
              String imageName =
                        IOUtils.getImageNameFromPath(f.getAbsolutePath());
              imageProcessingService.loadImage(ImageProcessingRequest.builder()
                        .imagePath(f.getAbsolutePath())
                        .imageName(imageName)
                        .build());
              updateImageToDisplay(imageName);
            }
        }
    );
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
            JFileChooser fchooser = createFileChooseWithFilter();
            int returnState = fchooser.showSaveDialog(null);
            if (returnState == JFileChooser.APPROVE_OPTION) {
              File file = fchooser.getSelectedFile();
              imageProcessingService.saveImage(ImageProcessingRequest.builder()
                        .imagePath(file.getAbsolutePath())
                        .imageName(getImageToDisplay())
                        .build());
              this.clearMemory();
            }
        }
    );
  }

  @Override
  public void applySepia() {
    executeImageOperation(
        () -> {
            showSplitView(this::handleSepiaCommand);
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

  /**
   * Handles the sepia command.
   *
   * @param percentage the split percentage of the image to be displayed.
   * @return the name of the image after applying the sepia command.
   * @throws ImageProcessorException if there is an error applying the sepia
   *                                 command
   */
  private String handleSepiaCommand(Integer percentage) throws
          ImageProcessorException {
    String sepiaImageName = createDestinationImageName(getImageToDisplay(),
            UserCommand.SEPIA);
    ImageProcessingRequest request = ImageProcessingRequest.builder()
            .imageName(getImageToDisplay())
            .destinationImageName(sepiaImageName)
            .percentage(percentage)
            .build();
    imageProcessingService.sepiaImage(request);
    return sepiaImageName;
  }


  /**
   * Creates a file chooser with the image filter.
   * Supported image formats are JPEG, PNG, PPM, and JPG.
   *
   * @return the file chooser with the image filter
   */
  private JFileChooser createFileChooseWithFilter() {
    JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images",
            ImageType.JPEG.getExtension(),
            ImageType.PNG.getExtension(),
            ImageType.PPM.getExtension(),
            ImageType.JPG.getExtension());
    fileChooser.setFileFilter(filter);
    return fileChooser;
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
    String splitImageName = splitView.run(100);
    updateImageViewOnly(splitImageName);
    // Slider to adjust the split view
    JSlider sepiaSlider = new JSlider(0, 100, 100);
    sepiaSlider.setMajorTickSpacing(10);
    sepiaSlider.setMinorTickSpacing(1);
    sepiaSlider.setPaintTicks(true);
    sepiaSlider.setPaintLabels(true);
    // Add listener to the slider to update the image view
    sepiaSlider.addChangeListener(e -> {
      if (!sepiaSlider.getValueIsAdjusting()) {
        try {
          String imageName =
                  splitView.run(sepiaSlider.getValue());
          updateImageViewOnly(imageName);
        } catch (ImageProcessorException exception) {
          displayMessage(exception.getMessage(), DisplayMessageType.ERROR);
        }
      }
    });

    JPanel panel = new JPanel();
    panel.add(new JLabel());
    panel.add(sepiaSlider);
    int result = JOptionPane.showConfirmDialog(null, panel, "Split View",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      String imageName = splitView.run(100);
      updateImageToDisplay(imageName);
    } else if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
      updateImageToDisplay(getImageToDisplay());
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
