package controller;

import exception.QuitException;

public interface ImageProcessorController {
  void processCommands() throws QuitException;
}
