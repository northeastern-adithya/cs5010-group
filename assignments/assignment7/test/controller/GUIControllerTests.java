package controller;

import org.junit.Test;

import javax.swing.JFrame;

import model.ImageImplementation;
import model.ImageModel;
import model.Pixel;

import static org.junit.Assert.assertArrayEquals;

/**
 * JUnit test class for GUIController to see if controller is working as
 * expected.
 */
public class GUIControllerTests {

  private static Pixel[][] getRandomImage() {
    return new Pixel[][]{
            {new Pixel(255, 0, 0), new Pixel(0, 0, 255)},
            {new Pixel(0, 255, 0), new Pixel(128, 128, 128)}
    };
  }

  @Test
  public void testImageDithering() {
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
