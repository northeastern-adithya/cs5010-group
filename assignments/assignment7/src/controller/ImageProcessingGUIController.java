package controller;

import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

import model.ImageModelV2;
import model.Pixel;
import view.ImageProcessingGUI;
import model.ImageModel;
import java.io.File;

/**
 * The ImageProcessingGUIController class is responsible for handling user interactions from the
 * ImageProcessingGUI view, processing image manipulation commands, and updating the ImageModel and
 * ImageProcessingGUI accordingly. It implements the Features interface to provide various image
 * manipulation features such as applying effects, loading, and saving images.
 */
public class ImageProcessingGUIController implements Features {

  private ImageModelV2 model;
  private ImageProcessingGUI view;

  /**
   * Constructs an ImageProcessingGUIController with the specified view, controller, and model.
   *
   * @param view       The ImageProcessingGUI instance for interacting with the user interface.
   * @param controller The ImageController instance for controlling image processing logic.
   * @param model      The ImageModel instance that holds and processes the image data.
   */
  public ImageProcessingGUIController(ImageProcessingGUI view, ImageController controller,
      ImageModelV2 model) {
    this.view = view;
    this.model = model;
  }

  /**
   * Checks if an image is loaded in the view.
   *
   * @return true if an image is loaded, false otherwise.
   */
  private boolean isImageLoaded() {
    if (view.getCurrentImageName() == null) {
      view.displayError("No image loaded.");
      return false;
    }
    return true;
  }

  /**
   * Retrieves the split percentage value from the user input, validating it.
   *
   * @return The split percentage value, or 100 if the input is invalid.
   */
  private int getSplitPercentage() {
    int splitPercentage = 100;
    String splitPercentageStr = view.getSplitPercentageFromUser();

    try {
      if (splitPercentageStr != null && !splitPercentageStr.isEmpty()) {
        splitPercentage = Integer.parseInt(splitPercentageStr);
        if (splitPercentage < 0 || splitPercentage > 100) {
          throw new NumberFormatException("Split percentage must be between 0 and 100.");
        }
      }
    } catch (NumberFormatException e) {
      view.displayError("Invalid split percentage value. Using default value (100%).");
    }

    return splitPercentage;
  }

  /**
   * Prompts the user to input values for shadow, mid, and highlight adjustments.
   *
   * @return An array containing shadow, mid, and highlight values, or null if input is invalid.
   */
  private int[] getShadowMidHighlight() {
    String shadow = JOptionPane.showInputDialog(view.getFrame(), "Enter shadow amount:");
    String mid = JOptionPane.showInputDialog(view.getFrame(), "Enter mid amount:");
    String highlight = JOptionPane.showInputDialog(view.getFrame(), "Enter highlight amount:");

    try {
      int shadowValue = Integer.parseInt(shadow);
      int midValue = Integer.parseInt(mid);
      int highlightValue = Integer.parseInt(highlight);

      // Validate values are within the range [0, 255]
      if (shadowValue < 0 || shadowValue > 255 ||
          midValue < 0 || midValue > 255 ||
          highlightValue < 0 || highlightValue > 255) {
        view.displayError("Shadow, mid, and highlight values must be between 0 and 255.");
        return null;
      }

      // Ensure the values are in ascending order (shadow ≤ mid ≤ highlight)
      if (shadowValue > midValue || midValue > highlightValue) {
        view.displayError("Shadow, mid, and highlight values must be in ascending order.");
        return null;
      }

      return new int[]{shadowValue, midValue, highlightValue};
    } catch (NumberFormatException e) {
      view.displayError("Invalid input for shadow, mid, or highlight values.");
      return null;
    }
  }


  /**
   * Helper method to apply an effect to the image by executing a given effect method.
   *
   * @param effectMethod The effect method to apply to the image.
   */
  private void applyEffectToImage(Runnable effectMethod) {
    if (isImageLoaded()) {
      effectMethod.run();
      Pixel[][] image = model.getImage();
      if (image != null) {
        view.displayImage(image);

        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }
      } else {
        view.displayError("Image processing failed.");
      }
    }
  }

  /**
   * Loads an image from a file and displays it, as well as its histogram.
   */
  @Override
  public void loadImage() {
    String imagePath = view.load();
    if (imagePath != null) {
      String currentImageName = new File(imagePath).getName().replaceFirst("[.][^.]+$",
          "");
      view.setCurrentImageName(currentImageName);

      Pixel[][] image = ImageUtil.loadImage(imagePath);
      if (image != null) {
        model.setImage(image);

        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }

        view.displayImage(imagePath);
      } else {
        view.displayError("Error loading the image.");
      }
    }
  }

  /**
   * Saves the current image to a file.
   */
  @Override
  public void saveImage() {
    String savePath = view.save();
    if (savePath != null && view.getCurrentImageName() != null) {
      Pixel[][] image = model.getImage();

      if (image != null) {
        model.saveImage(savePath, image);
        view.displayMessage("Image saved successfully.");
      } else {
        view.displayError("No image loaded to save.");
      }
    } else {
      view.displayError("No image loaded to save.");
    }
  }

  /**
   * Applies a sepia effect to the current image.
   */
  @Override
  public void applySepiaEffect() {
    if (isImageLoaded()) {
      int splitPercentage = getSplitPercentage();
      applyEffectToImage(() -> model.applySepia(splitPercentage));
    }
  }

  /**
   * Applies a blur effect to the current image.
   */
  @Override
  public void applyBlurEffect() {
    if (isImageLoaded()) {
      int splitPercentage = getSplitPercentage();
      applyEffectToImage(() -> model.applyBlur(splitPercentage));
    }
  }

  /**
   * Applies a sharpen effect to the current image.
   */
  @Override
  public void applySharpenEffect() {
    if (isImageLoaded()) {
      int splitPercentage = getSplitPercentage();
      applyEffectToImage(() -> model.applySharpen(splitPercentage));
    }
  }

  /**
   * Applies a grayscale effect to the current image.
   */
  @Override
  public void applyGreyscaleEffect() {
    if (isImageLoaded()) {
      int splitPercentage = getSplitPercentage();
      applyEffectToImage(() -> model.applyGreyScale(splitPercentage));
    }
  }

  /**
   * Brightens or darkens the image based on user input.
   */
  @Override
  public void applyBrightenEffect() {
    if (isImageLoaded()) {
      String amount = JOptionPane.showInputDialog(view.getFrame(),
          "Enter amount to brighten (0-255 to brighten, -255 to 0 to darken):");
      if (amount != null && !amount.isEmpty()) {
        try {
          int brightenAmount = Integer.parseInt(amount);

          // Validate for brightening (0 to 255)
          if (brightenAmount >= 0 && brightenAmount <= 255) {
            model.brighten(brightenAmount);
          }
          // Validate for darkening (-255 to 0)
          else if (brightenAmount >= -255 && brightenAmount < 0) {
            model.darken(Math.abs(brightenAmount));
          } else {
            view.displayError(
                "Amount to brighten must be between 0 and 255, or darken must be "
                    + "between -255 and 0.");
            return;
          }

          // Update the image after brightening/darkening
          Pixel[][] image = model.getImage();
          if (image != null) {
            view.displayImage(image);
            BufferedImage histogram = model.calculateHistogram();
            if (histogram != null) {
              view.displayHistogram(histogram);
            }
          }
        } catch (NumberFormatException e) {
          view.displayError("Invalid amount to brighten. Please enter a valid integer.");
        }
      }
    }
  }

  /**
   * Applies a vertical flip to the image.
   */
  @Override
  public void applyVerticalFlip() {
    if (isImageLoaded()) {
      model.flipVertically();
      Pixel[][] flippedImage = model.getImage();
      if (flippedImage != null) {
        view.displayImage(flippedImage);
        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }
      } else {
        view.displayError("Vertical flip failed.");
      }
    }
  }

  /**
   * Applies a horizontal flip to the image.
   */
  @Override
  public void applyHorizontalFlip() {
    if (isImageLoaded()) {
      model.flipHorizontally();
      Pixel[][] flippedImage = model.getImage();
      if (flippedImage != null) {
        view.displayImage(flippedImage);
        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }
      } else {
        view.displayError("Horizontal flip failed.");
      }
    }
  }

  /**
   * Adjusts the levels (shadows, midtones, and highlights) of the image.
   */
  @Override
  public void applyLevelsAdjust() {
    if (isImageLoaded()) {
      int splitPercentage = getSplitPercentage();
      int[] values = getShadowMidHighlight();
      if (values != null) {
        model.levelsAdjust(values[0], values[1], values[2], splitPercentage);
        Pixel[][] adjustedImage = model.getImage();
        if (adjustedImage != null) {
          view.displayImage(adjustedImage);
          BufferedImage histogram = model.calculateHistogram();
          if (histogram != null) {
            view.displayHistogram(histogram);
          }
        }
      }
    }
  }

  /**
   * Applies color correction to the image.
   */
  @Override
  public void applyColorCorrection() {
    if (isImageLoaded()) {
      int splitPercentage = getSplitPercentage();
      model.colorCorrect(splitPercentage);
      Pixel[][] image = model.getImage();
      if (image != null) {
        view.displayImage(image);
        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }
      } else {
        view.displayError("No image loaded.");
      }
    }
  }

  /**
   * Compresses the image based on a user-defined threshold.
   */
  @Override
  public void applyCompress() {
    if (isImageLoaded()) {
      String threshold = JOptionPane.showInputDialog(view.getFrame(),
          "Enter threshold amount (0-100):");
      if (threshold != null && !threshold.isEmpty()) {
        try {
          int thresholdValue = Integer.parseInt(threshold);

          // Validate that the threshold is between 0 and 100
          if (thresholdValue < 0 || thresholdValue > 100) {
            view.displayError("Threshold value must be between 0 and 100.");
            return;
          }

          model.compressImage(thresholdValue);
          Pixel[][] compressedImage = model.getImage();
          if (compressedImage != null) {
            view.displayImage(compressedImage);
            BufferedImage histogram = model.calculateHistogram();
            if (histogram != null) {
              view.displayHistogram(histogram);
            }
          } else {
            view.displayError("Compression failed.");
          }
        } catch (NumberFormatException e) {
          view.displayError("Invalid threshold value. Please enter a number between 0 and 100.");
        }
      }
    }
  }


  /**
   * Downscales the image based on user-defined width and height.
   */
  @Override
  public void applyDownscale() {
    if (isImageLoaded()) {
      String newWidth = JOptionPane.showInputDialog(view.getFrame(), "Enter new width:");
      String newHeight = JOptionPane.showInputDialog(view.getFrame(), "Enter new height:");
      if (newWidth != null && newHeight != null) {
        model.downscaleImage(Integer.parseInt(newWidth), Integer.parseInt(newHeight));
        Pixel[][] downscaledImage = model.getImage();
        if (downscaledImage != null) {
          view.displayImage(downscaledImage);
          BufferedImage histogram = model.calculateHistogram();
          if (histogram != null) {
            view.displayHistogram(histogram);
          }
        } else {
          view.displayError("Downscaling failed.");
        }
      }
    }
  }

  /**
   * Visualizes the red component of the image.
   */
  @Override
  public void visualizeRedComponent() {
    if (isImageLoaded()) {
      model.splitChannels();
      Pixel[][] componentImage = model.getRedChannelImage();
      if (componentImage != null) {
        view.displayImage(componentImage);
        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }
      } else {
        view.displayError("Red Component failed.");
      }
    }
  }

  /**
   * Visualizes the green component of the image.
   */
  @Override
  public void visualizeGreenComponent() {
    if (isImageLoaded()) {
      model.splitChannels();
      Pixel[][] componentImage = model.getGreenChannelImage();
      if (componentImage != null) {
        view.displayImage(componentImage);
        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }
      } else {
        view.displayError("Green Component failed.");
      }
    }
  }

  /**
   * Visualizes the blue component of the image.
   */
  @Override
  public void visualizeBlueComponent() {
    if (isImageLoaded()) {
      model.splitChannels();
      Pixel[][] componentImage = model.getBlueChannelImage();
      if (componentImage != null) {
        view.displayImage(componentImage);
        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }
      } else {
        view.displayError("Blue Component failed.");
      }
    }
  }

  /**
   * Applies a dithering effect to the image.
   */
  @Override
  public void applyDither() {
    if (isImageLoaded()) {
      int splitPercentage = getSplitPercentage();
      model.applyDithering(splitPercentage);
      Pixel[][] ditheredImage = model.getImage();
      if (ditheredImage != null) {
        view.displayImage(ditheredImage);
        BufferedImage histogram = model.calculateHistogram();
        if (histogram != null) {
          view.displayHistogram(histogram);
        }
      } else {
        view.displayError("Dithering failed.");
      }
    }
  }
}
