package controller;

import org.junit.Test;

import javax.swing.JFrame;

import model.ImageImplementation;
import model.ImageModel;
import model.Pixel;

import static controller.TestUtils.getRandomImage;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

/**
 * JUnit test class for GUIController to see if controller is working as
 * expected.
 */
public class GUIControllerTests {

  @Test
  public void testImageDitheringWithLessThanZero() {
    StringBuilder log = new StringBuilder();
    ImageModel model = new ImageImplementation();
    Pixel[][] pixels = getRandomImage();
    model.setImage(pixels);
    String imageName = "testImage";
    Features features = initialiseFeatures(
            log, imageName, null,
            null, null,
            "-1",
            model
    );
    features.applyDither();
    // If passed less than 0, it should be same as 100
    assertArrayEquals(
            new Pixel[][]{
                    {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
                    {new Pixel(255, 255, 255), new Pixel(0, 0, 0)}
            },
            model.getImage()
    );
  }

  @Test
  public void testImageDitheringWithGreaterThanHundred() {
    StringBuilder log = new StringBuilder();
    ImageModel model = new ImageImplementation();
    Pixel[][] pixels = getRandomImage();
    model.setImage(pixels);
    String imageName = "testImage";
    Features features = initialiseFeatures(
            log, imageName, null,
            null, null,
            "101",
            model
    );
    features.applyDither();
    // If passed 101, it should be same as 100
    assertArrayEquals(
            new Pixel[][]{
                    {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
                    {new Pixel(255, 255, 255), new Pixel(0, 0, 0)}
            },
            model.getImage()
    );
  }

  @Test
  public void testImageDitheringWithZeroPercentage() {
    StringBuilder log = new StringBuilder();
    ImageModel model = new ImageImplementation();
    Pixel[][] pixels = getRandomImage();
    model.setImage(pixels);
    String imageName = "testImage";
    Features features = initialiseFeatures(
            log, imageName, null,
            null, null,
            "0",
            model
    );
    features.applyDither();
    assertArrayEquals(
            new Pixel[][]{
                    {new Pixel(0, 0, 0), new Pixel(0, 0, 255)},
                    {new Pixel(0, 0, 0), new Pixel(128, 128, 128)}
            },
            model.getImage()
    );
  }

  @Test
  public void testImageDitheringWithHundredPercentage() {
    StringBuilder log = new StringBuilder();
    ImageModel model = new ImageImplementation();
    Pixel[][] pixels = getRandomImage();
    model.setImage(pixels);
    String imageName = "testImage";
    Features features = initialiseFeatures(
            log, imageName, null,
            null, null,
            "100",
            model
    );
    features.applyDither();
    assertArrayEquals(
            new Pixel[][]{
                    {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
                    {new Pixel(255, 255, 255), new Pixel(0, 0, 0)}
            },
            model.getImage()
    );
  }

  @Test
  public void testImageDitheringWithFiftyPercentage() {
    StringBuilder log = new StringBuilder();
    ImageModel model = new ImageImplementation();
    Pixel[][] pixels = getRandomImage();
    model.setImage(pixels);
    String imageName = "testImage";
    Features features = initialiseFeatures(
            log, imageName, null,
            null, null,
            "50",
            model
    );
    features.applyDither();
    assertArrayEquals(
            new Pixel[][]{
                    {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
                    {new Pixel(255, 255, 255), new Pixel(0, 0, 0)}
            },
            model.getImage()
    );
  }


  private Features initialiseFeatures(StringBuilder log,
                                      String currentImageName, JFrame frame,
                                      String loadImagePath,
                                      String saveImagePath,
                                      String splitPercentage,
                                      ImageModel model) {
    return new ImageProcessingGUIController(
            new MockImageProcessingGUI(
                    log, currentImageName, frame, loadImagePath,
                    saveImagePath, splitPercentage
            ), null, model
    );
  }
}
