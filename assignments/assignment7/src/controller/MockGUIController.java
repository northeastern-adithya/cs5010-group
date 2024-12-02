package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * The MockGUIController class is a mock implementation of the Features interface. It simulates the
 * behavior of an image processing controller by logging method calls instead of performing actual
 * image processing. This is useful for testing purposes.
 */
public class MockGUIController implements Features {

  private List<String> log = new ArrayList<>();

  /**
   * Gets the list of logged method calls.
   *
   * @return A list of strings representing the logged method calls.
   */
  public List<String> getLog() {
    return log;
  }

  @Override
  public void loadImage() {
    log.add("loadImage called");
  }

  @Override
  public void saveImage() {
    log.add("saveImage called");
  }

  @Override
  public void applySepiaEffect() {
    log.add("applySepiaEffect called");
  }

  @Override
  public void applyBlurEffect() {
    log.add("applyBlurEffect called");
  }

  @Override
  public void applySharpenEffect() {
    log.add("applySharpenEffect called");
  }

  @Override
  public void applyGreyscaleEffect() {
    log.add("applyGreyscaleEffect called");
  }

  @Override
  public void applyBrightenEffect() {
    log.add("applyBrightenEffect called");
  }

  @Override
  public void applyVerticalFlip() {
    log.add("applyVerticalFlip called");
  }

  @Override
  public void applyHorizontalFlip() {
    log.add("applyHorizontalFlip called");
  }

  @Override
  public void applyLevelsAdjust() {
    log.add("applyLevelsAdjust called");
  }

  @Override
  public void applyColorCorrection() {
    log.add("applyColorCorrection called");
  }

  @Override
  public void applyCompress() {
    log.add("applyCompress called");
  }

  @Override
  public void applyDownscale() {
    log.add("applyDownscale called");
  }

  @Override
  public void visualizeRedComponent() {
    log.add("visualizeRedComponent called");
  }

  @Override
  public void visualizeGreenComponent() {
    log.add("visualizeGreenComponent called");
  }

  @Override
  public void visualizeBlueComponent() {
    log.add("visualizeBlueComponent called");
  }

}
