package controller;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import model.Pixel;
import view.ImageProcessingGUI;

/**
 * Mock class for ImageProcessingGUI.
 * This class is used to test the controller mocking the behaviours of GUI view.
 */
public class MockImageProcessingGUI extends ImageProcessingGUI {

  private final StringBuilder log;
  private final String currentImageName;
  private final JFrame frame;
  private final String loadImagePath;
  private final String saveImagePath;
  private final String splitPercentage;

  /**
   * Constructor for the MockImageProcessingGUI class to test the controller.
   *
   * @param log               the log to log the output
   * @param currentImageName  the current image name
   * @param frame             the frame
   * @param loadImagePath     the load image path
   * @param saveImagePath     the save image path
   * @param splitPercentage   the split percentage
   */
  public MockImageProcessingGUI(StringBuilder log, String currentImageName,
                                JFrame frame,
                                String loadImagePath,
                                String saveImagePath,
                                String splitPercentage) {
    this.log = log;
    this.currentImageName = currentImageName;
    this.frame = frame;
    this.loadImagePath = loadImagePath;
    this.saveImagePath = saveImagePath;
    this.splitPercentage = splitPercentage;
  }

  @Override
  public void showGUI() {
    log("showGUI called");
  }

  @Override
  public String getCurrentImageName() {
    log("getCurrentImageName called");
    return currentImageName;
  }

  @Override
  public void setCurrentImageName(String imageName) {
    log("setCurrentImageName called");
  }

  @Override
  public JFrame getFrame() {
    log("getFrame called");
    return frame;
  }


  @Override
  public String load() {
    log("load called");
    return this.loadImagePath;
  }


  @Override
  public String save() {
    log("save called");
    return this.saveImagePath;
  }


  @Override
  public void displayError(String error) {
    log("displayError called");

  }


  @Override
  public void displayMessage(String message) {
    log("displayMessage called");
  }

  @Override
  public void displayImage(Pixel[][] imageData) {
    log("displayImage called");
  }


  @Override
  public void displayImage(String imagePath) {
    log("displayImage called");
  }


  @Override
  public void displayHistogram(BufferedImage histogramImage) {
    log("displayHistogram called");
  }

  @Override
  public void setController(ImageProcessingGUIController guiController) {
    log("setController called");
  }

  @Override
  public String getSplitPercentageFromUser() {
    log("getSplitPercentageFromUser called");
    return splitPercentage;
  }

  private void log(String message) {
    log.append(message).append("\n");
  }
}
