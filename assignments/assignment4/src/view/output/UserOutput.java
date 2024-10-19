package view.output;

import exception.DisplayException;

public interface UserOutput {
  void displayMessage(String message) throws DisplayException;
}
