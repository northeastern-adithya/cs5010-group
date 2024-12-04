package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;
import model.ImageMaskProcessor;
import model.ImageModel;
import model.MaskInterface;
import model.Pixel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import view.ViewInterface;

/**
 * The ImageController class is responsible for parsing script commands and invoking the
 * corresponding methods on the ImageModel to perform image processing tasks. The class maintains a
 * store of images in memory, allowing for multiple image transformations and operations to be
 * applied across different images.
 */
public class ImageController implements ControllerInterface {


  public ImageModel model;
  public MaskInterface mask1;
  public final Map<String, Pixel[][]> imageStore;
  private BufferedImage histogramImage;

  /**
   * Constructs an ImageController with a specified ImageModel.
   *
   * @param model the ImageModel used to perform image operations
   */
  public ImageController(ImageModel model, ViewInterface view) {
    this.model = model;
    this.imageStore = new HashMap<>();
  }

  /**
   * Reads and executes commands from a specified script file.
   *
   * @param filepath the path to the script file
   */
  @Override
  public void runScript(String filepath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.trim().startsWith("#")) {
          processCommand(line.trim());
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading script file: " + filepath);
      e.printStackTrace();
    }
  }

  /**
   * Processes individual commands from the script and performs the appropriate action on the
   * ImageModel.
   *
   * @param command the command to be processed
   */
  @Override
  public void processCommand(String command) {
    String[] tokens = command.split(" ");
    if (tokens.length == 0) {
      return;
    }

    try {
      handleProcessCommand(tokens);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void handleProcessCommand(String[] tokens) {
    switch (tokens[0]) {
      case "load":
        handleLoadCommand(tokens);
        break;
      case "save":
        handleSaveCommand(tokens);
        break;
      case "red-component":
        handleColorComponentCommand(tokens, "red");
        break;
      case "green-component":
        handleColorComponentCommand(tokens, "green");
        break;
      case "blue-component":
        handleColorComponentCommand(tokens, "blue");
        break;
      case "value-component":
        handleValueComponentCommand(tokens);
        break;
      case "intensity-component":
        handleIntensityComponentCommand(tokens);
        break;
      case "luma-component":
        handleLumaComponentCommand(tokens);
        break;
      case "horizontal-flip":
        handleFlipCommand(tokens, true);
        break;
      case "vertical-flip":
        handleFlipCommand(tokens, false);
        break;
      case "brighten":
        handleBrightenCommand(tokens);
        break;
      case "rgb-split":
        handleRgbSplitCommand(tokens);
        break;
      case "rgb-combine":
        handleRgbCombineCommand(tokens);
        break;
      case "blur":
        handleBlurCommand(tokens);
        break;
      case "greyscale":
        handleGreyscaleCommand(tokens);
        break;
      case "sharpen":
        handleSharpenCommand(tokens);
        break;
      case "histogram":
        handleHistogramCommand(tokens);
        break;
      case "color-correct":
        handleColorCorrectCommand(tokens);
        break;
      case "sepia":
        handleSepiaCommand(tokens);
        break;
      case "levels-adjust":
        handleLevelsAdjustCommand(tokens);
        break;
      case "compress":
        handleCompressCommand(tokens);
        break;
      case "downscale":
        handleDownscale(tokens);
        break;
      case "dither":
        handleDitherCommand(tokens);
        break;
      default:
        System.out.println("Unknown command: " + tokens[0]);
    }
  }

  // Helper methods for command processing
  private void handleLoadCommand(String[] tokens) {
    if (tokens.length == 3) {
      String filepath = tokens[1];
      String imageName = tokens[2];
      Pixel[][] image = ImageUtil.loadImage(filepath);
      imageStore.put(imageName, image);
      System.out.println("Loaded image: " + imageName);
    } else {
      System.out.println("Invalid load command format.");
    }
  }

  private void handleSaveCommand(String[] tokens) {
    if (tokens.length == 3) {
      String savePath = tokens[1];
      String imageName = tokens[2];

      if ("histogram".equals(imageName)) {
        saveHistogramImage(savePath);
      } else {
        saveImage(savePath, imageName);
      }
    } else {
      System.out.println("Invalid save command format.");
    }
  }

  private void saveHistogramImage(String savePath) {
    if (histogramImage != null) {
      try {
        String extension = savePath.substring(savePath.lastIndexOf('.') + 1).toLowerCase();
        ImageIO.write(histogramImage, extension, new File(savePath));
        System.out.println("Histogram saved as: " + savePath);
      } catch (IOException e) {
        System.err.println("Error saving histogram to " + savePath);
        e.printStackTrace();
      }
    } else {
      System.out.println("No histogram to save.");
    }
  }

  private void saveImage(String savePath, String imageName) {
    Pixel[][] image = imageStore.get(imageName);
    if (image != null) {
      model.saveImage(savePath, image);
      System.out.println("Saved image as: " + savePath);
    } else {
      System.out.println("Image not found: " + imageName);
    }
  }


  private boolean shouldApplyMask(Pixel maskPixel) {
    return maskPixel.get(0) == 0 && maskPixel.get(1) == 0 && maskPixel.get(2) == 0;
  }

  private void handleColorComponentCommand(String[] tokens, String component) {
    if (tokens.length < 3 || tokens.length > 4) {
      System.out.println("Invalid command: " + String.join(" ", tokens));
      return;
    }

    String sourceImageName = tokens[1];
    String targetImageName = (tokens.length == 3) ? tokens[2] : tokens[3];

    Pixel[][] sourceImage = imageStore.get(sourceImageName);
    if (sourceImage == null) {
      System.out.println("Image not found: " + sourceImageName);
      return;
    }

    Pixel[][] mask = null;
    if (tokens.length == 4) {
      String maskImageName = tokens[2];
      mask = imageStore.get(maskImageName);
      if (mask == null) {
        System.out.println("Mask not found: " + maskImageName);
        return;
      }
      if (mask.length != sourceImage.length || mask[0].length != sourceImage[0].length) {
        System.out.println("Mask size must match the image size.");
        return;
      }
    }

    int rows = sourceImage.length;
    int cols = sourceImage[0].length;
    Pixel[][] resultImage = new Pixel[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int red = sourceImage[i][j].get(0);
        int green = sourceImage[i][j].get(1);
        int blue = sourceImage[i][j].get(2);

        if (mask == null || shouldApplyMask(mask[i][j])) {
          switch (component) {
            case "red":
              resultImage[i][j] = new Pixel(red, red, red);
              break;
            case "green":
              resultImage[i][j] = new Pixel(green, green, green);
              break;
            case "blue":
              resultImage[i][j] = new Pixel(blue, blue, blue);
              break;
            default:
              System.out.println("Invalid component: " + component);
              return;
          }
        } else {
          resultImage[i][j] = sourceImage[i][j];
        }
      }
    }

    imageStore.put(targetImageName, resultImage);

    System.out.println("Visualized " + component + " component for " + sourceImageName
        + (mask != null ? " with masking" : "")
        + " and stored as " + targetImageName);
  }

  private void handleValueComponentCommand(String[] tokens) {
    if (tokens.length == 3 || tokens.length == 4) {
      String imageName = tokens[1];
      String destName = (tokens.length == 3) ? tokens[2] : tokens[3];
      Pixel[][] image = imageStore.get(imageName);

      if (image != null) {
        model.setImage(image);

        if (tokens.length == 3) {
          // Apply value component to the entire image
          Pixel[][] valueImage = model.getValue();
          imageStore.put(destName, valueImage);
          System.out.println("Saved value component as: " + destName);
        } else if (tokens.length == 4) {
          // Apply value component with masking
          String maskImageName = tokens[2];
          Pixel[][] mask = imageStore.get(maskImageName);

          if (mask == null) {
            System.out.println("Mask not found: " + maskImageName);
            return;
          }

          if (mask.length != image.length || mask[0].length != image[0].length) {
            System.out.println("Mask size must match the image size.");
            return;
          }

          mask1 = new ImageMaskProcessor(model.getImage());
          mask1.applyValueWithMask(mask);
          model.setImage(
              mask1.getImage());

          Pixel[][] valueImage = model.getImage();
          imageStore.put(destName, valueImage);
          System.out.println("Saved value component with masking as: " + destName);
        }
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for value-component.");
    }
  }

  private void handleIntensityComponentCommand(String[] tokens) {
    if (tokens.length == 3 || tokens.length == 4) {
      String imageName = tokens[1];
      String destName = (tokens.length == 3) ? tokens[2] : tokens[3];
      Pixel[][] image = imageStore.get(imageName);

      if (image != null) {
        model.setImage(image);

        if (tokens.length == 3) {
          // Apply regular intensity operation
          model.getIntensity();
        } else if (tokens.length == 4) {
          // Apply partial intensity operation with mask
          String maskImage = tokens[2];
          Pixel[][] mask = imageStore.get(maskImage);

          if (mask == null) {
            System.out.println("Mask not found: " + maskImage);
            return;
          }

          mask1 = new ImageMaskProcessor(model.getImage());
          mask1.applyIntensityWithMask(mask);
          model.setImage(mask1.getImage());
        }

        Pixel[][] intensityImage = model.getImage();
        imageStore.put(destName, intensityImage);
        System.out.println("Intensity component image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for intensity.");
    }
  }

  private void handleLumaComponentCommand(String[] tokens) {
    if (tokens.length == 3 || tokens.length == 4) {
      String imageName = tokens[1];
      String destName = (tokens.length == 3) ? tokens[2] : tokens[3];
      Pixel[][] image = imageStore.get(imageName);

      if (image != null) {
        model.setImage(image);

        if (tokens.length == 3) {
          // Apply regular luma
          model.getLuma();
        } else if (tokens.length == 4) {
          // Apply partial luma with mask
          String maskImage = tokens[2];
          Pixel[][] mask = imageStore.get(maskImage);

          if (mask == null) {
            System.out.println("Mask not found: " + maskImage);
            return;
          }

          mask1 = new ImageMaskProcessor(model.getImage());
          mask1.applyLumaWithMask(mask);
          model.setImage(mask1.getImage());
        }

        Pixel[][] lumaImage = model.getImage();
        imageStore.put(destName, lumaImage);
        System.out.println("Luma component image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for luma.");
    }
  }

  private void handleFlipCommand(String[] tokens, boolean isHorizontal) {
    if (tokens.length == 3) {
      String imageName = tokens[1];
      String outputName = tokens[2];
      Pixel[][] image = imageStore.get(imageName);
      if (image != null) {
        model.setImage(image);
        if (isHorizontal) {
          model.flipHorizontally();
        } else {
          model.flipVertically();
        }
        Pixel[][] flippedImage = model.getImage();
        imageStore.put(outputName, flippedImage);
        System.out.println(
            "Flipped image " + (isHorizontal ? "horizontally" : "vertically") + " and saved as: "
                + outputName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    }
  }

  private void handleBrightenCommand(String[] tokens) {
    if (tokens.length == 4) {
      int amount = Integer.parseInt(tokens[1]);
      String imageName = tokens[2];
      String destName = tokens[3];
      Pixel[][] image = imageStore.get(imageName);

      if (image != null) {
        if (amount > 0) {
          model.brighten(amount);
          System.out.println("Brightened image by " + amount);
        } else if (amount < 0) {
          model.darken(Math.abs(amount));
          System.out.println("Darkened image by " + Math.abs(amount));
        }

        Pixel[][] adjustedImage = model.getImage();
        imageStore.put(destName, adjustedImage);
        System.out.println("Image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid adjust-brightness command format.");
    }
  }

  private void handleRgbSplitCommand(String[] tokens) {
    if (tokens.length == 5) {
      String imageName = tokens[1];
      String redDest = tokens[2];
      String greenDest = tokens[3];
      String blueDest = tokens[4];
      model.splitChannels();
      imageStore.put(redDest, model.getRedChannelImage());
      imageStore.put(greenDest, model.getGreenChannelImage());
      imageStore.put(blueDest, model.getBlueChannelImage());
      System.out.println("RGB split saved as: " + redDest + ", " + greenDest + ", " + blueDest);
    }
  }

  private void handleRgbCombineCommand(String[] tokens) {
    if (tokens.length == 5) {
      String destName = tokens[1];
      String redImageName = tokens[2];
      String greenImageName = tokens[3];
      String blueImageName = tokens[4];

      Pixel[][] redImage = imageStore.get(redImageName);
      Pixel[][] greenImage = imageStore.get(greenImageName);
      Pixel[][] blueImage = imageStore.get(blueImageName);

      if (redImage != null && greenImage != null && blueImage != null) {
        model.combineChannels(model.extractChannel(redImage, 0),
            model.extractChannel(greenImage, 1), model.extractChannel(blueImage, 2));
        Pixel[][] combinedImage = model.getImage();
        imageStore.put(destName, combinedImage);
        System.out.println("RGB combined image saved as: " + destName);
      } else {
        System.out.println("One or more component images not found.");
      }
    }
  }

  private void handleBlurCommand(String[] tokens) {
    if (tokens.length == 3 || tokens.length == 4 || tokens.length == 5) {
      String imageName = tokens[1];
      String destName = (tokens.length == 3) ? tokens[2] : tokens[3];
      Pixel[][] image = imageStore.get(imageName);
      if (image != null) {
        model.setImage(image);
        if (tokens.length == 3) {
          // Apply regular blur
          model.applyBlur();
        } else if (tokens.length == 4) {
          // Apply partial blur with mask
          String maskImage = tokens[2];
          Pixel[][] mask = imageStore.get(maskImage);

          if (mask == null) {
            System.out.println("Mask not found: " + maskImage);
            return;
          }

          mask1 = new ImageMaskProcessor(model.getImage());
          mask1.applyBlurWithMask(mask);
          model.setImage(mask1.getImage());
        } else {
          double splitPercentage = Double.parseDouble(tokens[4]);
          model.applyBlur(splitPercentage);
        }
        Pixel[][] blurredImage = model.getImage();
        imageStore.put(destName, blurredImage);
        System.out.println("Blurred image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for blur.");
    }
  }

  private void handleGreyscaleCommand(String[] tokens) {
    if (tokens.length == 3 || tokens.length == 4 || tokens.length == 5) {
      String imageName = tokens[1];
      String destName = (tokens.length == 3) ? tokens[2] : tokens[3];
      Pixel[][] image = imageStore.get(imageName);
      if (image != null) {
        model.setImage(image);
        if (tokens.length == 3) {
          // Apply regular greyscale
          model.applyGreyScale();
        } else if (tokens.length == 4) {
          // Apply greyscale with a mask
          String maskImage = tokens[2];
          Pixel[][] mask = imageStore.get(maskImage);

          if (mask == null) {
            System.out.println("Mask not found: " + maskImage);
            return;
          }

          mask1 = new ImageMaskProcessor(model.getImage());
          mask1.applyGreyScaleWithMask(mask);
          model.setImage(mask1.getImage());
        } else {
          double splitPercentage = Double.parseDouble(tokens[4]);
          model.applyGreyScale(splitPercentage);
        }
        Pixel[][] greyScaleImage = model.getImage();
        imageStore.put(destName, greyScaleImage);
        System.out.println("Greyscale image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for greyscale.");
    }
  }

  private void handleSharpenCommand(String[] tokens) {
    if (tokens.length == 3 || tokens.length == 4 || tokens.length == 5) {
      String imageName = tokens[1];
      String destName = (tokens.length == 3) ? tokens[2] : tokens[3];
      Pixel[][] image = imageStore.get(imageName);
      if (image != null) {
        model.setImage(image);
        if (tokens.length == 3) {
          // Apply regular sharpen
          model.applySharpen();
        } else if (tokens.length == 4) {
          // Apply sharpen with mask
          String maskImage = tokens[2];
          Pixel[][] mask = imageStore.get(maskImage);

          if (mask == null) {
            System.out.println("Mask not found: " + maskImage);
            return;
          }

          mask1 = new ImageMaskProcessor(model.getImage());
          mask1.applySharpenWithMask(mask);
          model.setImage(mask1.getImage());
        } else {
          double splitPercentage = Double.parseDouble(tokens[4]);
          model.applySharpen(splitPercentage);
        }
        Pixel[][] sharpenImage = model.getImage();
        imageStore.put(destName, sharpenImage);
        System.out.println("Sharpen image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for sharpen.");
    }
  }

  private void handleHistogramCommand(String[] tokens) {
    if (tokens.length == 3) {
      String imageName = tokens[1];
      String destImageName = tokens[2];
      Pixel[][] image = imageStore.get(imageName);

      if (image != null) {
        model.setImage(image);
        histogramImage = model.calculateHistogram();
        System.out.println("Histogram calculated. You can now save it using the save command.");
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid histogram command format.");
    }
  }

  private void handleColorCorrectCommand(String[] tokens) {
    if (tokens.length == 3 || tokens.length == 5) {
      String imageName = tokens[1];
      String destImageName = tokens[2];
      Pixel[][] image = imageStore.get(imageName);

      if (image != null) {
        model.setImage(image);
        if (tokens.length == 3) {
          model.colorCorrect();
        } else {
          double splitPercentage = Double.parseDouble(tokens[4]);
          model.colorCorrect(splitPercentage);
        }
        Pixel[][] correctedImage = model.getImage();
        imageStore.put(destImageName, correctedImage);
        System.out.println("Color-corrected image saved as: " + destImageName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for color correction.");
    }
  }

  private void handleSepiaCommand(String[] tokens) {
    if (tokens.length == 3 || tokens.length == 4 || tokens.length == 5) {
      String imageName = tokens[1];
      String destName = (tokens.length == 3) ? tokens[2] : tokens[3];
      Pixel[][] image = imageStore.get(imageName);
      if (image != null) {
        model.setImage(image);
        if (tokens.length == 3) {
          // Apply regular sepia
          model.applySepia();
        } else if (tokens.length == 4) {
          // Apply sepia with a mask
          String maskImage = tokens[2];
          Pixel[][] mask = imageStore.get(maskImage);

          if (mask == null) {
            System.out.println("Mask not found: " + maskImage);
            return;
          }

          mask1 = new ImageMaskProcessor(model.getImage());
          mask1.applySepiaWithMask(mask);
          model.setImage(mask1.getImage());
        } else {
          double splitPercentage = Double.parseDouble(tokens[4]);
          model.applySepia(splitPercentage);
        }
        Pixel[][] sepiaImage = model.getImage();
        imageStore.put(destName, sepiaImage);
        System.out.println("Sepia image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for sepia.");
    }
  }

  private void handleLevelsAdjustCommand(String[] tokens) {
    if (tokens.length == 6 || tokens.length == 8) {
      int shadow = Integer.parseInt(tokens[1]);
      int mid = Integer.parseInt(tokens[2]);
      int highlight = Integer.parseInt(tokens[3]);
      String imageName = tokens[4];
      String destName = tokens[5];
      Pixel[][] image = imageStore.get(imageName);

      if (image != null) {
        model.setImage(image);
        if (tokens.length == 6) {
          model.levelsAdjust(shadow, mid, highlight);
        } else {
          double splitPercentage = Double.parseDouble(tokens[7]);
          model.levelsAdjust(shadow, mid, highlight, splitPercentage);
        }
        Pixel[][] adjustedImage = model.getImage();
        imageStore.put(destName, adjustedImage);
        System.out.println(
            "Levels adjusted for image: " + imageName + " and saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for levels adjustment.");
    }
  }

  private void handleCompressCommand(String[] tokens) {
    if (tokens.length == 4) {
      double percentage = Double.parseDouble(tokens[1]);
      String imageName = tokens[2];
      String destName = tokens[3];

      Pixel[][] image = imageStore.get(imageName);

      if (image != null) {
        model.setImage(image);
        model.compressImage(percentage);
        Pixel[][] compressedImage = model.getImage();
        imageStore.put(destName, compressedImage);
        System.out.println(
            "Image compressed with threshold: " + percentage + " and saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println(
          "Invalid compress command format. Use: compress <threshold> "
              + "<image-name> <dest-image-name>");
    }
  }

  private void handleDownscale(String[] tokens) {
    if (tokens.length == 5) {
      int newWidth = Integer.parseInt(tokens[1]);
      int newHeight = Integer.parseInt(tokens[2]);
      String imageName = tokens[3];
      String destName = tokens[4];

      Pixel[][] image = imageStore.get(imageName);
      if (image != null) {
        model.setImage(image);
        model.downscaleImage(newWidth, newHeight);
        Pixel[][] downscaledImage = model.getImage();
        imageStore.put(destName, downscaledImage);
        System.out.println("Downscaled image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid downscale command format.");
    }
  }

  private void handleDitherCommand(String[] tokens) {
    if (tokens.length == 3 || (tokens.length == 5 && tokens[3].equals("split"))) {
      String imageName = tokens[1];
      String destName = tokens[2];
      Pixel[][] image = imageStore.get(imageName);
      if (image != null) {
        if (tokens.length == 3) {
          model.setImage(image);
          model.applyDithering();
        } else {
          double splitPercentage = Double.parseDouble(tokens[4]);
          model.setImage(image);
          model.applyDithering(splitPercentage);
        }
        Pixel[][] ditheredImage = model.getImage();
        imageStore.put(destName, ditheredImage);
        System.out.println("Dithered image saved as: " + destName);
      } else {
        System.out.println("Image not found: " + imageName);
      }
    } else {
      System.out.println("Invalid number of arguments for dither.");
    }
  }
}