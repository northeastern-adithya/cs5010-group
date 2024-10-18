package view.output;

import java.io.IOException;

import exception.DisplayException;
import exception.ImageProcessorException;

public interface UserOutput {
  void displayMessage(String message) throws DisplayException;
}
