package controller;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import exception.DisplayException;
import exception.ImageProcessorException;
import exception.QuitException;
import model.UserCommand;
import services.ImageProcessingService;
import view.input.UserInput;
import view.output.UserOutput;

public class SimpleImageProcessorController implements ImageProcessorController {

  private final UserInput input;
  private final UserOutput output;
  private final ImageProcessingService imageProcessor;

  public SimpleImageProcessorController(UserInput input, UserOutput userOutput, ImageProcessingService imageProcessor) {
    validateInput(input, userOutput, imageProcessor);
    this.input = input;
    this.output = userOutput;
    this.imageProcessor = imageProcessor;
    displayCommands();
  }

  void validateInput(UserInput input, UserOutput output, ImageProcessingService imageProcessor) throws QuitException {
    if (Objects.isNull(input) || Objects.isNull(output) || Objects.isNull(imageProcessor)) {
      throw new QuitException("Invalid input provided.");
    }
  }

  private void displayCommands() {
    displayMessage(UserCommand.getUserCommands());
  }

  @Override
  public void processCommands() throws QuitException {
    Scanner scan = new Scanner(input.getUserInput());
    String userInput = scan.next();
    Optional<UserCommand> command = UserCommand.getCommand(userInput);
    command.ifPresentOrElse(
            userCommand -> {
              try {
                processUserCommands(userCommand, scan);
              } catch (ImageProcessorException e) {
                displayMessage(String.format("Failed to process input with message: %s\n. Please try again.", e.getMessage()));
              }
            },
            () -> handleInvalidCommand(userInput)

    );
  }


  private void processUserCommands(UserCommand command, Scanner scan) throws ImageProcessorException {
    switch (command) {
      case LOAD:
        this.loadImage(scan);
        break;
      case SAVE:
        this.saveImage(scan);
        break;
      case RED_COMPONENT:
        this.createRedComponent(scan);
        break;
      case BLUE_COMPONENT:
        this.createBlueComponent(scan);
        break;
      case GREEN_COMPONENT:
        this.createGreenComponent(scan);
        break;
      case VALUE_COMPONENT:
        this.valueComponent(scan);
        break;
      case LUMA_COMPONENT:
        this.lumaComponent(scan);
        break;
      case INTENSITY_COMPONENT:
        this.intensityComponent(scan);
        break;
      case HORIZONTAL_FLIP:
        this.horizontalFlip(scan);
        break;
      case VERTICAL_FLIP:
        this.verticalFlip(scan);
        break;
      case BRIGHTEN:
        this.brighten(scan);
        break;
      case RGB_SPLIT:
        this.rgbSplit(scan);
        break;
      case RGB_COMBINE:
        this.rgbCombine(scan);
        break;
      case BLUR:
        this.blurImage(scan);
        break;
      case SHARPEN:
        this.sharpenImage(scan);
        break;
      case SEPIA:
        this.sepiaImage(scan);
        break;
      case RUN:
        this.run(scan);
        break;
      case QUIT:
        throw new QuitException("Shutting down....");
      case HELP:
        displayCommands();
        break;
    }
  }

  private void handleInvalidCommand(String command) {
    displayMessage(String.format("Invalid command: %s received.Please try again.", command));
    displayCommands();
  }


  private void loadImage(Scanner scan) throws ImageProcessorException {
    String path = scan.next();
    String imageName = scan.next();
    imageProcessor.loadImage(path, imageName);
    displayMessage(String.format("Successfully loaded: %s from path: %s", imageName, path));
  }

  private void saveImage(Scanner scan) throws ImageProcessorException {
    String path = scan.next();
    String imageName = scan.next();
    imageProcessor.saveImage(path, imageName);
    displayMessage(String.format("Successfully saved: %s to path: %s", imageName, path));
  }

  private void createRedComponent(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.createRedComponent(imageName, destinationImageName);
    displayMessage("Successfully created red component.");
  }

  private void createGreenComponent(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.createGreenComponent(imageName, destinationImageName);
    displayMessage("Successfully created green component.");
  }

  private void createBlueComponent(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.createBlueComponent(imageName, destinationImageName);
    displayMessage("Successfully created blue component.");
  }

  private void valueComponent(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.createValueComponent(imageName, destinationImageName);
    displayMessage("Successfully created value component.");
  }

  private void lumaComponent(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.createLumaComponent(imageName, destinationImageName);
    displayMessage("Successfully created luma component.");
  }

  private void intensityComponent(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.createIntensityComponent(imageName, destinationImageName);
    displayMessage("Successfully created intensity component.");
  }

  private void horizontalFlip(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.horizontalFlip(imageName, destinationImageName);
    displayMessage("Successfully flipped the image horizontally.");
  }

  private void verticalFlip(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.verticalFlip(imageName, destinationImageName);
    displayMessage("Successfully flipped the image vertically.");
  }

  private void brighten(Scanner scan) throws ImageProcessorException {
    if (!scan.hasNextInt()) {
      throw new ImageProcessorException("Invalid factor provided for brightening the image.");
    }
    int factor = scan.nextInt();
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.brighten(imageName, destinationImageName, factor);
    displayMessage(String.format("Successfully brightened the image at factor:%s", factor));
  }


  private void rgbSplit(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageNameRed = scan.next();
    String destinationImageNameGreen = scan.next();
    String destinationImageNameBlue = scan.next();
    imageProcessor.rgbSplit(imageName, destinationImageNameRed, destinationImageNameGreen, destinationImageNameBlue);
    displayMessage("Successfully split the image into RGB components.");
  }

  private void rgbCombine(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String redImageName = scan.next();
    String greenImageName = scan.next();
    String blueImageName = scan.next();
    imageProcessor.rgbCombine(imageName, redImageName, greenImageName, blueImageName);
    displayMessage("Successfully combined the RGB components.");
  }

  private void blurImage(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.blurImage(imageName, destinationImageName);
    displayMessage("Successfully blurred the image.");
  }

  private void sharpenImage(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.sharpenImage(imageName, destinationImageName);
    displayMessage("Successfully sharpened the image.");
  }

  private void sepiaImage(Scanner scan) throws ImageProcessorException {
    String imageName = scan.next();
    String destinationImageName = scan.next();
    imageProcessor.sepiaImage(imageName, destinationImageName);
    displayMessage("Successfully converted the image to sepia.");
  }

  private void run(Scanner scan) throws ImageProcessorException {
    // TODO: Implement this method
  }


  private void displayMessage(String message) {
    try {
      this.output.displayMessage(message);
    } catch (DisplayException e) {
      throw new QuitException(String.format("Error displaying message:%s", message), e);
    }
  }


}
