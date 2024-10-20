import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.Test;

import model.ImageBuilder;
import model.color.Pixel;
import model.visual.RenderedImage;

import static org.junit.Assert.*;

public class ImageBuilderLocalTest {

    @Test
    public void testReadLocalImage() {
        try {
            File file = new File("test_resources/manhattan-small.png");
            BufferedImage expectedImage = ImageIO.read(file);

            ImageBuilder imageBuilder = new ImageBuilder();
            RenderedImage actualImage = imageBuilder.buildImageFromPath(file.getPath());

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