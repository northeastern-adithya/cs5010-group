import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.Test;

import app.ImageBuilder;

import static org.junit.Assert.*;

public class ImageBuilderLocalTest {

    @Test
    public void testReadLocalImage() {
        try {
            File file = new File("/Users/ishanrai/Desktop/Repos/CS5010/cs5010-group/assignments/assignment4/src/resource/manhattan-small.png");
            BufferedImage expectedImage = ImageIO.read(file);

            ImageBuilder imageBuilder = new ImageBuilder();
            app.visual.Image actualImage = imageBuilder.buildImageFromPath(file.getPath());

            int width = expectedImage.getWidth();
            int height = expectedImage.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int expectedPixel = expectedImage.getRGB(x, y);
                    int expectedRed = (expectedPixel >> 16) & 0xff;
                    int expectedGreen = (expectedPixel >> 8) & 0xff;
                    int expectedBlue = expectedPixel & 0xff;

                    app.color.Pixel actualPixel = actualImage.getPixel(x, y);
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