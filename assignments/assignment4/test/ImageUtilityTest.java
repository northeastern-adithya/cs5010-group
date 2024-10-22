import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import model.color.Pixel;
import model.visual.Image;

import static org.junit.Assert.*;

import utility.ImageUtility;


public class ImageUtilityTest {

    @Test
    public void testReadLocalImage() {
      try {
        File file = new File("test_resources/download.jpeg");
        BufferedImage expectedImage = ImageIO.read(file);

        Image actualImage = ImageUtility.loadImage(file.getPath());

        int width = expectedImage.getWidth();
        int height = expectedImage.getHeight();

        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            int expectedPixel = expectedImage.getRGB(x, y);
            int expectedRed = (expectedPixel >> 16) & 0xff;
            int expectedGreen = (expectedPixel >> 8) & 0xff;
            int expectedBlue = expectedPixel & 0xff;

            Pixel actualPixel = actualImage.getPixel(x, y);
            assertEquals(expectedRed, actualPixel.getRed());
            assertEquals(expectedGreen, actualPixel.getGreen());
            assertEquals(expectedBlue, actualPixel.getBlue());
          }
        }
      } catch (Exception e) {
        fail("Exception should not be thrown: " + e.getMessage());
      }
  }

  @Test
    public void testSaveImage() {
        try {
            // Load an image from the test resources
            File inputFile = new File("test_resources/download.jpeg");
            Image image = ImageUtility.loadImage(inputFile.getPath());

            // Save the image to a new file
            String outputPath = "test_resources/download-output.png";
            ImageUtility.saveImage(image, outputPath);

            // Load the saved image
            File outputFile = new File(outputPath);
            BufferedImage savedImage = ImageIO.read(outputFile);

            // Compare the original and saved images
            BufferedImage originalImage = ImageIO.read(inputFile);
            assertEquals(originalImage.getWidth(), savedImage.getWidth());
            assertEquals(originalImage.getHeight(), savedImage.getHeight());

            for (int y = 0; y < originalImage.getHeight(); y++) {
                for (int x = 0; x < originalImage.getWidth(); x++) {
                    assertEquals(originalImage.getRGB(x, y), savedImage.getRGB(x, y));
                }
            }
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

  @Test
  public void testSaveSepiaImage() {
    try {
      // Load an image from the test resources
      File inputFile = new File("test_resources/manhattan-small.png");
      Image image = ImageUtility.loadImage(inputFile.getPath());

      // Save the image to a new file
      String outputPath = "test_resources/manhattan-small-sepia-output.png";
      ImageUtility.saveImage(image.getSepia(), outputPath);

      // Load the saved image
      File outputFile = new File(outputPath);
      BufferedImage savedImage = ImageIO.read(outputFile);

      // Load the expected result image
      String resultPath = "test_resources/expected_results/manhattan-small-sepia.png";
      File expectedFile = new File(resultPath);
      BufferedImage expectedImage = ImageIO.read(expectedFile);

      // Compare the expected result and saved images
      assertEquals(expectedImage.getWidth(), savedImage.getWidth());
      assertEquals(expectedImage.getHeight(), savedImage.getHeight());

      for (int y = 0; y < expectedImage.getHeight(); y++) {
        for (int x = 0; x < expectedImage.getWidth(); x++) {
          assertEquals(expectedImage.getRGB(x, y), savedImage.getRGB(x, y));
        }
      }
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void testSaveLumaImage() {
    try {
      // Load an image from the test resources
      File inputFile = new File("test_resources/manhattan-small.png");
      Image image = ImageUtility.loadImage(inputFile.getPath());

      // Save the image to a new file
      String outputPath = "test_resources/manhattan-small-luma.png";
      ImageUtility.saveImage(image.getLuma(), outputPath);

      // Load the saved image
      File outputFile = new File(outputPath);
      BufferedImage savedImage = ImageIO.read(outputFile);

      // Load the expected result image
      String resultPath = "test_resources/expected_results/manhattan-small-luma-greyscale.png";
      File expectedFile = new File(resultPath);
      BufferedImage expectedImage = ImageIO.read(expectedFile);

      // Compare the expected result and saved images
      assertEquals(expectedImage.getWidth(), savedImage.getWidth());
      assertEquals(expectedImage.getHeight(), savedImage.getHeight());

      for (int y = 0; y < expectedImage.getHeight(); y++) {
        for (int x = 0; x < expectedImage.getWidth(); x++) {
          assertEquals(expectedImage.getRGB(x, y), savedImage.getRGB(x, y));
        }
      }
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void testSaveIntensityImage() {
    try {
      // Load an image from the test resources
      File inputFile = new File("test_resources/manhattan-small.png");
      Image image = ImageUtility.loadImage(inputFile.getPath());

      // Save the image to a new file
      String outputPath = "test_resources/manhattan-small-intensity.png";
      ImageUtility.saveImage(image.getIntensity(), outputPath);

      // Load the saved image
      File outputFile = new File(outputPath);
      BufferedImage savedImage = ImageIO.read(outputFile);

      // Load the expected result image
      String resultPath = "test_resources/expected_results/manhattan-small-intensity-greyscale.png";
      File expectedFile = new File(resultPath);
      BufferedImage expectedImage = ImageIO.read(expectedFile);

      // Compare the expected result and saved images
      assertEquals(expectedImage.getWidth(), savedImage.getWidth());
      assertEquals(expectedImage.getHeight(), savedImage.getHeight());

      for (int y = 0; y < expectedImage.getHeight(); y++) {
        for (int x = 0; x < expectedImage.getWidth(); x++) {
          assertEquals(expectedImage.getRGB(x, y), savedImage.getRGB(x, y));
        }
      }
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void testSaveValueImage() {
    try {
      // Load an image from the test resources
      File inputFile = new File("test_resources/manhattan-small.png");
      Image image = ImageUtility.loadImage(inputFile.getPath());

      // Save the image to a new file
      String outputPath = "test_resources/manhattan-small-value.png";
      ImageUtility.saveImage(image.getValue(), outputPath);

      // Load the saved image
      File outputFile = new File(outputPath);
      BufferedImage savedImage = ImageIO.read(outputFile);

      // Load the expected result image
      String resultPath = "test_resources/expected_results/manhattan-small-value-greyscale.png";
      File expectedFile = new File(resultPath);
      BufferedImage expectedImage = ImageIO.read(expectedFile);

      // Compare the expected result and saved images
      assertEquals(expectedImage.getWidth(), savedImage.getWidth());
      assertEquals(expectedImage.getHeight(), savedImage.getHeight());

      for (int y = 0; y < expectedImage.getHeight(); y++) {
        for (int x = 0; x < expectedImage.getWidth(); x++) {
          assertEquals(expectedImage.getRGB(x, y), savedImage.getRGB(x, y));
        }
      }
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }
}