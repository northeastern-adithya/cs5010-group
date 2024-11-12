package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.services.ImageProcessingService;
import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import model.enumeration.ImageType;
import model.enumeration.UserCommand;
import model.request.ImageProcessingRequest;
import model.visual.Image;
import utility.IOUtils;
import utility.StringUtils;
import view.input.UserInput;
import view.output.UserOutput;

public class GUIImageProcessorController implements ActionListener,
        ImageProcessorController {

  private final UserInput userInput;
  private final UserOutput userOutput;
  private final ImageProcessingService imageProcessingService;

  private String imageToDisplay = null;

  /**
   * Constructor to initialize the SimpleImageProcessorController.
   * Initializes and displays the commands to the user.
   *
   * @param userInput              input coming from user.
   * @param userOutput             output to be displayed to user.
   * @param imageProcessingService ImageProcessingService object.
   * @throws NullPointerException if input, userOutput or imageProcessor is null
   */
  public GUIImageProcessorController(UserInput userInput,
                                     UserOutput userOutput,
                                     ImageProcessingService imageProcessingService) {
    this.userInput = userInput;
    this.userOutput = userOutput;
    this.imageProcessingService = imageProcessingService;
    this.userOutput.displayCommands(
            new ArrayList<>(
                    Arrays.asList(
                            UserCommand.LOAD,
                            UserCommand.SAVE,
                            UserCommand.SEPIA,
                            UserCommand.CLEAR
                    )
            )
    );
    this.userOutput.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Optional<UserCommand> userCommand =
            UserCommand.getCommand(e.getActionCommand());
    if (userCommand.isPresent()) {
      try {
        handleUserCommand(userCommand.get());
      } catch (ImageProcessorException exception) {
        userOutput.displayMessage(exception.getMessage());
      }
    }
  }

  private void handleUserCommand(UserCommand command) throws ImageProcessorException {
    switch (command) {
      case LOAD:
        handleLoadCommand();
        break;
      case SAVE:
        handleSaveCommand();
        break;
      case SEPIA:
        handleSepiaCommand();
        break;
      case CLEAR:
        handleClearCommand();
        break;
    }
  }

  private void handleLoadCommand() throws ImageProcessorException {
    if (Objects.nonNull(imageToDisplay)) {
      throw new ImageProcessorException("Save the current image already " +
              "loaded before loading a new one");
    }
    JFileChooser fileChooser = createFileChooseWithFilter();
    int returnState = fileChooser.showOpenDialog(null);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      String imageName = IOUtils.getImageNameFromPath(f.getAbsolutePath());
      imageProcessingService.loadImage(ImageProcessingRequest.builder()
              .imagePath(f.getAbsolutePath())
              .imageName(imageName)
              .build());
      updateImageToDisplay(imageName);
    }
  }

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

  private void handleSaveCommand() throws ImageProcessorException {
    validateImageToApplyCommand();
    JFileChooser fchooser = createFileChooseWithFilter();
    int returnState = fchooser.showSaveDialog(null);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      File file = fchooser.getSelectedFile();
      imageProcessingService.saveImage(ImageProcessingRequest.builder()
              .imagePath(file.getAbsolutePath())
              .imageName(imageToDisplay)
              .build());
      updateImageToDisplay(null);
    }
  }

  private void updateViewToDisplay(String imageName) throws ImageProcessorException {
    if (StringUtils.isNullOrEmpty(imageName)) {
      userOutput.displayImage(null, null);
      return;
    }

    Image image = imageProcessingService.getImage(imageName);
    userOutput.displayImage(image, image.histogram());
  }

  private void updateImageToDisplay(String imageName) throws ImageProcessorException {
    imageToDisplay = imageName;
    updateViewToDisplay(imageName);
  }

  private void showSplitView(BiConsumer<Integer, Boolean> imageProcessingFunction) throws ImageProcessorException {
    imageProcessingFunction.accept(100, false);
    JSlider sepiaSlider = new JSlider(0, 100, 100);
    sepiaSlider.setMajorTickSpacing(10);
    sepiaSlider.setMinorTickSpacing(1);
    sepiaSlider.setPaintTicks(true);
    sepiaSlider.setPaintLabels(true);
    sepiaSlider.addChangeListener(e -> {
      if (!sepiaSlider.getValueIsAdjusting()) {
        imageProcessingFunction.accept(sepiaSlider.getValue(), false);
      }
    });

    JPanel panel = new JPanel();
    panel.add(new JLabel());
    panel.add(sepiaSlider);
    int result = JOptionPane.showConfirmDialog(null, panel, "Split View",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      imageProcessingFunction.accept(sepiaSlider.getValue(), true);
    } else if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
      updateImageToDisplay(imageToDisplay);
    }
  }


  private void handleSepiaCommand() throws ImageProcessorException {
    validateImageToApplyCommand();
    showSplitView((percentage, updateImageToDisplay) -> {
      try {
        handleSepiaCommand(percentage, updateImageToDisplay);
      } catch (ImageProcessorException e) {
        userOutput.displayMessage(e.getMessage());
      }
    });
  }


  private void handleSepiaCommand(Integer percentage,
                                  boolean updateImageToDisplay) throws ImageProcessorException {
    String sepiaImageName = createDestinationImageName(imageToDisplay,
            UserCommand.SEPIA);
    ImageProcessingRequest request = ImageProcessingRequest.builder()
            .imageName(imageToDisplay)
            .destinationImageName(sepiaImageName)
            .percentage(percentage)
            .build();
    imageProcessingService.sepiaImage(request);
    if (updateImageToDisplay) {
      updateImageToDisplay(sepiaImageName);
    } else {
      updateViewToDisplay(sepiaImageName);
    }
  }

  private void handleClearCommand() throws ImageProcessorException {
    imageProcessingService.clearMemory();
    updateImageToDisplay(null);
  }


  private void validateImageToApplyCommand() throws ImageProcessorException {
    if (StringUtils.isNullOrEmpty(imageToDisplay)) {
      throw new ImageProcessorException("No image loaded to apply command");
    }
  }

  private String createDestinationImageName(String imageName,
                                            UserCommand command) {
    return command.getCommand() + "_" + imageName;
  }


  @Override
  public void processCommands() throws ImageProcessingRunTimeException.QuitException {
    throw new ImageProcessingRunTimeException.QuitException("Not required for" +
            " GUI based application");
  }
}
