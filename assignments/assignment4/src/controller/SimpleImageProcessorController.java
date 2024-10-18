package controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import exception.DisplayException;
import exception.ImageProcessorException;
import exception.QuitException;
import model.UserCommand;
import services.ImageProcessor;
import view.input.UserInput;
import view.output.UserOutput;

public class SimpleImageProcessorController implements ImageProcessorController {

  private final UserInput input;
  private final UserOutput output;
  private final ImageProcessor imageProcessor;

  public SimpleImageProcessorController(UserInput input, UserOutput userOutput,ImageProcessor imageProcessor) {
    this.input = input;
    this.output = userOutput;
    this.imageProcessor = imageProcessor;
  }

  @Override
  public void processCommands() {
    if(Objects.isNull(imageProcessor)){
      throw new QuitException("ImageProcessor cannot be null");
    }

    try {
      this.processCommands(imageProcessor);
    } catch (ImageProcessorException e) {
      try {
        output.displayMessage(String.format("Failed to process input with message: %s\n. Please try again." ,e.getMessage()));
      } catch (DisplayException displayException) {
        throw new QuitException("Error displaying message");
      }
    }
  }


  private void processCommands(ImageProcessor imageProcessor) throws ImageProcessorException {
    Scanner scan = new Scanner(input.getUserInput());
    String command = scan.next();
      switch (UserCommand.getCommand(command)) {
        case LOAD:
          this.loadImage(scan);
          break;
        default:
          this.output.displayMessage("Invalid command received.Please try again.");
      }
  }


  private void loadImage(Scanner scan) throws ImageProcessorException {
    String path = scan.next();
    String fileName = scan.next();
    imageProcessor.loadImage(path,fileName);
  }
}
