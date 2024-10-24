import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import factories.Factory;
import model.enumeration.FilterOption;
import utility.FilterUtils;
import model.enumeration.ImageType;
import model.enumeration.LinearColorTransformationType;
import model.enumeration.PixelType;
import model.enumeration.UserCommand;
import model.memory.HashMapMemory;
import model.pixels.Pixel;
import model.pixels.RGB;
import model.visual.Image;
import model.visual.RenderedImage;
import view.input.ConsoleInput;
import view.output.ConsoleOutput;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class UnitTestForImageProcessor {

  public static class DisplayExceptionTest {

    @Test
    public void testDisplayExceptionMessage() {
      String message = "Error displaying image";
      Throwable cause = new Throwable("Cause of the error");
      ImageProcessingRunTimeException.DisplayException exception = new ImageProcessingRunTimeException.DisplayException(message, cause);
      assertEquals(message, exception.getMessage());
    }

    @Test
    public void testDisplayExceptionMessageAndCause() {
      String message = "Error displaying image";
      Throwable cause = new Throwable("Cause of the error");
      ImageProcessingRunTimeException.DisplayException exception = new ImageProcessingRunTimeException.DisplayException(message, cause);
      assertEquals(message, exception.getMessage());
      assertEquals(cause, exception.getCause());
    }
  }

  public static class ImageProcessingRunTimeExceptionTest {
    @Test
    public void testConstructorWithMessage() {
      String message = "Test message";
      ImageProcessingRunTimeException exception = new ImageProcessingRunTimeException(message);
      assertEquals(message, exception.getMessage());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
      String message = "Test message";
      Throwable cause = new Throwable("Cause message");
      ImageProcessingRunTimeException exception = new ImageProcessingRunTimeException(message, cause);
      assertEquals(message, exception.getMessage());
      assertEquals(cause, exception.getCause());
    }
  }

  public static class ImageProcessorExceptionTest {
    @Test
    public void testImageProcessorExceptionMessage() {
      String errorMessage = "Error processing image";
      ImageProcessorException exception = new ImageProcessorException(errorMessage);
      assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testImageProcessorExceptionMessageAndCause() {
      String errorMessage = "Error processing image";
      Throwable cause = new Throwable("Cause of the error");
      ImageProcessorException exception = new ImageProcessorException(errorMessage, cause);
      assertEquals(errorMessage, exception.getMessage());
      assertEquals(cause, exception.getCause());
    }
  }

  public static class NotFoundExceptionTest {

    @Test
    public void testNotFoundExceptionMessage() {
      String errorMessage = "Error processing image";
      ImageProcessorException.NotFoundException exception = new ImageProcessorException.NotFoundException(errorMessage);
      assertEquals(errorMessage, exception.getMessage());
    }

  }

  public static class NotImplementedExceptionTest {

    @Test
    public void testConstructorWithMessage() {
      String message = "Test message";
      ImageProcessingRunTimeException.NotImplementedException exception = new ImageProcessingRunTimeException.NotImplementedException(message);
      assertEquals(message, exception.getMessage());
    }
  }

  public static class QuitExceptionTest {

    @Test
    public void testConstructorWithMessage() {
      String message = "Test message";
      ImageProcessingRunTimeException.QuitException exception = new ImageProcessingRunTimeException.QuitException(message);
      assertEquals(message, exception.getMessage());
    }
  }

  public static class ImageTest {
    @Test
    public void testGetPixel() {
      Pixel[][] pixels = new Pixel[2][2];
      pixels[0][0] = new RGB(255, 0, 0);
      pixels[0][1] = new RGB(0, 255, 0);
      pixels[1][0] = new RGB(0, 0, 255);
      pixels[1][1] = new RGB(255, 255, 0);

      RenderedImage image = new RenderedImage(pixels);

      assertEquals(pixels[0][0], image.getPixel(0, 0));
      assertEquals(pixels[0][1], image.getPixel(0, 1));
      assertEquals(pixels[1][0], image.getPixel(1, 0));
      assertEquals(pixels[1][1], image.getPixel(1, 1));
    }

    @Test
    public void testBrightenImage() {
      Pixel[][] pixels = new Pixel[2][2];
      pixels[0][0] = new RGB(100, 100, 100);
      pixels[0][1] = new RGB(150, 150, 150);
      pixels[1][0] = new RGB(200, 200, 200);
      pixels[1][1] = new RGB(250, 250, 250);

      RenderedImage image = new RenderedImage(pixels);
      Image brightenedImage = image.adjustImageBrightness(50);

      assertEquals(new RGB(150, 150, 150), brightenedImage.getPixel(0, 0));
      assertEquals(new RGB(200, 200, 200), brightenedImage.getPixel(0, 1));
      assertEquals(new RGB(250, 250, 250), brightenedImage.getPixel(1, 0));
      assertEquals(new RGB(255, 255, 255), brightenedImage.getPixel(1, 1));
    }

    @Test
    public void testDarkenImage() {
      Pixel[][] pixels = new Pixel[2][2];
      pixels[0][0] = new RGB(100, 100, 100);
      pixels[0][1] = new RGB(150, 150, 150);
      pixels[1][0] = new RGB(200, 200, 200);
      pixels[1][1] = new RGB(250, 250, 250);

      RenderedImage image = new RenderedImage(pixels);
      Image darkenedImage = (RenderedImage) image.adjustImageBrightness(-50);

      assertEquals(new RGB(50, 50, 50), darkenedImage.getPixel(0, 0));
      assertEquals(new RGB(100, 100, 100), darkenedImage.getPixel(0, 1));
      assertEquals(new RGB(150, 150, 150), darkenedImage.getPixel(1, 0));
      assertEquals(new RGB(200, 200, 200), darkenedImage.getPixel(1, 1));
    }
  }


  public static class ConsoleInputTest {

    @Test
    public void testConstructorWithValidInputStream() {
      ConsoleInput consoleInput = new ConsoleInput(new StringReader("test input"));
      assertNotNull(consoleInput);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullInputStream() {
      new ConsoleInput(null);
    }

    @Test
    public void testGetUserInput() {
      StringReader reader = new StringReader("test input");
      ConsoleInput consoleInput = new ConsoleInput(reader);
      assertEquals(reader, consoleInput.getUserInput());
    }
  }

  public static class ConsoleOutputTest {

    private StringWriter stringWriter;
    private ConsoleOutput consoleOutput;

    @Before
    public void setUp() {
      stringWriter = new StringWriter();
      consoleOutput = new ConsoleOutput(stringWriter);
    }

    @Test
    public void testDisplayMessage() throws ImageProcessingRunTimeException.DisplayException {
      String message = "Hello, World!";
      consoleOutput.displayMessage(message);
      assertEquals("Hello, World!\n", stringWriter.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullOutput() {
      new ConsoleOutput(null);
    }

    @Test(expected = ImageProcessingRunTimeException.DisplayException.class)
    public void testDisplayMessageThrowsDisplayException() throws ImageProcessingRunTimeException.DisplayException {
      Appendable failingAppendable = new Appendable() {
        @Override
        public Appendable append(CharSequence csq) throws IOException {
          throw new IOException("Forced IOException");
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) throws IOException {
          throw new IOException("Forced IOException");
        }

        @Override
        public Appendable append(char c) throws IOException {
          throw new IOException("Forced IOException");
        }
      };
      ConsoleOutput failingConsoleOutput = new ConsoleOutput(failingAppendable);
      failingConsoleOutput.displayMessage("This will fail");
    }
  }

  public static class HashMapMemoryTest {

    private HashMapMemory memory;
    private Image testImage;

    @Before
    public void setUp() {
      memory = new HashMapMemory();
      Pixel[][] pixels = new Pixel[0][0];
      testImage = new RenderedImage(pixels);
    }

    @Test
    public void testConstructor() {
      assertNotNull(memory);
    }

    @Test
    public void testAddAndRetrieveImage() throws ImageProcessorException.NotFoundException {
      memory.addImage("testImage", testImage);
      assertEquals(testImage, memory.getImage("testImage"));
    }

    @Test(expected = ImageProcessorException.NotFoundException.class)
    public void testAddNullImage() throws ImageProcessorException.NotFoundException {
      memory.addImage("nullImage", null);
      assertNull(memory.getImage("nullImage"));
    }

    @Test(expected = ImageProcessorException.NotFoundException.class)
    public void testRetrieveNonExistentImage() throws ImageProcessorException.NotFoundException {
      memory.getImage("nonExistentImage");
    }
  }

  public static class BlackPixelTest {
    private final int BLACK = 0;
    private RGB blackPixel;

    @Before
    public void setUp() {
      blackPixel = new RGB(BLACK, BLACK, BLACK);
    }

    @Test
    public void testConstructor() {
      assertEquals(BLACK, blackPixel.getRed());
      assertEquals(BLACK, blackPixel.getGreen());
      assertEquals(BLACK, blackPixel.getBlue());
    }

    @Test
    public void testCreatePixel() {
      Pixel newPixel = blackPixel.createPixel(0, 0, 0);
      assertTrue(newPixel instanceof RGB);
      assertEquals(BLACK, newPixel.getRed());
      assertEquals(BLACK, newPixel.getGreen());
      assertEquals(BLACK, newPixel.getBlue());
    }

    @Test
    public void testCreateRedComponent() {
      Pixel redComponent = blackPixel.createRedComponent();
      assertEquals(BLACK, redComponent.getRed());
      assertEquals(BLACK, redComponent.getGreen());
      assertEquals(BLACK, redComponent.getBlue());
    }

    @Test
    public void testCreateGreenComponent() {
      Pixel greenComponent = blackPixel.createGreenComponent();
      assertEquals(BLACK, greenComponent.getRed());
      assertEquals(BLACK, greenComponent.getGreen());
      assertEquals(BLACK, greenComponent.getBlue());
    }

    @Test
    public void testCreateBlueComponent() {
      Pixel blueComponent = blackPixel.createBlueComponent();
      assertEquals(BLACK, blueComponent.getRed());
      assertEquals(BLACK, blueComponent.getGreen());
      assertEquals(BLACK, blueComponent.getBlue());
    }

    @Test
    public void testEqualsReflexive() {
      RGB x = new RGB(BLACK, BLACK, BLACK);
      assertTrue("Reflexive property failed", x.equals(x));
    }

    @Test
    public void testEqualsSymmetric() {
      RGB x = new RGB(BLACK, BLACK, BLACK);
      RGB y = new RGB(BLACK, BLACK, BLACK);

      assertTrue("Forward symmetric test failed", x.equals(y));
      assertTrue("Backward symmetric test failed", y.equals(x));
    }

    @Test
    public void testEqualsTransitive() {
      RGB x = new RGB(BLACK, BLACK, BLACK);
      RGB y = new RGB(BLACK, BLACK, BLACK);
      RGB z = new RGB(BLACK, BLACK, BLACK);

      assertTrue("First transitive condition failed", x.equals(y));
      assertTrue("Second transitive condition failed", y.equals(z));
      assertTrue("Transitive property failed", x.equals(z));
    }

    @Test
    public void testEqualsConsistent() {
      RGB x = new RGB(BLACK, BLACK, BLACK);
      RGB y = new RGB(BLACK, BLACK, BLACK);

      boolean firstResult = x.equals(y);
      for (int i = 0; i < 5; i++) {
        assertEquals("Consistent property failed on iteration " + i,
                firstResult, x.equals(y));
      }
    }

    @Test
    public void testEqualsNullComparison() {
      RGB x = new RGB(BLACK, BLACK, BLACK);
      assertFalse("Null comparison failed", x.equals(null));
    }

    @Test
    public void testEqualsDifferentTypes() {
      RGB x = new RGB(BLACK, BLACK, BLACK);
      Object nonPixel = "Not a pixel";
      assertFalse("Different type comparison failed", x.equals(nonPixel));
    }

    @Test
    public void testAdjustBrightness() {
      Pixel brighterPixel = blackPixel.adjustBrightness(50);
      assertEquals(50, brighterPixel.getRed());
      assertEquals(50, brighterPixel.getGreen());
      assertEquals(50, brighterPixel.getBlue());

      // Adjusting brightness of black pixel by negative value should still be black
      Pixel darkerPixel = blackPixel.adjustBrightness(-50);
      assertEquals(BLACK, darkerPixel.getRed());
      assertEquals(BLACK, darkerPixel.getGreen());
      assertEquals(BLACK, darkerPixel.getBlue());
    }

    @Test
    public void testGetIntensity() {
      Pixel intensity = blackPixel.getIntensity();
      assertEquals(BLACK, intensity.getRed());
      assertEquals(BLACK, intensity.getGreen());
      assertEquals(BLACK, intensity.getBlue());
    }

    @Test
    public void testGetValue() {
      Pixel value = blackPixel.getValue();
      assertEquals(BLACK, value.getRed());
      assertEquals(BLACK, value.getGreen());
      assertEquals(BLACK, value.getBlue());
    }

    @Test
    public void testGetLuma() {
      Pixel luma = blackPixel.getLuma();
      assertEquals(BLACK, luma.getRed());
      assertEquals(BLACK, luma.getGreen());
      assertEquals(BLACK, luma.getBlue());
    }

    @Test
    public void testGetSepia() {
      // Black pixel should remain black even after sepia transformation
      Pixel sepia = blackPixel.getSepia();
      assertEquals(BLACK, sepia.getRed());
      assertEquals(BLACK, sepia.getGreen());
      assertEquals(BLACK, sepia.getBlue());
    }
  }

  public static class RGBTest {
    private final int RED = 100;
    private final int GREEN = 150;
    private final int BLUE = 200;
    private RGB pixel;

    @Before
    public void setUp() {
      pixel = new RGB(RED, GREEN, BLUE);
    }

    @Test
    public void testConstructor() {
      assertEquals(RED, pixel.getRed());
      assertEquals(GREEN, pixel.getGreen());
      assertEquals(BLUE, pixel.getBlue());
    }

    @Test
    public void testConstructorWithNegativeValues() {
      RGB pixel = new RGB(-10, -20, -30);
      assertEquals(0, pixel.getRed());
      assertEquals(0, pixel.getGreen());
      assertEquals(0, pixel.getBlue());
    }

    @Test
    public void testConstructorWithOverflowValues() {
      RGB pixel = new RGB(300, 400, 500);
      assertEquals(255, pixel.getRed());
      assertEquals(255, pixel.getGreen());
      assertEquals(255, pixel.getBlue());
    }

    @Test
    public void testCreatePixel() {
      Pixel newPixel = pixel.createPixel(50, 100, 150);
      assertTrue(newPixel instanceof RGB);
      assertEquals(50, newPixel.getRed());
      assertEquals(100, newPixel.getGreen());
      assertEquals(150, newPixel.getBlue());
    }

    @Test
    public void testCreateRedComponent() {
      Pixel redComponent = pixel.createRedComponent();
      assertEquals(RED, redComponent.getRed());
      assertEquals(RED, redComponent.getGreen());
      assertEquals(RED, redComponent.getBlue());
    }

    @Test
    public void testCreateGreenComponent() {
      Pixel greenComponent = pixel.createGreenComponent();
      assertEquals(GREEN, greenComponent.getRed());
      assertEquals(GREEN, greenComponent.getGreen());
      assertEquals(GREEN, greenComponent.getBlue());
    }

    @Test
    public void testCreateBlueComponent() {
      Pixel blueComponent = pixel.createBlueComponent();
      assertEquals(BLUE, blueComponent.getRed());
      assertEquals(BLUE, blueComponent.getGreen());
      assertEquals(BLUE, blueComponent.getBlue());
    }

    @Test
    public void testEquals() {
      RGB samePixel = new RGB(RED, GREEN, BLUE);
      RGB differentPixel = new RGB(RED + 1, GREEN, BLUE);

      assertTrue(pixel.equals(pixel)); // Same object
      assertTrue(pixel.equals(samePixel)); // Equal values
      assertFalse(pixel.equals(differentPixel)); // Different values
      assertFalse(pixel.equals(null)); // Null comparison
      assertFalse(pixel.equals("Not a pixel")); // Different type
    }

    @Test
    public void testEqualsReflexive() {
      // Reflexive: x.equals(x) should return true
      RGB x = new RGB(100, 150, 200);
      assertTrue(x.equals(x));
    }

    @Test
    public void testEqualsSymmetric() {
      // Symmetric: x.equals(y) should return true if and only if y.equals(x) returns true
      RGB x = new RGB(100, 150, 200);
      RGB y = new RGB(100, 150, 200);

      assertTrue(x.equals(y));
      assertTrue(y.equals(x));
    }

    @Test
    public void testEqualsTransitive() {
      // Transitive: if x.equals(y) and y.equals(z), then x.equals(z)
      RGB x = new RGB(100, 150, 200);
      RGB y = new RGB(100, 150, 200);
      RGB z = new RGB(100, 150, 200);

      assertTrue(x.equals(y));
      assertTrue(y.equals(z));
      assertTrue(x.equals(z));
    }

    @Test
    public void testAdjustBrightness() {
      Pixel brighterPixel = pixel.adjustBrightness(50);
      assertEquals(RED + 50, brighterPixel.getRed());
      assertEquals(GREEN + 50, brighterPixel.getGreen());
      assertEquals(BLUE + 50, brighterPixel.getBlue());

      Pixel darkerPixel = pixel.adjustBrightness(-50);
      assertEquals(RED - 50, darkerPixel.getRed());
      assertEquals(GREEN - 50, darkerPixel.getGreen());
      assertEquals(BLUE - 50, darkerPixel.getBlue());
    }

    @Test
    public void testGetIntensity() {
      int expectedIntensity = (RED + GREEN + BLUE) / 3;
      Pixel intensity = pixel.getIntensity();
      assertEquals(expectedIntensity, intensity.getRed());
      assertEquals(expectedIntensity, intensity.getGreen());
      assertEquals(expectedIntensity, intensity.getBlue());
    }

    @Test
    public void testGetValue() {
      int expectedValue = Math.max(Math.max(RED, GREEN), BLUE);
      Pixel value = pixel.getValue();
      assertEquals(expectedValue, value.getRed());
      assertEquals(expectedValue, value.getGreen());
      assertEquals(expectedValue, value.getBlue());
    }

    @Test
    public void testGetLuma() {
      Pixel luma = pixel.getLuma();
      double[][] lumaKernel = LinearColorTransformationType.LUMA.getKernel();

      int expectedRed = (int) (lumaKernel[0][0] * RED + lumaKernel[0][1] * GREEN + lumaKernel[0][2] * BLUE);
      int expectedGreen = (int) (lumaKernel[1][0] * RED + lumaKernel[1][1] * GREEN + lumaKernel[1][2] * BLUE);
      int expectedBlue = (int) (lumaKernel[2][0] * RED + lumaKernel[2][1] * GREEN + lumaKernel[2][2] * BLUE);

      assertEquals(expectedRed, luma.getRed());
      assertEquals(expectedGreen, luma.getGreen());
      assertEquals(expectedBlue, luma.getBlue());
    }

    @Test
    public void testGetSepia() {
      Pixel sepia = pixel.getSepia();
      double[][] sepiaKernel = LinearColorTransformationType.SEPIA.getKernel();

      int expectedRed = (int) (sepiaKernel[0][0] * RED + sepiaKernel[0][1] * GREEN + sepiaKernel[0][2] * BLUE);
      int expectedGreen = (int) (sepiaKernel[1][0] * RED + sepiaKernel[1][1] * GREEN + sepiaKernel[1][2] * BLUE);
      int expectedBlue = (int) (sepiaKernel[2][0] * RED + sepiaKernel[2][1] * GREEN + sepiaKernel[2][2] * BLUE);

      assertEquals(expectedRed, sepia.getRed());
      assertEquals(expectedGreen, sepia.getGreen());
      assertEquals(expectedBlue, sepia.getBlue());
    }

    @Test
    public void testBrightnessAdjustmentClamping() {
      // Test upper bound clamping
      Pixel veryBright = pixel.adjustBrightness(1000);
      assertEquals(255, veryBright.getRed());
      assertEquals(255, veryBright.getGreen());
      assertEquals(255, veryBright.getBlue());

      // Test lower bound clamping
      Pixel veryDark = pixel.adjustBrightness(-1000);
      assertEquals(0, veryDark.getRed());
      assertEquals(0, veryDark.getGreen());
      assertEquals(0, veryDark.getBlue());
    }
  }

  public static class WhitePixelTest {
    private final int WHITE = 255;
    private RGB whitePixel;

    @Before
    public void setUp() {
      whitePixel = new RGB(WHITE, WHITE, WHITE);
    }

    @Test
    public void testConstructor() {
      assertEquals(WHITE, whitePixel.getRed());
      assertEquals(WHITE, whitePixel.getGreen());
      assertEquals(WHITE, whitePixel.getBlue());
    }

    @Test
    public void testCreatePixel() {
      Pixel newPixel = whitePixel.createPixel(255, 255, 255);
      assertTrue(newPixel instanceof RGB);
      assertEquals(WHITE, newPixel.getRed());
      assertEquals(WHITE, newPixel.getGreen());
      assertEquals(WHITE, newPixel.getBlue());
    }

    @Test
    public void testCreateRedComponent() {
      Pixel redComponent = whitePixel.createRedComponent();
      assertEquals(WHITE, redComponent.getRed());
      assertEquals(WHITE, redComponent.getGreen());
      assertEquals(WHITE, redComponent.getBlue());
    }

    @Test
    public void testCreateGreenComponent() {
      Pixel greenComponent = whitePixel.createGreenComponent();
      assertEquals(WHITE, greenComponent.getRed());
      assertEquals(WHITE, greenComponent.getGreen());
      assertEquals(WHITE, greenComponent.getBlue());
    }

    @Test
    public void testCreateBlueComponent() {
      Pixel blueComponent = whitePixel.createBlueComponent();
      assertEquals(WHITE, blueComponent.getRed());
      assertEquals(WHITE, blueComponent.getGreen());
      assertEquals(WHITE, blueComponent.getBlue());
    }

    @Test
    public void testEqualsReflexive() {
      RGB x = new RGB(WHITE, WHITE, WHITE);
      assertTrue("Reflexive property failed", x.equals(x));
    }

    @Test
    public void testEqualsSymmetric() {
      RGB x = new RGB(WHITE, WHITE, WHITE);
      RGB y = new RGB(WHITE, WHITE, WHITE);

      assertTrue("Forward symmetric test failed", x.equals(y));
      assertTrue("Backward symmetric test failed", y.equals(x));
    }

    @Test
    public void testEqualsTransitive() {
      RGB x = new RGB(WHITE, WHITE, WHITE);
      RGB y = new RGB(WHITE, WHITE, WHITE);
      RGB z = new RGB(WHITE, WHITE, WHITE);

      assertTrue("First transitive condition failed", x.equals(y));
      assertTrue("Second transitive condition failed", y.equals(z));
      assertTrue("Transitive property failed", x.equals(z));
    }

    @Test
    public void testEqualsConsistent() {
      RGB x = new RGB(WHITE, WHITE, WHITE);
      RGB y = new RGB(WHITE, WHITE, WHITE);

      boolean firstResult = x.equals(y);
      for (int i = 0; i < 5; i++) {
        assertEquals("Consistent property failed on iteration " + i,
                firstResult, x.equals(y));
      }
    }

    @Test
    public void testEqualsNullComparison() {
      RGB x = new RGB(WHITE, WHITE, WHITE);
      assertFalse("Null comparison failed", x.equals(null));
    }

    @Test
    public void testEqualsDifferentTypes() {
      RGB x = new RGB(WHITE, WHITE, WHITE);
      Object nonPixel = "Not a pixel";
      assertFalse("Different type comparison failed", x.equals(nonPixel));
    }

    @Test
    public void testAdjustBrightness() {
      // White pixel with increased brightness should remain white
      Pixel brighterPixel = whitePixel.adjustBrightness(50);
      assertEquals(WHITE, brighterPixel.getRed());
      assertEquals(WHITE, brighterPixel.getGreen());
      assertEquals(WHITE, brighterPixel.getBlue());

      Pixel darkerPixel = whitePixel.adjustBrightness(-50);
      assertEquals(WHITE - 50, darkerPixel.getRed());
      assertEquals(WHITE - 50, darkerPixel.getGreen());
      assertEquals(WHITE - 50, darkerPixel.getBlue());
    }

    @Test
    public void testGetIntensity() {
      Pixel intensity = whitePixel.getIntensity();
      assertEquals(WHITE, intensity.getRed());
      assertEquals(WHITE, intensity.getGreen());
      assertEquals(WHITE, intensity.getBlue());
    }

    @Test
    public void testGetValue() {
      Pixel value = whitePixel.getValue();
      assertEquals(WHITE, value.getRed());
      assertEquals(WHITE, value.getGreen());
      assertEquals(WHITE, value.getBlue());
    }

    @Test
    public void testGetLuma() {
      Pixel luma = whitePixel.getLuma();
      // White pixel should remain white after luma transformation
      // Because 0.2126 + 0.7152 + 0.0722 = 1.0
      assertEquals(254, luma.getRed());
      assertEquals(254, luma.getGreen());
      assertEquals(254, luma.getBlue());
    }

    @Test
    public void testGetSepia() {
      Pixel sepia = whitePixel.getSepia();
      double[][] sepiaKernel = LinearColorTransformationType.SEPIA.getKernel();

      // For white pixel, all channels are 255, so we can calculate expected values
      int expectedRed = (int) ((sepiaKernel[0][0] + sepiaKernel[0][1] + sepiaKernel[0][2]) * WHITE);
      int expectedGreen = (int) ((sepiaKernel[1][0] + sepiaKernel[1][1] + sepiaKernel[1][2]) * WHITE);
      int expectedBlue = (int) ((sepiaKernel[2][0] + sepiaKernel[2][1] + sepiaKernel[2][2]) * WHITE);

      // Values should be clamped to 255 if they exceed it
      expectedRed = Math.min(255, expectedRed);
      expectedGreen = Math.min(255, expectedGreen);
      expectedBlue = Math.min(255, expectedBlue);

      assertEquals(expectedRed, sepia.getRed());
      assertEquals(expectedGreen, sepia.getGreen());
      assertEquals(expectedBlue, sepia.getBlue());
    }
  }

  public static class ImageTypeTest {

    @Test
    public void testFromExtension_png() {
      ImageType imageType = ImageType.fromExtension("png");
      assertEquals(ImageType.PNG, imageType);
    }

    @Test
    public void testFromExtension_ppm() {
      ImageType imageType = ImageType.fromExtension("ppm");
      assertEquals(ImageType.PPM, imageType);
    }

    @Test
    public void testFromExtension_jpg() {
      ImageType imageType = ImageType.fromExtension("jpg");
      assertEquals(ImageType.JPG, imageType);
    }

    @Test
    public void testFromExtension_jpeg() {
      ImageType imageType = ImageType.fromExtension("jpeg");
      assertEquals(ImageType.JPEG, imageType);
    }

    @Test(expected = ImageProcessingRunTimeException.NotImplementedException.class)
    public void testFromExtension_unsupported() {
      ImageType.fromExtension("gif");
    }

    @Test
    public void testGetExtension() {
      assertEquals("png", ImageType.PNG.getExtension());
      assertEquals("ppm", ImageType.PPM.getExtension());
      assertEquals("jpg", ImageType.JPG.getExtension());
      assertEquals("jpeg", ImageType.JPEG.getExtension());
    }
  }

  public static class LinearColorTransformationTypeTest {
    @Test
    public void testLumaKernel() {
      double[][] expectedLumaKernel = {
              {0.2126, 0.7152, 0.0722},
              {0.2126, 0.7152, 0.0722},
              {0.2126, 0.7152, 0.0722}
      };
      assertArrayEquals(expectedLumaKernel, LinearColorTransformationType.LUMA.getKernel());
    }

    @Test
    public void testSepiaKernel() {
      double[][] expectedSepiaKernel = {
              {0.393, 0.769, 0.189},
              {0.349, 0.686, 0.168},
              {0.272, 0.534, 0.131}
      };
      assertArrayEquals(expectedSepiaKernel, LinearColorTransformationType.SEPIA.getKernel());
    }
  }

  public static class PixelTypeTest {

    @Test
    public void testFromBufferedImageTypeSupportedTypes() {
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_INT_RGB));
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_INT_ARGB));
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_INT_ARGB_PRE));
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_INT_BGR));
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_3BYTE_BGR));
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_4BYTE_ABGR));
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_4BYTE_ABGR_PRE));
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_USHORT_565_RGB));
      assertEquals(PixelType.RGB, PixelType.fromBufferedImageType(BufferedImage.TYPE_USHORT_555_RGB));
    }

    @Test(expected = ImageProcessingRunTimeException.NotImplementedException.class)
    public void testFromBufferedImageTypeUnsupportedType() {
      PixelType.fromBufferedImageType(BufferedImage.TYPE_BYTE_BINARY);
    }

    @Test(expected = ImageProcessingRunTimeException.NotImplementedException.class)
    public void testFromBufferedImageTypeInvalidType() {
      PixelType.fromBufferedImageType(-1);
    }
  }

  public static class UserCommandTest {
    @Test
    public void testGetCommand() {
      assertEquals(Optional.of(UserCommand.LOAD), UserCommand.getCommand("load"));
      assertEquals(Optional.of(UserCommand.SAVE), UserCommand.getCommand("save"));
      assertEquals(Optional.of(UserCommand.RED_COMPONENT), UserCommand.getCommand("red-component"));
      assertEquals(Optional.of(UserCommand.GREEN_COMPONENT), UserCommand.getCommand("green-component"));
      assertEquals(Optional.of(UserCommand.BLUE_COMPONENT), UserCommand.getCommand("blue-component"));
      assertEquals(Optional.of(UserCommand.VALUE_COMPONENT), UserCommand.getCommand("value-component"));
      assertEquals(Optional.of(UserCommand.LUMA_COMPONENT), UserCommand.getCommand("luma-component"));
      assertEquals(Optional.of(UserCommand.INTENSITY_COMPONENT), UserCommand.getCommand("intensity-component"));
      assertEquals(Optional.of(UserCommand.HORIZONTAL_FLIP), UserCommand.getCommand("horizontal-flip"));
      assertEquals(Optional.of(UserCommand.VERTICAL_FLIP), UserCommand.getCommand("vertical-flip"));
      assertEquals(Optional.of(UserCommand.BRIGHTEN), UserCommand.getCommand("brighten"));
      assertEquals(Optional.of(UserCommand.RGB_SPLIT), UserCommand.getCommand("rgb-split"));
      assertEquals(Optional.of(UserCommand.RGB_COMBINE), UserCommand.getCommand("rgb-combine"));
      assertEquals(Optional.of(UserCommand.BLUR), UserCommand.getCommand("blur"));
      assertEquals(Optional.of(UserCommand.SHARPEN), UserCommand.getCommand("sharpen"));
      assertEquals(Optional.of(UserCommand.SEPIA), UserCommand.getCommand("sepia"));
      assertEquals(Optional.of(UserCommand.RUN), UserCommand.getCommand("run"));
      assertEquals(Optional.of(UserCommand.QUIT), UserCommand.getCommand("quit"));
      assertEquals(Optional.of(UserCommand.HELP), UserCommand.getCommand("help"));
      assertEquals(Optional.empty(), UserCommand.getCommand("non-existent-command"));
    }

    @Test
    public void testGetUserCommands() {
      String expectedCommands = "load image-path image-name: Load an image from the specified path and refer it to henceforth in the program by the given image name.\n" +
              "save image-path image-name: Save the image with the given name to the specified path which should include the name of the file.\n" +
              "red-component image-name dest-image-name: Create an image with the red-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
              "green-component image-name dest-image-name: Create an image with the green-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
              "blue-component image-name dest-image-name: Create an image with the blue-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
              "value-component image-name dest-image-name: Create an image with the value-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
              "luma-component image-name dest-image-name: Create an image with the luma-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
              "intensity-component image-name dest-image-name: Create an image with the intensity-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
              "horizontal-flip image-name dest-image-name: Flip an image horizontally to create a new image, referred to henceforth by the given destination name.\n" +
              "vertical-flip image-name dest-image-name: Flip an image vertically to create a new image, referred to henceforth by the given destination name.\n" +
              "brighten increment image-name dest-image-name: brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).\n" +
              "rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue: split the given image into three images containing its red, green and blue components respectively. These would be the same images that would be individually produced with the red-component, green-component and blue-component commands.\n" +
              "rgb-combine image-name red-image green-image blue-image: Combine the three images that are individually red, green and blue into a single image that gets its red, green and blue components from the three images respectively.\n" +
              "blur image-name dest-image-name: blur the given image and store the result in another image with the given name.\n" +
              "sharpen image-name dest-image-name: sharpen the given image and store the result in another image with the given name.\n" +
              "sepia image-name dest-image-name: produce a sepia-toned version of the given image and store the result in another image with the given name.\n" +
              "run script-file: Load and run the script commands in the specified file.\n" +
              "quit: Quit the program.\n" +
              "help: Print this help message.\n";

      assertEquals(expectedCommands, UserCommand.getUserCommands());
    }

  }

  public static class RenderedImageBlackTest {
    private RenderedImage blackImage;
    private Pixel[][] blackPixels;

    @Before
    public void setUp() {
      // Create a 2x2 completely black image
      blackPixels = new Pixel[2][2];
      blackPixels[0][0] = new RGB(0, 0, 0);
      blackPixels[0][1] = new RGB(0, 0, 0);
      blackPixels[1][0] = new RGB(0, 0, 0);
      blackPixels[1][1] = new RGB(0, 0, 0);
      blackImage = new RenderedImage(blackPixels);
    }

    @Test
    public void testConstructor() {
      assertNotNull("Black image should be created successfully", blackImage);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNull() {
      new RenderedImage(null);
    }

    @Test
    public void testGetPixel() {
      assertEquals(new RGB(0, 0, 0), blackImage.getPixel(0, 0));
      assertEquals(new RGB(0, 0, 0), blackImage.getPixel(0, 1));
      assertEquals(new RGB(0, 0, 0), blackImage.getPixel(1, 0));
      assertEquals(new RGB(0, 0, 0), blackImage.getPixel(1, 1));
    }

    @Test
    public void testGetWidth() {
      assertEquals(2, blackImage.getWidth());
    }

    @Test
    public void testGetHeight() {
      assertEquals(2, blackImage.getHeight());
    }

    @Test
    public void testCreateRedComponent() {
      Image redImage = blackImage.createRedComponent();
      // Red component of a black image should remain black
      assertEquals(new RGB(0, 0, 0), redImage.getPixel(0, 0));
      assertEquals(new RGB(0, 0, 0), redImage.getPixel(0, 1));
    }

    @Test
    public void testCreateGreenComponent() {
      Image greenImage = blackImage.createGreenComponent();
      // Green component of a black image should remain black
      assertEquals(new RGB(0, 0, 0), greenImage.getPixel(0, 0));
      assertEquals(new RGB(0, 0, 0), greenImage.getPixel(0, 1));
    }

    @Test
    public void testCreateBlueComponent() {
      Image blueImage = blackImage.createBlueComponent();
      // Blue component of a black image should remain black
      assertEquals(new RGB(0, 0, 0), blueImage.getPixel(0, 0));
      assertEquals(new RGB(0, 0, 0), blueImage.getPixel(0, 1));
    }

    @Test
    public void testAdjustImageBrightness() {
      // Test brightening a black image
      Image brightenedImage = blackImage.adjustImageBrightness(50);
      assertEquals(new RGB(50, 50, 50), brightenedImage.getPixel(0, 0));

      // Test darkening a black image (it should remain black)
      Image darkenedImage = blackImage.adjustImageBrightness(-50);
      assertEquals(new RGB(0, 0, 0), darkenedImage.getPixel(0, 0));
    }

    @Test
    public void testGetLuma() {
      Image lumaImage = blackImage.getLuma();
      // Luma of a black image should remain black
      for (int x = 0; x < blackImage.getWidth(); x++) {
        for (int y = 0; y < blackImage.getHeight(); y++) {
          assertEquals(new RGB(0, 0, 0), lumaImage.getPixel(x, y));
        }
      }
    }

    @Test
    public void testGetSepia() {
      Image sepiaImage = blackImage.getSepia();
      // Sepia transformation of a black image should remain black
      for (int x = 0; x < blackImage.getWidth(); x++) {
        for (int y = 0; y < blackImage.getHeight(); y++) {
          assertEquals(new RGB(0, 0, 0), sepiaImage.getPixel(x, y));
        }
      }
    }

    @Test
    public void testGetIntensity() {
      Image intensityImage = blackImage.getIntensity();
      // Intensity of a black image should remain black
      for (int x = 0; x < blackImage.getWidth(); x++) {
        for (int y = 0; y < blackImage.getHeight(); y++) {
          assertEquals(new RGB(0, 0, 0), intensityImage.getPixel(x, y));
        }
      }
    }

    @Test
    public void testGetValue() {
      Image valueImage = blackImage.getValue();
      // Value of a black image should remain black
      for (int x = 0; x < blackImage.getWidth(); x++) {
        for (int y = 0; y < blackImage.getHeight(); y++) {
          assertEquals(new RGB(0, 0, 0), valueImage.getPixel(x, y));
        }
      }
    }

    @Test
    public void testHorizontalFlip() {
      Image flippedImage = blackImage.horizontalFlip();
      // Black image flipped horizontally should remain the same
      assertEquals(blackImage.getPixel(0, 0), flippedImage.getPixel(1, 0));
      assertEquals(blackImage.getPixel(1, 0), flippedImage.getPixel(0, 0));
      assertEquals(blackImage.getPixel(0, 1), flippedImage.getPixel(1, 1));
      assertEquals(blackImage.getPixel(1, 1), flippedImage.getPixel(0, 1));
    }

    @Test
    public void testVerticalFlip() {
      Image flippedImage = blackImage.verticalFlip();
      // Black image flipped vertically should remain the same
      assertEquals(blackImage.getPixel(0, 0), flippedImage.getPixel(0, 1));
      assertEquals(blackImage.getPixel(0, 1), flippedImage.getPixel(0, 0));
      assertEquals(blackImage.getPixel(1, 0), flippedImage.getPixel(1, 1));
      assertEquals(blackImage.getPixel(1, 1), flippedImage.getPixel(1, 0));
    }

    @Test
    public void testEdgeCases() {
      // Test brightness adjustment clamping on a black image
      Image overBrightened = blackImage.adjustImageBrightness(300);
      assertTrue(overBrightened.getPixel(0, 0).getRed() <= 255);

      Image overDarkened = blackImage.adjustImageBrightness(-300);
      assertTrue(overDarkened.getPixel(0, 0).getRed() >= 0);
    }
  }

  public static class RenderedImageRectangleTest {
    private RenderedImage image;
    private Pixel[][] pixels;

    @Before
    public void setUp() {
      // Create a 3x2 test image with different RGB values
      pixels = new Pixel[3][2];
      pixels[0][0] = new RGB(100, 150, 200); // top-left
      pixels[0][1] = new RGB(50, 100, 150);  // bottom-left
      pixels[1][0] = new RGB(200, 100, 50);  // top-center
      pixels[1][1] = new RGB(150, 200, 100); // bottom-center
      pixels[2][0] = new RGB(25, 75, 125);   // top-right
      pixels[2][1] = new RGB(75, 125, 175);  // bottom-right
      image = new RenderedImage(pixels);
    }

    @Test
    public void testConstructor() {
      assertNotNull("Image should be created successfully", image);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNull() {
      new RenderedImage(null);
    }

    @Test
    public void testGetPixel() {
      assertEquals(new RGB(100, 150, 200), image.getPixel(0, 0));
      assertEquals(new RGB(50, 100, 150), image.getPixel(0, 1));
      assertEquals(new RGB(200, 100, 50), image.getPixel(1, 0));
      assertEquals(new RGB(150, 200, 100), image.getPixel(1, 1));
      assertEquals(new RGB(25, 75, 125), image.getPixel(2, 0));
      assertEquals(new RGB(75, 125, 175), image.getPixel(2, 1));
    }

    @Test
    public void testGetWidth() {
      assertEquals(3, image.getWidth());
    }

    @Test
    public void testGetHeight() {
      assertEquals(2, image.getHeight());
    }

    @Test
    public void testCreateRedComponent() {
      Image redImage = image.createRedComponent();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          Pixel redPixel = redImage.getPixel(x, y);
          assertEquals(redPixel.getRed(), original.getRed());
          assertEquals(redPixel.getGreen(), original.getRed());
          assertEquals(redPixel.getBlue(), original.getRed());
        }
      }
    }

    @Test
    public void testCreateGreenComponent() {
      Image greenImage = image.createGreenComponent();
      // Check green component preservation and other components zeroed
      assertEquals(new RGB(150, 150, 150), greenImage.getPixel(0, 0));
      assertEquals(new RGB(100, 100, 100), greenImage.getPixel(0, 1));
    }

    @Test
    public void testCreateBlueComponent() {
      Image blueImage = image.createBlueComponent();
      // Check blue component preservation and other components zeroed
      assertEquals(new RGB(200, 200, 200), blueImage.getPixel(0, 0));
      assertEquals(new RGB(150, 150, 150), blueImage.getPixel(0, 1));
    }

    @Test
    public void testAdjustImageBrightness() {
      // Test brightening
      Image brightenedImage = image.adjustImageBrightness(50);
      assertEquals(new RGB(150, 200, 250), brightenedImage.getPixel(0, 0));

      // Test darkening
      Image darkenedImage = image.adjustImageBrightness(-50);
      assertEquals(new RGB(50, 100, 150), darkenedImage.getPixel(0, 0));
    }

    @Test
    public void testGetLuma() {
      Image lumaImage = image.getLuma();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          int expectedLuma = (int) (0.2126 * original.getRed() +
                  0.7152 * original.getGreen() +
                  0.0722 * original.getBlue());
          Pixel lumaPixel = lumaImage.getPixel(x, y);

          assertEquals(new RGB(expectedLuma, expectedLuma, expectedLuma), lumaPixel);
        }
      }
    }

    @Test
    public void testGetSepia() {
      Image sepiaImage = image.getSepia();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          int originalRed = original.getRed();
          int originalGreen = original.getGreen();
          int originalBlue = original.getBlue();

          int expectedRed = Math.min(255, (int) (0.393 * originalRed +
                  0.769 * originalGreen +
                  0.189 * originalBlue));
          int expectedGreen = Math.min(255, (int) (0.349 * originalRed +
                  0.686 * originalGreen +
                  0.168 * originalBlue));
          int expectedBlue = Math.min(255, (int) (0.272 * originalRed +
                  0.534 * originalGreen +
                  0.131 * originalBlue));

          Pixel sepiaPixel = sepiaImage.getPixel(x, y);

          assertEquals(new RGB(expectedRed, expectedGreen, expectedBlue), sepiaPixel);
        }
      }
    }

    @Test
    public void testGetIntensity() {
      Image intensityImage = image.getIntensity();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          int expectedIntensity = (original.getRed() +
                  original.getGreen() +
                  original.getBlue()) / 3;

          Pixel intensityPixel = intensityImage.getPixel(x, y);

          assertEquals(new RGB(expectedIntensity, expectedIntensity, expectedIntensity),
                  intensityPixel);
        }
      }
    }

    @Test
    public void testGetValue() {
      Image valueImage = image.getValue();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          int expectedValue = Math.max(Math.max(original.getRed(),
                          original.getGreen()),
                  original.getBlue());

          Pixel valuePixel = valueImage.getPixel(x, y);

          assertEquals(new RGB(expectedValue, expectedValue, expectedValue),
                  valuePixel);
        }
      }
    }

    @Test
    public void testHorizontalFlip() {
      Image flippedImage = image.horizontalFlip();
      // Verify pixels are flipped horizontally
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          assertEquals(image.getPixel(x, y), flippedImage.getPixel(image.getWidth() - 1 - x, y));
        }
      }

      //check dimensions
      assertEquals(image.getWidth(), flippedImage.getWidth());
      assertEquals(image.getHeight(), flippedImage.getHeight());
    }

    @Test
    public void testVerticalFlip() {
      Image flippedImage = image.verticalFlip();
      // Verify pixels are flipped vertically
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          assertEquals(image.getPixel(x, y), flippedImage.getPixel(x, image.getHeight() - 1 - y));
        }
      }

      //check dimensions
      assertEquals(image.getWidth(), flippedImage.getWidth());
      assertEquals(image.getHeight(), flippedImage.getHeight());
    }

    @Test
    public void testEdgeCases() {
      // Test 1x1 image
      Pixel[][] singlePixel = new Pixel[][]{{new RGB(100, 100, 100)}};
      RenderedImage smallImage = new RenderedImage(singlePixel);
      assertEquals(1, smallImage.getWidth());
      assertEquals(1, smallImage.getHeight());

      // Test brightness adjustment clamping
      Image overBrightened = image.adjustImageBrightness(300);
      assertTrue(overBrightened.getPixel(0, 0).getRed() <= 255);

      Image overDarkened = image.adjustImageBrightness(-300);
      assertTrue(overDarkened.getPixel(0, 0).getRed() >= 0);
    }

  }

  public static class RenderedImageTest {
    private RenderedImage image;
    private Pixel[][] pixels;

    @Before
    public void setUp() {
      // Create a 2x2 test image with different RGB values
      pixels = new Pixel[2][2];
      pixels[0][0] = new RGB(100, 150, 200); // top-left
      pixels[0][1] = new RGB(50, 100, 150);  // bottom-left
      pixels[1][0] = new RGB(200, 100, 50);  // top-right
      pixels[1][1] = new RGB(150, 200, 100); // bottom-right
      image = new RenderedImage(pixels);
    }

    @Test
    public void testConstructor() {
      assertNotNull("Image should be created successfully", image);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNull() {
      new RenderedImage(null);
    }

    @Test
    public void testGetPixel() {
      assertEquals(new RGB(100, 150, 200), image.getPixel(0, 0));
      assertEquals(new RGB(50, 100, 150), image.getPixel(0, 1));
      assertEquals(new RGB(200, 100, 50), image.getPixel(1, 0));
      assertEquals(new RGB(150, 200, 100), image.getPixel(1, 1));
    }

    @Test
    public void testGetWidth() {
      assertEquals(2, image.getWidth());
    }

    @Test
    public void testGetHeight() {
      assertEquals(2, image.getHeight());
    }

    @Test
    public void testCreateRedComponent() {
      Image redImage = image.createRedComponent();
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          Pixel redPixel = redImage.getPixel(x, y);
          assertEquals(redPixel.getRed(), original.getRed());
          assertEquals(redPixel.getGreen(), original.getRed());
          assertEquals(redPixel.getBlue(), original.getRed());
        }
      }
    }

    @Test
    public void testCreateGreenComponent() {
      Image greenImage = image.createGreenComponent();
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          Pixel green = greenImage.getPixel(x, y);
          assertEquals(green.getRed(), original.getGreen());
          assertEquals(green.getGreen(), original.getGreen());
          assertEquals(green.getBlue(), original.getGreen());
        }
      }
    }

    @Test
    public void testCreateBlueComponent() {
      Image blueImage = image.createBlueComponent();
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          Pixel blue = blueImage.getPixel(x, y);
          assertEquals(blue.getRed(), original.getBlue());
          assertEquals(blue.getGreen(), original.getBlue());
          assertEquals(blue.getBlue(), original.getBlue());
        }
      }
    }

    @Test
    public void testAdjustImageBrightness() {
      // Test brightening
      Image brightenedImage = image.adjustImageBrightness(50);
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          Pixel brightened = brightenedImage.getPixel(x, y);
          assertEquals(Math.min(255, original.getRed() + 50), brightened.getRed());
          assertEquals(Math.min(255, original.getGreen() + 50), brightened.getGreen());
          assertEquals(Math.min(255, original.getBlue() + 50), brightened.getBlue());
        }
      }

      // Test darkening
      Image darkenedImage = image.adjustImageBrightness(-50);
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          Pixel darkened = darkenedImage.getPixel(x, y);
          assertEquals(Math.max(0, original.getRed() - 50), darkened.getRed());
          assertEquals(Math.max(0, original.getGreen() - 50), darkened.getGreen());
          assertEquals(Math.max(0, original.getBlue() - 50), darkened.getBlue());
        }
      }
    }

    @Test
    public void testGetLuma() {
      Image lumaImage = image.getLuma();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          int expectedLuma = (int) (0.2126 * original.getRed() +
                  0.7152 * original.getGreen() +
                  0.0722 * original.getBlue());
          Pixel lumaPixel = lumaImage.getPixel(x, y);

          assertEquals(new RGB(expectedLuma, expectedLuma, expectedLuma), lumaPixel);
        }
      }
    }

    @Test
    public void testGetSepia() {
      Image sepiaImage = image.getSepia();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          int originalRed = original.getRed();
          int originalGreen = original.getGreen();
          int originalBlue = original.getBlue();

          int expectedRed = Math.min(255, (int) (0.393 * originalRed +
                  0.769 * originalGreen +
                  0.189 * originalBlue));
          int expectedGreen = Math.min(255, (int) (0.349 * originalRed +
                  0.686 * originalGreen +
                  0.168 * originalBlue));
          int expectedBlue = Math.min(255, (int) (0.272 * originalRed +
                  0.534 * originalGreen +
                  0.131 * originalBlue));

          Pixel sepiaPixel = sepiaImage.getPixel(x, y);

          assertEquals(new RGB(expectedRed, expectedGreen, expectedBlue), sepiaPixel);
        }
      }
    }

    @Test
    public void testGetIntensity() {
      Image intensityImage = image.getIntensity();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          int expectedIntensity = (original.getRed() +
                  original.getGreen() +
                  original.getBlue()) / 3;

          Pixel intensityPixel = intensityImage.getPixel(x, y);

          assertEquals(new RGB(expectedIntensity, expectedIntensity, expectedIntensity),
                  intensityPixel);
        }
      }
    }

    @Test
    public void testGetValue() {
      Image valueImage = image.getValue();

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          Pixel original = image.getPixel(x, y);
          int expectedValue = Math.max(Math.max(original.getRed(),
                          original.getGreen()),
                  original.getBlue());

          Pixel valuePixel = valueImage.getPixel(x, y);

          assertEquals(new RGB(expectedValue, expectedValue, expectedValue),
                  valuePixel);
        }
      }
    }

    @Test
    public void testHorizontalFlip() {
      Image flippedImage = image.horizontalFlip();
      // Verify pixels are flipped horizontally
      assertEquals(image.getPixel(0, 0), flippedImage.getPixel(1, 0));
      assertEquals(image.getPixel(1, 0), flippedImage.getPixel(0, 0));
      assertEquals(image.getPixel(0, 1), flippedImage.getPixel(1, 1));
      assertEquals(image.getPixel(1, 1), flippedImage.getPixel(0, 1));
    }

    @Test
    public void testVerticalFlip() {
      Image flippedImage = image.verticalFlip();
      // Verify pixels are flipped vertically
      assertEquals(image.getPixel(0, 0), flippedImage.getPixel(0, 1));
      assertEquals(image.getPixel(0, 1), flippedImage.getPixel(0, 0));
      assertEquals(image.getPixel(1, 0), flippedImage.getPixel(1, 1));
      assertEquals(image.getPixel(1, 1), flippedImage.getPixel(1, 0));
    }

    @Test
    public void testEdgeCases() {
      // Test 1x1 image
      Pixel[][] singlePixel = new Pixel[][]{{new RGB(100, 100, 100)}};
      RenderedImage smallImage = new RenderedImage(singlePixel);
      assertEquals(1, smallImage.getWidth());
      assertEquals(1, smallImage.getHeight());

      // Test brightness adjustment clamping
      Image overBrightened = image.adjustImageBrightness(300);
      assertTrue(overBrightened.getPixel(0, 0).getRed() <= 255);

      Image overDarkened = image.adjustImageBrightness(-300);
      assertTrue(overDarkened.getPixel(0, 0).getRed() >= 0);
    }

  }

  public static class RenderedImageWhiteTest {
    private RenderedImage whiteImage;
    private Pixel[][] whitePixels;

    @Before
    public void setUp() {
      // Create a 2x2 completely white image
      whitePixels = new Pixel[2][2];
      whitePixels[0][0] = new RGB(255, 255, 255);
      whitePixels[0][1] = new RGB(255, 255, 255);
      whitePixels[1][0] = new RGB(255, 255, 255);
      whitePixels[1][1] = new RGB(255, 255, 255);
      whiteImage = new RenderedImage(whitePixels);
    }

    @Test
    public void testConstructor() {
      assertNotNull("White image should be created successfully", whiteImage);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNull() {
      new RenderedImage(null);
    }

    @Test
    public void testGetPixel() {
      assertEquals(new RGB(255, 255, 255), whiteImage.getPixel(0, 0));
      assertEquals(new RGB(255, 255, 255), whiteImage.getPixel(0, 1));
      assertEquals(new RGB(255, 255, 255), whiteImage.getPixel(1, 0));
      assertEquals(new RGB(255, 255, 255), whiteImage.getPixel(1, 1));
    }

    @Test
    public void testGetWidth() {
      assertEquals(2, whiteImage.getWidth());
    }

    @Test
    public void testGetHeight() {
      assertEquals(2, whiteImage.getHeight());
    }

    @Test
    public void testCreateRedComponent() {
      Image redImage = whiteImage.createRedComponent();
      // Red component of a white image should remain white
      assertEquals(new RGB(255, 255, 255), redImage.getPixel(0, 0));
      assertEquals(new RGB(255, 255, 255), redImage.getPixel(0, 1));
    }

    @Test
    public void testCreateGreenComponent() {
      Image greenImage = whiteImage.createGreenComponent();
      // Green component of a white image should remain white
      assertEquals(new RGB(255, 255, 255), greenImage.getPixel(0, 0));
      assertEquals(new RGB(255, 255, 255), greenImage.getPixel(0, 1));
    }

    @Test
    public void testCreateBlueComponent() {
      Image blueImage = whiteImage.createBlueComponent();
      // Blue component of a white image should remain white
      assertEquals(new RGB(255, 255, 255), blueImage.getPixel(0, 0));
      assertEquals(new RGB(255, 255, 255), blueImage.getPixel(0, 1));
    }

    @Test
    public void testAdjustImageBrightness() {
      // Test brightening a white image (it should remain white as it can't go beyond 255)
      Image brightenedImage = whiteImage.adjustImageBrightness(50);
      assertEquals(new RGB(255, 255, 255), brightenedImage.getPixel(0, 0));

      // Test darkening a white image
      Image darkenedImage = whiteImage.adjustImageBrightness(-50);
      assertEquals(new RGB(205, 205, 205), darkenedImage.getPixel(0, 0));
    }

    @Test
    public void testGetLuma() {
      Image lumaImage = whiteImage.getLuma();
      // Luma of a white image should remain white
      for (int x = 0; x < whiteImage.getWidth(); x++) {
        for (int y = 0; y < whiteImage.getHeight(); y++) {
          assertEquals(new RGB(254, 254, 254), lumaImage.getPixel(x, y));
        }
      }
    }

    @Test
    public void testGetSepia() {
      Image sepiaImage = whiteImage.getSepia();
      // Sepia transformation of a white image should result in light sepia tones
      for (int x = 0; x < whiteImage.getWidth(); x++) {
        for (int y = 0; y < whiteImage.getHeight(); y++) {
          assertEquals(new RGB(255, 255, 238), sepiaImage.getPixel(x, y));
        }
      }
    }

    @Test
    public void testGetIntensity() {
      Image intensityImage = whiteImage.getIntensity();
      // Intensity of a white image should remain white
      for (int x = 0; x < whiteImage.getWidth(); x++) {
        for (int y = 0; y < whiteImage.getHeight(); y++) {
          assertEquals(new RGB(255, 255, 255), intensityImage.getPixel(x, y));
        }
      }
    }

    @Test
    public void testGetValue() {
      Image valueImage = whiteImage.getValue();
      // Value of a white image should remain white
      for (int x = 0; x < whiteImage.getWidth(); x++) {
        for (int y = 0; y < whiteImage.getHeight(); y++) {
          assertEquals(new RGB(255, 255, 255), valueImage.getPixel(x, y));
        }
      }
    }

    @Test
    public void testHorizontalFlip() {
      Image flippedImage = whiteImage.horizontalFlip();
      // White image flipped horizontally should remain the same
      assertEquals(whiteImage.getPixel(0, 0), flippedImage.getPixel(1, 0));
      assertEquals(whiteImage.getPixel(1, 0), flippedImage.getPixel(0, 0));
      assertEquals(whiteImage.getPixel(0, 1), flippedImage.getPixel(1, 1));
      assertEquals(whiteImage.getPixel(1, 1), flippedImage.getPixel(0, 1));
    }

    @Test
    public void testVerticalFlip() {
      Image flippedImage = whiteImage.verticalFlip();
      // White image flipped vertically should remain the same
      assertEquals(whiteImage.getPixel(0, 0), flippedImage.getPixel(0, 1));
      assertEquals(whiteImage.getPixel(0, 1), flippedImage.getPixel(0, 0));
      assertEquals(whiteImage.getPixel(1, 0), flippedImage.getPixel(1, 1));
      assertEquals(whiteImage.getPixel(1, 1), flippedImage.getPixel(1, 0));
    }

    @Test
    public void testEdgeCases() {
      // Test brightness adjustment clamping on a white image
      Image overBrightened = whiteImage.adjustImageBrightness(300);
      assertTrue(overBrightened.getPixel(0, 0).getRed() == 255);

      Image overDarkened = whiteImage.adjustImageBrightness(-300);
      assertTrue(overDarkened.getPixel(0, 0).getRed() == 0);
    }
  }

  public static class SinglePixelImageTest {
    private RenderedImage singlePixelImage;
    private Pixel originalPixel;

    @Before
    public void setUp() {
      // Create a single pixel with distinct RGB values for clear testing
      originalPixel = new RGB(120, 180, 240);
      Pixel[][] pixels = new Pixel[][]{{originalPixel}};
      singlePixelImage = new RenderedImage(pixels);
    }

    @Test
    public void testBasicProperties() {
      // Verify dimensions
      assertEquals("Width should be 1", 1, singlePixelImage.getWidth());
      assertEquals("Height should be 1", 1, singlePixelImage.getHeight());

      // Verify pixel retrieval
      Pixel retrievedPixel = singlePixelImage.getPixel(0, 0);
      assertEquals("Original pixel should match retrieved pixel", originalPixel, retrievedPixel);
    }

    @Test
    public void testLuma() {
      Image lumaImage = singlePixelImage.getLuma();

      // Calculate expected luma value
      int expectedLuma = (int) (0.2126 * originalPixel.getRed() +
              0.7152 * originalPixel.getGreen() +
              0.0722 * originalPixel.getBlue());

      Pixel lumaPixel = lumaImage.getPixel(0, 0);

      assertEquals("Luma transformation should match expected value",
              new RGB(expectedLuma, expectedLuma, expectedLuma), lumaPixel);
    }

    @Test
    public void testSepia() {
      Image sepiaImage = singlePixelImage.getSepia();

      // Calculate expected sepia values
      int originalRed = originalPixel.getRed();
      int originalGreen = originalPixel.getGreen();
      int originalBlue = originalPixel.getBlue();

      int expectedRed = Math.min(255, (int) (0.393 * originalRed +
              0.769 * originalGreen +
              0.189 * originalBlue));
      int expectedGreen = Math.min(255, (int) (0.349 * originalRed +
              0.686 * originalGreen +
              0.168 * originalBlue));
      int expectedBlue = Math.min(255, (int) (0.272 * originalRed +
              0.534 * originalGreen +
              0.131 * originalBlue));

      Pixel sepiaPixel = sepiaImage.getPixel(0, 0);

      assertEquals("Sepia transformation should match expected values",
              new RGB(expectedRed, expectedGreen, expectedBlue), sepiaPixel);
    }

    @Test
    public void testIntensity() {
      Image intensityImage = singlePixelImage.getIntensity();

      // Calculate expected intensity
      int expectedIntensity = (originalPixel.getRed() +
              originalPixel.getGreen() +
              originalPixel.getBlue()) / 3;

      Pixel intensityPixel = intensityImage.getPixel(0, 0);

      assertEquals("Intensity transformation should match expected value",
              new RGB(expectedIntensity, expectedIntensity, expectedIntensity), intensityPixel);
    }

    @Test
    public void testValue() {
      Image valueImage = singlePixelImage.getValue();

      // Calculate expected value (maximum of RGB)
      int expectedValue = Math.max(Math.max(originalPixel.getRed(),
                      originalPixel.getGreen()),
              originalPixel.getBlue());

      Pixel valuePixel = valueImage.getPixel(0, 0);

      assertEquals("Value transformation should match expected value",
              new RGB(expectedValue, expectedValue, expectedValue), valuePixel);
    }

    @Test
    public void testColorComponents() {
      // Test Red Component
      Image redImage = singlePixelImage.createRedComponent();
      Pixel redPixel = redImage.getPixel(0, 0);
      assertEquals("Red component should preserve red value only",
              new RGB(originalPixel.getRed(), originalPixel.getRed(), originalPixel.getRed()), redPixel);

      // Test Green Component
      Image greenImage = singlePixelImage.createGreenComponent();
      Pixel greenPixel = greenImage.getPixel(0, 0);
      assertEquals("Green component should preserve green value only",
              new RGB(originalPixel.getGreen(), originalPixel.getGreen(), originalPixel.getGreen()), greenPixel);

      // Test Blue Component
      Image blueImage = singlePixelImage.createBlueComponent();
      Pixel bluePixel = blueImage.getPixel(0, 0);
      assertEquals("Blue component should preserve blue value only",
              new RGB(originalPixel.getBlue(), originalPixel.getBlue(), originalPixel.getBlue()), bluePixel);
    }

    @Test
    public void testBrightnessAdjustment() {
      // Test brightening
      int brightenFactor = 50;
      Image brightenedImage = singlePixelImage.adjustImageBrightness(brightenFactor);
      Pixel brightenedPixel = brightenedImage.getPixel(0, 0);

      assertEquals("Brightened pixel should have increased RGB values",
              new RGB(originalPixel.getRed() + brightenFactor,
                      originalPixel.getGreen() + brightenFactor,
                      originalPixel.getBlue() + brightenFactor), brightenedPixel);

      // Test darkening
      int darkenFactor = -30;
      Image darkenedImage = singlePixelImage.adjustImageBrightness(darkenFactor);
      Pixel darkenedPixel = darkenedImage.getPixel(0, 0);

      assertEquals("Darkened pixel should have decreased RGB values",
              new RGB(originalPixel.getRed() + darkenFactor,
                      originalPixel.getGreen() + darkenFactor,
                      originalPixel.getBlue() + darkenFactor), darkenedPixel);

    }
  }

  /**
   * Test class for applying Blur filter to black images.
   */
  public static class BlurBlackImageTest extends BlurTestBase {
    private Image blackImage;

    @Before
    public void setUp() {
      Pixel[][] pixels = new Pixel[3][3];
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          pixels[i][j] = Factory.createRGBPixel(0, 0, 0);
        }
      }
      blackImage = Factory.createImage(pixels);
    }

    @Test
    public void testBlurBlackImage() {
      Image blurredImage = FilterUtils.applyFilter(blackImage, FilterOption.GAUSSIAN_BLUR);

      Pixel[][] expectedPixels = new Pixel[3][3];
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          expectedPixels[i][j] = Factory.createRGBPixel(0, 0, 0);
        }
      }

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          Pixel resultPixel = blurredImage.getPixel(i, j);
          Pixel expectedPixel = expectedPixels[i][j];
          assertEquals("Blurred image red value should be as expected",
                  expectedPixel.getRed(), resultPixel.getRed());
          assertEquals("Blurred image green value should be as expected",
                  expectedPixel.getGreen(), resultPixel.getGreen());
          assertEquals("Blurred image blue value should be as expected",
                  expectedPixel.getBlue(), resultPixel.getBlue());
        }
      }
    }
  }

  /**
   * Test class for applying Blur filter to normal RGB images.
   */
  public static class BlurNormalImageTest extends BlurTestBase {
    private Image testImage;

    @Before
    public void setUp() {
      Pixel[][] pixels = new Pixel[3][3];
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          pixels[i][j] = Factory.createRGBPixel(100, 150, 200);
        }
      }
      testImage = Factory.createImage(pixels);
    }

    @Test
    public void testBlurUniformImage() {
      Image blurredImage = FilterUtils.applyFilter(testImage, FilterOption.GAUSSIAN_BLUR);
      Pixel[][] expectedPixels = {
              {Factory.createRGBPixel(56, 84, 112), Factory.createRGBPixel(75, 112, 150), Factory.createRGBPixel(56, 84, 112)},
              {Factory.createRGBPixel(75, 112, 150), Factory.createRGBPixel(100, 150, 200), Factory.createRGBPixel(75, 112, 150)},
              {Factory.createRGBPixel(56, 84, 112), Factory.createRGBPixel(75, 112, 150), Factory.createRGBPixel(56, 84, 112)}
      };

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          Pixel resultPixel = blurredImage.getPixel(i, j);
          Pixel expectedPixel = expectedPixels[i][j];
          assertEquals("Pixel red value should be as expected", expectedPixel.getRed(), resultPixel.getRed());
          assertEquals("Pixel green value should be as expected", expectedPixel.getGreen(), resultPixel.getGreen());
          assertEquals("Pixel blue value should be as expected", expectedPixel.getBlue(), resultPixel.getBlue());
        }
      }
    }

    @Test
    public void testImageDimensionsPreserved() {
      Image blurredImage = FilterUtils.applyFilter(testImage, FilterOption.GAUSSIAN_BLUR);
      assertEquals("Image width should be preserved",
              testImage.getWidth(), blurredImage.getWidth());
      assertEquals("Image height should be preserved",
              testImage.getHeight(), blurredImage.getHeight());
    }
  }

  /**
   * Test class for applying Blur filter to single pixel images.
   */
  public static class BlurSinglePixelTest extends BlurTestBase {
    private Image singlePixelImage;

    @Before
    public void setUp() {
      Pixel[][] pixel = new Pixel[1][1];
      pixel[0][0] = Factory.createRGBPixel(100, 100, 100);
      singlePixelImage = Factory.createImage(pixel);
    }

    @Test
    public void testBlurSinglePixel() {
      Image blurredImage = FilterUtils.applyFilter(singlePixelImage, FilterOption.GAUSSIAN_BLUR);
      Pixel resultPixel = blurredImage.getPixel(0, 0);

      assertEquals("Single pixel red value should be affected by kernel",
              25, resultPixel.getRed());
      assertEquals("Single pixel green value should be affected by kernel",
              25, resultPixel.getGreen());
      assertEquals("Single pixel blue value should be affected by kernel",
              25, resultPixel.getBlue());
    }

    @Test
    public void testSinglePixelDimensionsPreserved() {
      Image blurredImage = FilterUtils.applyFilter(singlePixelImage, FilterOption.GAUSSIAN_BLUR);
      assertEquals("Image width should remain 1",
              1, blurredImage.getWidth());
      assertEquals("Image height should remain 1",
              1, blurredImage.getHeight());
    }
  }

  /**
   * Base test class for Blur filter tests containing common setup and utilities.
   */
  public abstract static class BlurTestBase {
    private static final double DELTA = 0.0001;

    @Test
    public void testGaussianKernelValues() {
      double[][] kernel = FilterOption.GAUSSIAN_BLUR.getKernel();
      assertEquals("Kernel should be 3x3", 3, kernel.length);
      assertEquals("Kernel should be 3x3", 3, kernel[0].length);

      assertEquals("Center value should be 1/4", 0.25, kernel[1][1], DELTA);
      assertEquals("Corner value should be 1/16", 0.0625, kernel[0][0], DELTA);
      assertEquals("Edge value should be 1/8", 0.125, kernel[0][1], DELTA);
    }

    @Test(expected = NullPointerException.class)
    public void testApplyFilterWithNullImage() {
      FilterUtils.applyFilter(null, FilterOption.GAUSSIAN_BLUR);
    }
  }

  /**
   * Test class for applying Blur filter to white images.
   */
  public static class BlurWhiteImageTest extends BlurTestBase {
    private Image whiteImage;

    @Before
    public void setUp() {
      Pixel[][] pixels = new Pixel[3][3];
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          pixels[i][j] = Factory.createRGBPixel(255, 255, 255);
        }
      }
      whiteImage = Factory.createImage(pixels);
    }

    @Test
    public void testBlurWhiteImage() {
      Image blurredImage = FilterUtils.applyFilter(whiteImage, FilterOption.GAUSSIAN_BLUR);

      Pixel[][] expectedPixels = {
              {Factory.createRGBPixel(143, 143, 143), Factory.createRGBPixel(191, 191, 191), Factory.createRGBPixel(143, 143, 143)},
              {Factory.createRGBPixel(191, 191, 191), Factory.createRGBPixel(255, 255, 255), Factory.createRGBPixel(191, 191, 191)},
              {Factory.createRGBPixel(143, 143, 143), Factory.createRGBPixel(191, 191, 191), Factory.createRGBPixel(143, 143, 143)}
      };

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          assertEquals(expectedPixels[i][j], blurredImage.getPixel(i, j));
        }
      }
    }
  }

  /**
   * Test class for testing the Sharpen filter functionality on white images.
   */
  public static class SharpenWhiteImageTest {
    protected static final double DELTA = 0.0001;
    private Image whiteImage;

    @Before
    public void setUp() {
      Pixel[][] pixels = new Pixel[5][5];
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          pixels[i][j] = Factory.createRGBPixel(255, 255, 255);
        }
      }
      whiteImage = Factory.createImage(pixels);
    }

    @Test
    public void testSharpenKernelValues() {
      double[][] kernel = FilterOption.SHARPEN.getKernel();
      assertEquals("Kernel should be 5x5", 5, kernel.length);
      assertEquals("Kernel should be 5x5", 5, kernel[0].length);

      assertEquals("Center value should be 1", 1.0, kernel[2][2], DELTA);
      assertEquals("Edge value should be -1/8", -0.125, kernel[0][0], DELTA);
      assertEquals("Near center value should be 1/4", 0.25, kernel[1][2], DELTA);
    }

    @Test(expected = NullPointerException.class)
    public void testApplyFilterWithNullImage() {
      FilterUtils.applyFilter(null, FilterOption.SHARPEN);
    }

    @Test
    public void testSharpenWhiteImage() {
      Image sharpenedImage = FilterUtils.applyFilter(whiteImage, FilterOption.SHARPEN);

      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          assertEquals("Sharpened image should remain white (red)",
                  255, sharpenedImage.getPixel(i, j).getRed());
          assertEquals("Sharpened image should remain white (green)",
                  255, sharpenedImage.getPixel(i, j).getGreen());
          assertEquals("Sharpened image should remain white (blue)",
                  255, sharpenedImage.getPixel(i, j).getBlue());
        }
      }
    }
  }

  /**
   * Test class for testing the Sharpen filter functionality on black images.
   */
  public static class SharpenBlackImageTest {
    private Image blackImage;

    @Before
    public void setUp() {
      Pixel[][] pixels = new Pixel[5][5];
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          pixels[i][j] = Factory.createRGBPixel(0, 0, 0);
        }
      }
      blackImage = Factory.createImage(pixels);
    }

    @Test
    public void testSharpenBlackImage() {
      Image sharpenedImage = FilterUtils.applyFilter(blackImage, FilterOption.SHARPEN);
      Pixel centerPixel = sharpenedImage.getPixel(2, 2);

      assertEquals("Black image should remain black (red)",
              0, centerPixel.getRed());
      assertEquals("Black image should remain black (green)",
              0, centerPixel.getGreen());
      assertEquals("Black image should remain black (blue)",
              0, centerPixel.getBlue());
    }
  }

  /**
   * Test class for testing the Sharpen filter functionality on normal RGB images.
   */
  public static class SharpenNormalImageTest {
    private Image testImage;

    @Before
    public void setUp() {
      Pixel[][] pixels = new Pixel[5][5];
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          pixels[i][j] = Factory.createRGBPixel(100, 150, 200);
        }
      }
      testImage = Factory.createImage(pixels);
    }

    @Test
    public void testSharpenUniformImage() {
      Image sharpenedImage = FilterUtils.applyFilter(testImage, FilterOption.SHARPEN);
      ;
      Pixel[][] expectedPixels = {
              {Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(112, 168, 225)},
              {Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(212, 255, 255), Factory.createRGBPixel(162, 243, 255), Factory.createRGBPixel(212, 255, 255), Factory.createRGBPixel(150, 225, 255)},
              {Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(162, 243, 255), Factory.createRGBPixel(100, 150, 200), Factory.createRGBPixel(162, 243, 255), Factory.createRGBPixel(112, 168, 225)},
              {Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(212, 255, 255), Factory.createRGBPixel(162, 243, 255), Factory.createRGBPixel(212, 255, 255), Factory.createRGBPixel(150, 225, 255)},
              {Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(112, 168, 225), Factory.createRGBPixel(150, 225, 255), Factory.createRGBPixel(112, 168, 225)}
      };

      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          assertEquals(expectedPixels[i][j], sharpenedImage.getPixel(i, j));
        }
      }
    }

    @Test
    public void testImageDimensionsPreserved() {
      Image sharpenedImage = FilterUtils.applyFilter(testImage, FilterOption.SHARPEN);
      ;
      assertEquals("Image width should be preserved",
              testImage.getWidth(), sharpenedImage.getWidth());
      assertEquals("Image height should be preserved",
              testImage.getHeight(), sharpenedImage.getHeight());
    }
  }

  /**
   * Test class for testing the Sharpen filter functionality on single pixel images.
   */
  public static class SharpenSinglePixelTest {
    private Image singlePixelImage;

    @Before
    public void setUp() {
      Pixel[][] pixel = new Pixel[1][1];
      pixel[0][0] = Factory.createRGBPixel(100, 100, 100);
      singlePixelImage = Factory.createImage(pixel);
    }

    @Test
    public void testSharpenSinglePixel() {
      Image sharpenedImage = FilterUtils.applyFilter(singlePixelImage, FilterOption.SHARPEN);
      Pixel resultPixel = sharpenedImage.getPixel(0, 0);

      assertEquals("Single pixel red value should be affected by kernel",
              100, resultPixel.getRed());
      assertEquals("Single pixel green value should be affected by kernel",
              100, resultPixel.getGreen());
      assertEquals("Single pixel blue value should be affected by kernel",
              100, resultPixel.getBlue());
    }

    @Test
    public void testSinglePixelDimensionsPreserved() {
      Image sharpenedImage = FilterUtils.applyFilter(singlePixelImage, FilterOption.SHARPEN);
      assertEquals("Image width should remain 1",
              1, sharpenedImage.getWidth());
      assertEquals("Image height should remain 1",
              1, sharpenedImage.getHeight());
    }
  }

}
