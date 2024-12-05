package model;


import controller.ImageUtil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * This class implements the ImageModel interface and provides functionalities for image processing
 * using a 2D array of Pixel objects.
 */
public class ImageImplementation implements ImageModel {

  private Pixel[][] image;
  private Pixel[][] redChannelImage;
  private Pixel[][] greenChannelImage;
  private Pixel[][] blueChannelImage;

  /**
   * Constructor for ImageImplementation. Initializes an ImageImplementation instance with no image
   * loaded initially.
   */
  public ImageImplementation() {
    // No image loaded initially
  }

  /**
   * Saves the current image to the specified filepath. Supported formats include PPM, JPG, and
   * PNG.
   *
   * @param filepath the path to save the image.
   * @param image    the 2D array of Pixel objects representing the image.
   */
  @Override
  public void saveImage(String filepath, Pixel[][] image) {
    // Check for null or empty filepath
    if (filepath == null || filepath.isEmpty()) {
      throw new IllegalArgumentException("Filepath cannot be null or empty.");
    }

    if (filepath.endsWith(".ppm")) {
      ImageUtil.savePPM(filepath, image);
    } else {
      try {
        int height = image.length;
        int width = image[0].length;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < height; row++) {
          for (int col = 0; col < width; col++) {
            Pixel pixel = image[row][col];
            int rgb = (pixel.get(0) << 16) | (pixel.get(1) << 8) | (pixel.get(2));
            bufferedImage.setRGB(col, row, rgb);
          }
        }

        File outputFile = new File(filepath);
        String format = filepath.substring(filepath.lastIndexOf('.') + 1);
        ImageIO.write(bufferedImage, format, outputFile);

        System.out.println("Image saved successfully to " + filepath);
      } catch (IOException e) {
        System.err.println("Error saving image: " + filepath);
        e.printStackTrace();
      }
    }
  }

  /**
   * Sets the current image to the provided 2D array of Pixel objects. This allows you to directly
   * assign a new image to the model.
   *
   * @param image the 2D array of Pixel objects representing the image to be set.
   */
  public void setImage(Pixel[][] image) {
    // perform deep copy
    int height = image.length;
    int width = image[0].length;

    this.image = new Pixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        this.image[row][col] = new Pixel(image[row][col].get(0), image[row][col].get(1),
            image[row][col].get(2));
      }
    }
  }

  /**
   * Retrieves the RGB components of the current image. Each pixel is represented as an array of
   * three integers corresponding to red, green, and blue values.
   *
   * @return a 3D array containing the RGB components of each pixel in the image. If no image is
   *         loaded, returns an empty 3D array.
   */
  @Override
  public int[][][] getRGBComponents() {
    if (image == null) {
      return new int[0][][];
    }

    int height = image.length;
    int width = image[0].length;
    int[][][] rgbComponents = new int[height][width][3];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        rgbComponents[row][col][0] = image[row][col].get(0);
        rgbComponents[row][col][1] = image[row][col].get(1);
        rgbComponents[row][col][2] = image[row][col].get(2);
      }
    }
    return rgbComponents;
  }

  /**
   * Computes and returns the luma (brightness) value of each pixel in the image. The luma is
   * calculated as a weighted sum of the red, green, and blue values.
   *
   * @return a 2D array of Pixels where each pixel is set to its luma value. If no image is loaded,
   *         returns an empty 2D array.
   */
  @Override
  public Pixel[][] getLuma() {
    if (image == null) {
      return new Pixel[0][];
    }

    int height = image.length;
    int width = image[0].length;
    Pixel[][] luma = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = image[row][col].get(0);
        int green = image[row][col].get(1);
        int blue = image[row][col].get(2);

        int lumaValue = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
        luma[row][col] = new Pixel(lumaValue, lumaValue, lumaValue);
      }
    }
    return luma;
  }

  /**
   * Computes and returns the intensity of each pixel in the image. The intensity is calculated as
   * the average of the red, green, and blue values.
   *
   * @return a 2D array of Pixels where each pixel is set to its intensity value. If no image is
   *         loaded, returns an empty 2D array.
   */
  @Override
  public Pixel[][] getIntensity() {
    if (image == null) {
      return new Pixel[0][];
    }

    int height = image.length;
    int width = image[0].length;
    Pixel[][] intensity = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = image[row][col].get(0);
        int green = image[row][col].get(1);
        int blue = image[row][col].get(2);

        int intensityValue = (red + green + blue) / 3;
        intensity[row][col] = new Pixel(intensityValue, intensityValue, intensityValue);
      }
    }
    return intensity;
  }

  /**
   * Computes and returns the value component of each pixel in the image. The value is the maximum
   * of the red, green, and blue values.
   *
   * @return a 2D array of Pixels where each pixel is set to its value. If no image is loaded,
   *         returns an empty 2D array.
   */
  @Override
  public Pixel[][] getValue() {
    if (image == null) {
      return new Pixel[0][];
    }

    int height = image.length;
    int width = image[0].length;
    Pixel[][] value = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = image[row][col].get(0);
        int green = image[row][col].get(1);
        int blue = image[row][col].get(2);

        int valueValue = Math.max(Math.max(red, green), blue);
        value[row][col] = new Pixel(valueValue, valueValue, valueValue);
      }
    }
    return value;
  }

  /**
   * Flips the current image horizontally, reversing the pixel order in each row. If no image is
   * loaded, the method does nothing.
   */
  @Override
  public void flipHorizontally() {
    if (image == null) {
      return;
    }

    int height = image.length;
    int width = image[0].length;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width / 2; col++) {

        Pixel temp = image[row][col];
        image[row][col] = image[row][width - col - 1];
        image[row][width - col - 1] = temp;
      }
    }
  }

  /**
   * Flips the current image vertically, reversing the pixel order in each column. If no image is
   * loaded, the method does nothing.
   */
  @Override
  public void flipVertically() {
    if (image == null) {
      return;
    }

    int height = image.length;
    int width = image[0].length;

    for (int row = 0; row < height / 2; row++) {
      for (int col = 0; col < width; col++) {

        Pixel temp = image[row][col];
        image[row][col] = image[height - row - 1][col];
        image[height - row - 1][col] = temp;
      }
    }
  }

  /**
   * Brightens the current image by a specified amount. Increases the red, green, and blue values of
   * each pixel, clamping the values to a maximum of 255.
   *
   * @param amount the amount to increase the brightness of each pixel.
   */
  @Override
  public void brighten(int amount) {
    if (image == null) {
      return;
    }

    int height = image.length;
    int width = image[0].length;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = image[row][col];
        pixel.set(0, Math.min(pixel.get(0) + amount, 255));
        pixel.set(1, Math.min(pixel.get(1) + amount, 255));
        pixel.set(2, Math.min(pixel.get(2) + amount, 255));
      }
    }
  }

  /**
   * Darkens the current image by a specified amount. Decreases the red, green, and blue values of
   * each pixel, clamping the values to a minimum of 0.
   *
   * @param amount the amount to decrease the brightness of each pixel.
   */
  @Override
  public void darken(int amount) {
    if (image == null) {
      return;
    }

    int height = image.length;
    int width = image[0].length;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = image[row][col];
        pixel.set(0, Math.max(pixel.get(0) - amount, 0));
        pixel.set(1, Math.max(pixel.get(1) - amount, 0));
        pixel.set(2, Math.max(pixel.get(2) - amount, 0));
      }
    }
  }

  /**
   * Splits the current image into its red, green, and blue channels and stores the result in
   * separate images for each channel, represented in grayscale.
   */
  @Override
  public void splitChannels() {
    if (image == null || image.length == 0 || image[0].length == 0) {
      throw new IllegalStateException("No image loaded.");
    }

    int height = image.length;
    int width = image[0].length;

    int[][] redChannel = new int[height][width];
    int[][] greenChannel = new int[height][width];
    int[][] blueChannel = new int[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel originalPixel = image[row][col];
        redChannel[row][col] = originalPixel.get(0);
        greenChannel[row][col] = originalPixel.get(1);
        blueChannel[row][col] = originalPixel.get(2);
      }
    }

    redChannelImage = convertSingleChannelToGrayscaleImage(redChannel);
    greenChannelImage = convertSingleChannelToGrayscaleImage(greenChannel);
    blueChannelImage = convertSingleChannelToGrayscaleImage(blueChannel);
  }

  private Pixel[][] convertSingleChannelToGrayscaleImage(int[][] channel) {
    int height = channel.length;
    int width = channel[0].length;
    Pixel[][] grayscaleImage = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int value = channel[row][col];
        grayscaleImage[row][col] = new Pixel(value, value, value);
      }
    }

    return grayscaleImage;
  }

  /**
   * The red channel is represented as a 2D array of Pixels where each pixel contains only the red
   * value.
   *
   * @return a 2D array representing the red channel of the image, or null if no image is loaded.
   */
  public Pixel[][] getRedChannelImage() {
    return redChannelImage;
  }

  /**
   * The green channel is represented as a 2D array of Pixels where each pixel contains only the
   * green value.
   *
   * @return a 2D array representing the green channel of the image, or null if no image is loaded.
   */
  public Pixel[][] getGreenChannelImage() {
    return greenChannelImage;
  }

  /**
   * The blue channel is represented as a 2D array of Pixels where each pixel contains only the blue
   * value.
   *
   * @return a 2D array representing the blue channel of the image, or null if no image is loaded.
   */
  public Pixel[][] getBlueChannelImage() {
    return blueChannelImage;
  }

  /**
   * Combines the red, green, and blue channel arrays into a single image.
   *
   * @param red   the red channel array.
   * @param green the green channel array.
   * @param blue  the blue channel array.
   * @throws IllegalArgumentException if any of the channel arrays are null or have different
   *                                  dimensions.
   */
  @Override
  public void combineChannels(int[][] red, int[][] green, int[][] blue) {
    if (red == null || green == null || blue == null) {
      throw new IllegalArgumentException("Channel arrays cannot be null.");
    }

    int height = red.length;
    int width = red[0].length;

    if (green.length != height || blue.length != height || green[0].length != width
        || blue[0].length != width) {
      throw new IllegalArgumentException("All channel arrays must have the same dimensions.");
    }

    this.image = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        this.image[row][col] = new Pixel(Math.min(Math.max(red[row][col], 0), 255),
            Math.min(Math.max(green[row][col], 0), 255),
            Math.min(Math.max(blue[row][col], 0), 255));
      }
    }
  }

  /**
   * Extracts a specific color channel (Red, Green, or Blue) from a 2D array of Pixel objects.
   *
   * @param image   the 2D array of Pixel objects representing the image.
   * @param channel an integer representing the color channel to extract: 0 for Red, 1 for Green,
   *                and 2 for Blue.
   * @return a 2D integer array containing the values of the specified color channel. Each element
   *         corresponds to the color intensity of the given channel at that position
   *         in the original image.
   * @throws IllegalArgumentException if the specified channel is not 0, 1, or 2.
   */
  @Override
  public int[][] extractChannel(Pixel[][] image, int channel) {
    int height = image.length;
    int width = image[0].length;
    int[][] channelArray = new int[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        switch (channel) {
          case 0:
            channelArray[row][col] = image[row][col].get(0);
            break;
          case 1:
            channelArray[row][col] = image[row][col].get(1);
            break;
          case 2:
            channelArray[row][col] = image[row][col].get(2);
            break;
          default:
            throw new IllegalArgumentException("Invalid channel: " + channel);
        }
      }
    }
    return channelArray;
  }

  /**
   * Applies a convolution filter to the current image using the provided kernel.
   *
   * @param kernel the convolution kernel to apply to the image.
   */
  @Override
  public void applyFilter(double[][] kernel) {
    if (image == null || kernel == null) {
      System.out.println("Image not loaded or kernel is null.");
      return;
    }

    int height = image.length;
    int width = image[0].length;
    int kernelSize = kernel.length;
    int kernelRadius = kernelSize / 2;

    Pixel[][] outputImage = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        double red = 0.0;
        double green = 0.0;
        double blue = 0.0;

        for (int kr = -kernelRadius; kr <= kernelRadius; kr++) {
          for (int kc = -kernelRadius; kc <= kernelRadius; kc++) {
            int imageRow = row + kr;
            int imageCol = col + kc;

            imageRow = Math.min(Math.max(imageRow, 0), height - 1);
            imageCol = Math.min(Math.max(imageCol, 0), width - 1);

            Pixel pixel = image[imageRow][imageCol];
            double kernelValue = kernel[kr + kernelRadius][kc + kernelRadius];

            red += pixel.get(0) * kernelValue;
            green += pixel.get(1) * kernelValue;
            blue += pixel.get(2) * kernelValue;
          }
        }

        int clampedRed = (int) Math.min(Math.max(red, 0), 255);
        int clampedGreen = (int) Math.min(Math.max(green, 0), 255);
        int clampedBlue = (int) Math.min(Math.max(blue, 0), 255);

        outputImage[row][col] = new Pixel(clampedRed, clampedGreen, clampedBlue);
      }
    }
    this.image = outputImage;

  }

  /**
   * Applies the convolution filter to the part of the image, which is determined by the split
   * point. The filter operates only on the left half of the image up to the specified split. All
   * the pixels to the right of the split remain in place, while the pixel at the split is set to
   * black (0, 0, 0).
   *
   * @param kernel     the convolution kernel represented as a 2D array of double values, which
   *                   defines the filter to apply.
   * @param splitPoint the column index that divides the image. The filter is applied to all pixels
   *                   in columns less than this value, and pixels at or beyond the split point are
   *                   unaffected or set to black.
   * @throws IllegalArgumentException if the kernel is null or if the image has not been loaded.
   */
  @Override
  public void applyFilterToPart(double[][] kernel, int splitPoint) {
    if (image == null || kernel == null) {
      System.out.println("Image not loaded or kernel is null.");
      return;
    }

    int height = image.length;
    int width = image[0].length;
    int kernelSize = kernel.length;
    int kernelRadius = kernelSize / 2;

    Pixel[][] outputImage = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          double red = 0.0;
          double green = 0.0;
          double blue = 0.0;

          for (int kr = -kernelRadius; kr <= kernelRadius; kr++) {
            for (int kc = -kernelRadius; kc <= kernelRadius; kc++) {
              int imageRow = Math.min(Math.max(row + kr, 0), height - 1);
              int imageCol = Math.min(Math.max(col + kc, 0), width - 1);

              Pixel pixel = image[imageRow][imageCol];
              double kernelValue = kernel[kr + kernelRadius][kc + kernelRadius];

              red += pixel.get(0) * kernelValue;
              green += pixel.get(1) * kernelValue;
              blue += pixel.get(2) * kernelValue;
            }
          }

          int clampedRed = (int) Math.min(Math.max(red, 0), 255);
          int clampedGreen = (int) Math.min(Math.max(green, 0), 255);
          int clampedBlue = (int) Math.min(Math.max(blue, 0), 255);

          outputImage[row][col] = new Pixel(clampedRed, clampedGreen, clampedBlue);
        } else if (col == splitPoint) {
          outputImage[row][col] = new Pixel(0, 0, 0);
        } else {
          outputImage[row][col] = image[row][col];
        }
      }
    }
    this.image = outputImage;
  }

  /**
   * Applies a blur filter to the current image. Uses a predefined kernel to blur the image.
   *
   * @throws IllegalStateException if the image is null.
   */
  @Override
  public void applyBlur() {
    if (image == null) {
      throw new IllegalStateException("Image is not loaded.");
    }
    applyBlur(100);
  }

  /**
   * Applies a Gaussian blur to a specified percentage of the image, starting from the left edge to
   * the specified split percentage. The blur applies only to the left part of the image, while the
   * right part. It is just the same.
   *
   * @param p the percentage of the image width up to which the blur will be applied. Must be
   *          between 0 and 100, where 0 means no blur, and 100 means blur is applied to the entire
   *          image.
   * @throws IllegalArgumentException if the provided percentage is not within the range [0, 100].
   */
  @Override
  public void applyBlur(double p) {
    if (p < 0 || p > 100) {
      throw new IllegalArgumentException("Split Percentage must be between 0 and 100.");
    }

    int width = image[0].length;
    int splitPoint = (int) (width * (p / 100));

    double[][] blurKernel = {
        {1.0 / 16, 1.0 / 8, 1.0 / 16},
        {1.0 / 8, 1.0 / 4, 1.0 / 8},
        {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

    applyFilterToPart(blurKernel, splitPoint);
  }

  /**
   * Applies a sharpen filter to the current image. Uses a predefined kernel to sharpen the image.
   *
   * @throws IllegalStateException if the image is null.
   */
  @Override
  public void applySharpen() {
    if (image == null) {
      throw new IllegalStateException("Image is not loaded.");
    }
    applySharpen(100);
  }

  /**
   * Applies a sharpening filter to a specified percentage of the image, from the left edge up to
   * the specified split percentage. The sharpening effect is applied only to the left portion of
   * the image, while the right portion remains unchanged.
   *
   * @param p the percentage of the image width up to which the sharpen filter will be applied. Must
   *          be between 0 and 100, where 0 means no sharpening, and 100 means sharpening is applied
   *          to the entire image.
   * @throws IllegalArgumentException if the provided percentage is not within the range [0, 100].
   */
  @Override
  public void applySharpen(double p) {
    if (p < 0 || p > 100) {
      throw new IllegalArgumentException("Split Percentage must be between 0 and 100.");
    }

    int width = image[0].length;
    int splitPoint = (int) (width * (p / 100));

    double[][] sharpenKernel = {{-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}};
    applyFilterToPart(sharpenKernel, splitPoint);
  }

  /**
   * Converts the current image to grayscale using the luma formula.
   *
   * @throws IllegalStateException when the image is null.
   */
  @Override
  public void applyGreyScale() {
    if (image == null) {
      throw new IllegalStateException("Image is not loaded.");
    }
    applyGreyScale(100);
  }

  /**
   * Applies the grayscale filter to the specified percentage of the image from the left edge up to
   * the original according to a given split percentage. The grayscale effect applies only from the
   * left half of the image. Everything but the right part of the image is translated. The color of
   * the pixel at the splitting point is black (0, 0, 0).
   *
   * @param p the percentage of the image width up to which the grayscale filter will be applied.
   *          Must be between 0 and 100, where 0 means no grayscale is applied, and 100 means
   *          grayscale is applied to the entire image.
   * @throws IllegalArgumentException if the provided percentage is not within the range [0, 100].
   */
  @Override
  public void applyGreyScale(double p) {
    if (p < 0 || p > 100) {
      throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
    }

    int width = image[0].length;
    int splitPoint = (int) (width * (p / 100));

    int height = image.length;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          Pixel pixel = image[row][col];
          int r = pixel.get(0);
          int g = pixel.get(1);
          int b = pixel.get(2);

          int newRed = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

          pixel.set(0, Math.min(Math.max(newRed, 0), 255));  // Set red channel
          pixel.set(1, Math.min(Math.max(newRed, 0), 255));  // Set green channel
          pixel.set(2, Math.min(Math.max(newRed, 0), 255));
        } else if (col == splitPoint) {
          image[row][col] = new Pixel(0, 0, 0);
        } else {
          continue;
        }
      }
    }
  }

  /**
   * Applies a sepia tone filter to the current image.
   */
  @Override
  public void applySepia() {
    if (image == null) {
      throw new IllegalStateException("Image is not loaded.");
    }
    applySepia(100);
  }

  /**
   * Applies the sepia filter to the specified percentage of the image from the left edge to a given
   * percentage of split. Sepia effect is only applied to the left half of the image right side of
   * the screen doesn't change. The pixel at split position will be black color which is 0, 0, 0.
   *
   * @param p the percentage of the image width up to which the sepia filter will be applied. Must
   *          be between 0 and 100, where 0 means no sepia is applied, and 100 means sepia is
   *          applied to the entire image.
   * @throws IllegalArgumentException if the provided percentage is not within the range [0, 100].
   */
  @Override
  public void applySepia(double p) {
    if (p < 0 || p > 100) {
      throw new IllegalArgumentException("Split Percentage must be between 0 and 100.");
    }

    int width = image[0].length;
    int splitPoint = (int) (width * (p / 100));

    int height = image.length;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          Pixel pixel = image[row][col];
          int r = pixel.get(0);
          int g = pixel.get(1);
          int b = pixel.get(2);

          int newRed = (int) (0.393 * r + 0.769 * g + 0.189 * b);
          int newGreen = (int) (0.349 * r + 0.686 * g + 0.168 * b);
          int newBlue = (int) (0.272 * r + 0.534 * g + 0.131 * b);

          pixel.set(0, Math.min(Math.max(newRed, 0), 255));  // Set red channel
          pixel.set(1, Math.min(Math.max(newGreen, 0), 255));  // Set green channel
          pixel.set(2, Math.min(Math.max(newBlue, 0), 255));
        } else if (col == splitPoint) {
          image[row][col] = new Pixel(0, 0, 0);
        } else {
          continue;
        }
      }
    }
  }

  /**
   * Applies color correction to the entire image by adjusting each color channel (Red, Green, and
   * Blue) based on histogram peaks. The color correction aims to balance the color distribution
   * across the image.
   *
   * @throws IllegalStateException if no image is loaded for color correction.
   */
  @Override
  public void colorCorrect() {
    if (image == null) {
      throw new IllegalStateException("Image is not loaded.");
    }
    colorCorrect(100);
  }

  /**
   * Applies color correction to a specified percentage of the image from the left edge up to the
   * specified split percentage. Color correction is applied only to the left portion of the image;
   * the right portion remains unaltered. Sets the pixel at the split point to black (0, 0, 0).
   * Color correction is done for each color channel: Red, Green, and Blue, using histogram peaks to
   * adjust it to attain a perfect balance of colors in the corrected portion.
   *
   * @param p the percentage of the image width up to which color correction will be applied. Must
   *          be between 0 and 100, where 0 means no correction is applied, and 100 means correction
   *          is applied to the entire image.
   * @throws IllegalArgumentException if the provided percentage is not within the range [0, 100].
   */
  @Override
  public void colorCorrect(double p) {
    if (p < 0 || p > 100) {
      throw new IllegalArgumentException("Percentage must be between 0 and 100.");
    }

    if (image == null) {
      System.out.println("No image loaded for color correction.");
      return;
    }

    int width = image[0].length;
    int splitPoint = (int) (width * (p / 100));
    int height = image.length;

    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    for (Pixel[] row : image) {
      for (Pixel pixel : row) {
        redHistogram[pixel.get(0)]++;
        greenHistogram[pixel.get(1)]++;
        blueHistogram[pixel.get(2)]++;
      }
    }

    int redPeak = findPeakInRange(redHistogram, 10, 245);
    int greenPeak = findPeakInRange(greenHistogram, 10, 245);
    int bluePeak = findPeakInRange(blueHistogram, 10, 245);

    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    int redOffset = averagePeak - redPeak;
    int greenOffset = averagePeak - greenPeak;
    int blueOffset = averagePeak - bluePeak;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          Pixel pixel = image[row][col];
          pixel.set(0, clamp(pixel.get(0) + redOffset));
          pixel.set(1, clamp(pixel.get(1) + greenOffset));
          pixel.set(2, clamp(pixel.get(2) + blueOffset));
        } else if (col == splitPoint) {
          image[row][col] = new Pixel(0, 0, 0);
        } else {
          // Keep the original pixel for the remaining part
          continue;
        }
      }
    }
  }

  private int findPeakInRange(int[] histogram, int minRange, int maxRange) {
    int peak = minRange;
    int maxCount = histogram[minRange];

    for (int i = minRange + 1; i <= maxRange; i++) {
      if (histogram[i] > maxCount) {
        maxCount = histogram[i];
        peak = i;
      }
    }

    return peak;
  }

  private int clamp(int value) {
    return Math.min(Math.max(value, 0), 255);
  }

  /**
   * Calculates and generates a histogram image representing the distribution of color intensities
   * for the Red, Green, and Blue channels in the loaded image. The histogram is drawn as a bar
   * graph for each channel, allowing visual analysis of color distribution.
   *
   * @return a BufferedImage object representing the histogram of the loaded image. Each color
   *         channel (Red, Green, and Blue) is represented with its respective color on the graph.
   *         Returns null if no image is loaded.
   */
  @Override
  public BufferedImage calculateHistogram() {
    if (image == null) {
      System.out.println("No image loaded to generate a histogram.");
      return null;
    }

    int width = 256;
    int height = 256;
    BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = histogramImage.createGraphics();

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, width, height);

    int[] redChannel = new int[256];
    int[] greenChannel = new int[256];
    int[] blueChannel = new int[256];

    for (Pixel[] row : image) {
      for (Pixel pixel : row) {
        redChannel[pixel.get(0)]++;
        greenChannel[pixel.get(1)]++;
        blueChannel[pixel.get(2)]++;
      }
    }

    int maxRed = getMax(redChannel);
    int maxGreen = getMax(greenChannel);
    int maxBlue = getMax(blueChannel);

    drawChannelHistogram(g2d, redChannel, maxRed, Color.RED, width, height);
    drawChannelHistogram(g2d, greenChannel, maxGreen, Color.GREEN, width, height);
    drawChannelHistogram(g2d, blueChannel, maxBlue, Color.BLUE, width, height);

    g2d.dispose();

    return histogramImage;
  }

  private int getMax(int[] channel) {
    int max = 0;
    for (int value : channel) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  private void drawChannelHistogram(Graphics2D g2d, int[] channelData, int max, Color color,
      int width, int height) {
    g2d.setColor(color);
    for (int i = 0; i < channelData.length - 1; i++) {
      int x1 = i;
      int y1 = height - (channelData[i] * height / max);
      int x2 = i + 1;
      int y2 = height - (channelData[i + 1] * height / max);
      g2d.drawLine(x1, y1, x2, y2);
    }
  }

  /**
   * Get the current image as a 2D array of Pixel objects.
   *
   * @return the image.
   */
  public Pixel[][] getImage() {
    return image;
  }

  /**
   * Adjusts the color levels of the loaded image based on specified shadow, midtone, and highlight
   * values. This level adjustment operation modifies the brightness and contrast of the image by
   * applying a quadratic transformation to each pixel's color channels (Red, Green, and Blue) based
   * on the input values.
   *
   * @param shadow    the intensity value representing the shadow level, typically between 0 and
   *                  255. Affects the dark areas of the image.
   * @param mid       the intensity value representing the midtone level, typically between 0 and
   *                  255. Affects the mid-brightness areas of the image.
   * @param highlight the intensity value representing the highlight level, typically between 0
   *                  and 255. Affects the bright areas of the image.
   * @throws IllegalStateException    if no image is loaded before applying levels adjustment.
   * @throws IllegalArgumentException if the shadow, mid, and highlight values
   *                                  are not in ascending order,
   *                                  or if any of them are not between 0 and 255.
   */
  @Override
  public void levelsAdjust(int shadow, int mid, int highlight) {
    if (image == null) {
      throw new IllegalStateException("Image is not loaded.");
    }

    // Check if shadow, mid, and highlight are in ascending order
    if (shadow >= mid || mid >= highlight) {
      throw new IllegalArgumentException("Shadow, mid, and highlight must be in ascending order.");
    }

    // Check if shadow, mid, and highlight are between 0 and 255
    if (shadow < 0 || shadow > 255 || mid < 0 || mid > 255 || highlight < 0 || highlight > 255) {
      throw new IllegalArgumentException(
          "Shadow, mid, and highlight values must be between 0 and 255.");
    }

    // Call the overloaded levelsAdjust method
    levelsAdjust(shadow, mid, highlight, 100);
  }

  /**
   * Using the provided shadow, midtone, and highlight values, it modifies the color levels of the
   * designated percentage of the image from the left edge up to the designated split %.
   *
   * @param shadow    the intensity value representing the shadow level, typically between 0 and
   *                  255. Affects the dark areas of the image.
   * @param mid       the intensity value representing the midtone level, typically between 0 and
   *                  255. Affects the mid-brightness areas of the image.
   * @param highlight the intensity value representing the highlight level, typically between 0 and
   *                  255. Affects the bright areas of the image.
   * @param p         the percentage of the image width up to which the levels adjustment will be
   *                  applied. Must be between 0 and 100, where 0 means no adjustment, and 100 means
   *                  adjustment is applied to the entire image.
   * @throws IllegalArgumentException if the provided percentage is not within the range [0, 100].
   * @throws IllegalStateException    if no image is loaded before applying levels adjustment.
   */
  @Override
  public void levelsAdjust(int shadow, int mid, int highlight, double p) {
    if (image == null) {
      System.out.println("No image loaded.");
      return;
    }

    if (p < 0 || p > 100) {
      throw new IllegalArgumentException("Percentage must be between 0 and 100.");
    }

    int width = image[0].length;
    int splitPoint = (int) (width * (p / 100));

    double a =
        Math.pow(shadow, 2) * (mid - highlight) - shadow * (Math.pow(mid, 2) - Math.pow(highlight,
            2)) + highlight * Math.pow(mid, 2) - mid * Math.pow(highlight, 2);
    double aA = -shadow * (128 - 255) + 128 * highlight - 255 * mid;
    double aB =
        Math.pow(shadow, 2) * (128 - 255) + 255 * Math.pow(mid, 2) - 128 * Math.pow(highlight, 2);
    double aC =
        Math.pow(shadow, 2) * (255 * mid - 128 * highlight) - shadow * (255 * Math.pow(mid, 2)
            - 128 * Math.pow(highlight, 2));

    double a1 = aA / a;
    double b1 = aB / a;
    double c1 = aC / a;

    int height = image.length;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          Pixel pixel = image[row][col];
          pixel.set(0,
              clamp((int) (a1 * pixel.get(0) * pixel.get(0) + b1 * pixel.get(0) + c1)));
          pixel.set(1,
              clamp((int) (a1 * pixel.get(1) * pixel.get(1) + b1 * pixel.get(1) + c1)));
          pixel.set(2,
              clamp((int) (a1 * pixel.get(2) * pixel.get(2) + b1 * pixel.get(2) + c1)));
        } else if (col == splitPoint) {
          image[row][col] = new Pixel(0, 0, 0);
        }
      }
    }

  }

  private double[] flattenImage() {
    int height = image.length;
    int width = image[0].length;
    double[] flattened = new double[height * width * 3];  // 3 for R, G, B channels

    int index = 0;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = image[row][col];
        int red = pixel.get(0);
        int green = pixel.get(1);
        int blue = pixel.get(2);

        // Store RGB values separately
        flattened[index++] = red;
        flattened[index++] = green;
        flattened[index++] = blue;
      }
    }
    return flattened;
  }


  // Method to reshape a 1D array back into a 2D Pixel[][] array
  private void reshapeImage(double[] flattened) {
    int height = image.length;
    int width = image[0].length;
    int index = 0;

    // Ensure the flattened array size is correct (should be height * width * 3 for RGB)
    if (flattened.length != height * width * 3) {
      throw new IllegalArgumentException(
          "Flattened array size does not match the expected size of the image.");
    }

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = (int) Math.round(flattened[index++]);
        int green = (int) Math.round(flattened[index++]);
        int blue = (int) Math.round(flattened[index++]);

        // Assign decompressed RGB values to respective channels
        image[row][col] = new Pixel(red, green, blue);
      }
    }
  }


  // Haar wavelet transform (simplified version)
  private double[] haarTransform(double[] s) {
    int n = s.length;
    double[] result = new double[n];
    double[] temp = new double[n];

    int m = 1;
    while (m <= n / 2) {
      for (int i = 0; i < n / (2 * m); i++) {
        int start = i * 2 * m;
        for (int j = 0; j < m; j++) {
          result[start + j] = (s[start + 2 * j] + s[start + 2 * j + 1]) / Math.sqrt(2);
          result[start + m + j] = (s[start + 2 * j] - s[start + 2 * j + 1]) / Math.sqrt(2);
        }
      }
      m *= 2;
    }
    return result;
  }

  // Inverse Haar wavelet transform
  private double[] inverseHaarTransform(double[] s) {
    int n = s.length;
    double[] result = new double[n];
    double[] temp = new double[n];

    int m = 1;
    while (m <= n / 2) {
      for (int i = 0; i < n / (2 * m); i++) {
        int start = i * 2 * m;
        for (int j = 0; j < m; j++) {
          result[start + 2 * j] = (s[start + j] + s[start + m + j]) / Math.sqrt(2);
          result[start + 2 * j + 1] = (s[start + j] - s[start + m + j]) / Math.sqrt(2);
        }
      }
      m *= 2;
    }
    return result;
  }

  /**
   * Compresses the image by applying the Haar wavelet transform to every color channel, performs
   * thresholding of low-magnitude coefficients, reducing data size, and then reconstructs the
   * image.
   *
   * @param threshold the percentage threshold used to compress the image. Coefficients below this
   *                  percentage of the maximum value in each channel are set to zero, effectively
   *                  removing them from the compressed image. Must be between 0 and 100, where 0
   *                  means no compression and 100 means maximum compression.
   * @throws IllegalStateException if the image channels are not loaded before compression.
   */
  @Override
  public void compressImage(double threshold) {
    if (threshold < 0 || threshold > 100) {
      throw new IllegalArgumentException("Threshold must be between 0 and 100.");
    }

    double[] flattenedImage = flattenImage();

    double[] transformedImage = haarTransform(flattenedImage);

    for (int i = 0; i < transformedImage.length; i++) {
      if (Math.abs(transformedImage[i]) < threshold) {
        transformedImage[i] = 0;
      }
    }

    double[] decompressedImage = inverseHaarTransform(transformedImage);

    decompressedImage = downSampleImage(decompressedImage, threshold);

    decompressedImage = upSampleImage(decompressedImage, image[0].length / 8, image.length / 8,
        image[0].length, image.length);

    reshapeImage(decompressedImage);
  }

  private double[] downSampleImage(double[] decompressedImage, double threshold) {
    int height = image.length;
    int width = image[0].length;

    int factor = (int) Math.max(1, Math.min(10, Math.round((100 - threshold) / 10.0)));

    int newWidth = width / factor;
    int newHeight = height / factor;

    newWidth = Math.max(newWidth, 1);
    newHeight = Math.max(newHeight, 1);

    double[] downsampledImage = new double[newWidth * newHeight * 3];
    int index = 0;

    for (int row = 0; row < newHeight; row++) {
      for (int col = 0; col < newWidth; col++) {
        int startRow = Math.min((row * height) / newHeight, height - 1);
        int endRow = Math.min(((row + 1) * height) / newHeight, height);
        int startCol = Math.min((col * width) / newWidth, width - 1);
        int endCol = Math.min(((col + 1) * width) / newWidth, width);

        double red = 0;
        double green = 0;
        double blue = 0;
        int count = 0;

        for (int r = startRow; r < endRow; r++) {
          for (int c = startCol; c < endCol; c++) {
            int pixelIndex = (r * width + c) * 3;
            red += decompressedImage[pixelIndex];
            green += decompressedImage[pixelIndex + 1];
            blue += decompressedImage[pixelIndex + 2];
            count++;
          }
        }

        downsampledImage[index++] = red / count;
        downsampledImage[index++] = green / count;
        downsampledImage[index++] = blue / count;
      }
    }

    return downsampledImage;
  }


  private double[] upSampleImage(double[] downsampledImage, int downsampledWidth,
      int downsampledHeight, int originalWidth, int originalHeight) {
    double[] upsampledImage = new double[originalWidth * originalHeight * 3];  // 3 channels for RGB
    int index = 0;

    for (int row = 0; row < originalHeight; row++) {
      for (int col = 0; col < originalWidth; col++) {

        int srcRow = Math.min((row * downsampledHeight) / originalHeight, downsampledHeight - 1);
        int srcCol = Math.min((col * downsampledWidth) / originalWidth, downsampledWidth - 1);

        int pixelIndex =
            (srcRow * downsampledWidth + srcCol) * 3;

        if (pixelIndex < downsampledImage.length) {

          upsampledImage[index++] = downsampledImage[pixelIndex];
          upsampledImage[index++] = downsampledImage[pixelIndex + 1];
          upsampledImage[index++] = downsampledImage[pixelIndex + 2];
        }
      }
    }

    return upsampledImage;
  }

  /**
   * Downscales the current image to the specified width and height.
   *
   * @param newWidth  the desired width of the downscaled image, must be greater than 0
   * @param newHeight the desired height of the downscaled image, must be greater than 0
   */
  @Override
  public void downscaleImage(int newWidth, int newHeight) {
    if (image == null) {
      System.out.println("No image loaded.");
      return;
    }
    if (newWidth < 0 || newHeight < 0) {
      throw new IllegalArgumentException("Width and height must be greater than 0.");
    }

    int oldWidth = image[0].length;
    int oldHeight = image.length;
    Pixel[][] downscaledImage = new Pixel[newHeight][newWidth];

    for (int row = 0; row < newHeight; row++) {
      for (int col = 0; col < newWidth; col++) {
        // Mapping pixel coordinates from the original image to the new image size
        double srcX = (double) col * oldWidth / newWidth;
        double srcY = (double) row * oldHeight / newHeight;

        // Use floor and ceiling for rounding the floating-point values to nearest pixels
        int x1 = (int) Math.floor(srcX);
        int y1 = (int) Math.floor(srcY);
        int x2 = Math.min(x1 + 1, oldWidth - 1);
        int y2 = Math.min(y1 + 1, oldHeight - 1);

        // Retrieve the four surrounding pixels
        Pixel p11 = image[y1][x1];
        Pixel p12 = image[y1][x2];
        Pixel p21 = image[y2][x1];
        Pixel p22 = image[y2][x2];

        // Compute the average color of the four neighboring pixels
        int red = (p11.get(0) + p12.get(0) + p21.get(0) + p22.get(0)) / 4;
        int green = (p11.get(1) + p12.get(1) + p21.get(1) + p22.get(1)) / 4;
        int blue = (p11.get(2) + p12.get(2) + p21.get(2) + p22.get(2)) / 4;

        downscaledImage[row][col] = new Pixel(red, green, blue);
      }
    }

    this.image = downscaledImage;  // Update the image to the downscaled version
  }

  /**
   * applies the dithering effect to the current image, reducing it to a greyscale.
   */
  @Override
  public void applyDithering() {
    applyDithering(100);
  }

  /**
   * Applies Floyd-Steinberg dithering to the current image, reducing it to a greyscale image.
   */
  @Override
  public void applyDithering(double p) {
    if (image == null) {
      System.out.println("No image loaded.");
      return;
    }

    if (p < 0 || p > 100) {
      throw new IllegalArgumentException("Split Percentage must be between 0 and 100.");
    }

    Pixel[][] ditheredImage = computeDitheredImage();

    // replace pixels in split with dithered Image
    int width = image[0].length;
    int splitPoint = (int) (width * (p / 100));
    int height = image.length;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          image[row][col] = ditheredImage[row][col];
        } else if (col == splitPoint) {
          image[row][col] = new Pixel(0, 0, 0);
        } else {
          // leave the same image
        }
      }
    }
  }

  private Pixel[][] computeDitheredImage() {
    Pixel[][] intensity = this.getIntensity();

    for (int row = 0; row < intensity.length; row++) {
      for (int col = 0; col < intensity[0].length; col++) {
        Pixel pixel = intensity[row][col];

        int oldColor = pixel.get(0);
        int newColor = oldColor < 128 ? 0 : 255;

        pixel.set(0, newColor);
        pixel.set(1, newColor);
        pixel.set(2, newColor);

        int error = oldColor - newColor;

        if (col + 1 < intensity[0].length) {
          adjustPixel(intensity[row][col + 1], (7 * error) / 16);
        }
        if (row + 1 < intensity.length) {
          adjustPixel(intensity[row + 1][col], (5 * error) / 16);
        }
        if (row + 1 < intensity.length && col > 0) {
          adjustPixel(intensity[row + 1][col - 1],(3 * error) / 16);
        }
        if (row + 1 < intensity.length && col + 1 < intensity[0].length) {
          adjustPixel(intensity[row + 1][col + 1], (1 * error) / 16);
        }
      }
    }
    return intensity;
  }


  /**
   * Adjusts the grayscale value of a pixel by adding the specified error,
   * ensuring it stays within [0, 255].
   *
   * @param pixel the pixel to adjust
   * @param error the error value to add
   */
  private void adjustPixel(Pixel pixel, int error) {
    int newGray = clamp(pixel.get(0) + error);
    pixel.set(0, newGray);
    pixel.set(1, newGray);
    pixel.set(2, newGray);
  }
}