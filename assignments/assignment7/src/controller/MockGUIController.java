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

  /**
   * logs loadImage call.
   */
  @Override
  public void loadImage() {
    log.add("loadImage called");
  }

  /**
   *
   */
  @Override
  public void saveImage() {
    log.add("saveImage called");
  }

  /**
   * logs applySepiaEffect call.
   */
  @Override
  public void applySepiaEffect() {
    log.add("applySepiaEffect called");
  }

  /**
   * logs applyBlurEffect call.
   */
  @Override
  public void applyBlurEffect() {
    log.add("applyBlurEffect called");
  }

  /**
   * logs applySharpenEffect call.
   */
  @Override
  public void applySharpenEffect() {
    log.add("applySharpenEffect called");
  }

  /**
   * logs applyGreyscaleEffect call.
   */
  @Override
  public void applyGreyscaleEffect() {
    log.add("applyGreyscaleEffect called");
  }

  /**
   * logs applyBrightenEffect call.
   */
  @Override
  public void applyBrightenEffect() {
    log.add("applyBrightenEffect called");
  }

  /**
   * logs applyVerticalFlip call.
   */
  @Override
  public void applyVerticalFlip() {
    log.add("applyVerticalFlip called");
  }

  /**
   * logs applyHorizontalFlip call.
   */
  @Override
  public void applyHorizontalFlip() {
    log.add("applyHorizontalFlip called");
  }

  /**
   * logs applyLevelsAdjust call.
   */
  @Override
  public void applyLevelsAdjust() {
    log.add("applyLevelsAdjust called");
  }

  /**
   * logs applyColorCorrection call.
   */
  @Override
  public void applyColorCorrection() {
    log.add("applyColorCorrection called");
  }

  /**
   * logs applyCompress call.
   */
  @Override
  public void applyCompress() {
    log.add("applyCompress called");
  }

  /**
   * logs applyDownscale call.
   */
  @Override
  public void applyDownscale() {
    log.add("applyDownscale called");
  }

  /**
   * logs visualizeRedComponent call.
   */
  @Override
  public void visualizeRedComponent() {
    log.add("visualizeRedComponent called");
  }

  /**
   * logs visualizeGreenComponent call.
   */
  @Override
  public void visualizeGreenComponent() {
    log.add("visualizeGreenComponent called");
  }

  /**
   * logs visualizeBlueComponent call.
   */
  @Override
  public void visualizeBlueComponent() {
    log.add("visualizeBlueComponent called");
  }

  /**
   * logs applyDither call.
   */
  @Override
  public void applyDither() {
    log.add("applyDither called");
  }
}
