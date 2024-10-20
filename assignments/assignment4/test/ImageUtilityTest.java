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
}