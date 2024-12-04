package view;


import controller.ImageUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ImageController;
import controller.ImageProcessingGUIController;
import model.ImageImplementation;
import model.ImageModel;
import model.Pixel;

/**
 * The ImageProcessingGUI class provides the graphical user interface for the image processing
 * application. It allows the user to interact with the image processing features, such as loading,
 * saving, and applying various effects on the image.
 */
public class ImageProcessingGUI extends JFrame implements ImageProcessingView {

  private ImageProcessingGUIController guiController;

  private JFrame frame;
  private JLabel imageLabel;


  private String currentImageName; // Keeps track of the current image name

  private JPanel histogramPanel;  // Panel for displaying the histogram

  /**
   * Initializes the ImageProcessingGUI by setting up the layout, buttons, and GUI components.
   * Creates the controller and sets up action listeners for the buttons.
   */
  public ImageProcessingGUI() {
    JScrollPane scrollPane;
    JButton loadButton;
    JButton saveButton;
    JButton verticalFlipButton;
    JButton horizontalFlipButton;
    JButton greyscaleButton;
    JButton brightenButton;
    JButton blurButton;
    JButton sharpenButton;
    JButton sepiaButton;
    JButton levelsAdjustButton;
    JButton colorCorrectButton;
    JButton compressButton;
    JButton downscaleButton;
    JButton redButton;
    JButton greenButton;
    JButton blueButton;
    JButton ditherButton;
    ImageModel model = new ImageImplementation();
    ViewInterface viewInterface = this;
    ImageController imageController = new ImageController(model,
            viewInterface,new HashMap<>());

    // Set up the frame and layout
    frame = new JFrame("Image Processing Application");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 800);

    // Initialize the image label and scroll pane
    imageLabel = new JLabel();
    scrollPane = new JScrollPane(imageLabel);
    scrollPane.setPreferredSize(new Dimension(800, 600));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    // Histogram panel (for showing the histogram)
    histogramPanel = new JPanel();
    histogramPanel.setPreferredSize(new Dimension(500, 500));

    // Create a JSplitPane to split the image and histogram into two halves
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, histogramPanel);
    splitPane.setDividerLocation(
        500);  // Initial divider location, image takes up most of the space
    splitPane.setResizeWeight(0.7);  // Allow the image to take more space by default

    // Add the split pane to the frame
    frame.add(splitPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 4, 10, 10));
    frame.add(buttonPanel, BorderLayout.SOUTH);

    // Initialize buttons
    loadButton = new JButton("Load Image");
    saveButton = new JButton("Save Image");
    verticalFlipButton = new JButton("Vertical Flip");
    horizontalFlipButton = new JButton("Horizontal Flip");
    greyscaleButton = new JButton("Greyscale");
    brightenButton = new JButton("Brighten");
    blurButton = new JButton("Blur");
    sharpenButton = new JButton("Sharpen");
    sepiaButton = new JButton("Sepia");
    levelsAdjustButton = new JButton("Levels Adjust");
    colorCorrectButton = new JButton("Color Correct");
    compressButton = new JButton("Compress");
    downscaleButton = new JButton("Downscale");
    redButton = new JButton("Red");
    greenButton = new JButton("Green");
    blueButton = new JButton("Blue");
    ditherButton = new JButton("Dither");

    // Add buttons to the button panel
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(verticalFlipButton);
    buttonPanel.add(horizontalFlipButton);
    buttonPanel.add(greyscaleButton);
    buttonPanel.add(brightenButton);
    buttonPanel.add(blurButton);
    buttonPanel.add(sharpenButton);
    buttonPanel.add(sepiaButton);
    buttonPanel.add(levelsAdjustButton);
    buttonPanel.add(colorCorrectButton);
    buttonPanel.add(compressButton);
    buttonPanel.add(downscaleButton);
    buttonPanel.add(redButton);
    buttonPanel.add(greenButton);
    buttonPanel.add(blueButton);
    buttonPanel.add(ditherButton);

    // Initialize the GUI controller and pass the view and the main controller
    guiController = new ImageProcessingGUIController(this, imageController, model);

    // Set up action listeners for buttons
    loadButton.addActionListener(e -> guiController.loadImage());
    saveButton.addActionListener(e -> guiController.saveImage());
    sepiaButton.addActionListener(e -> guiController.applySepiaEffect());
    blurButton.addActionListener(e -> guiController.applyBlurEffect());
    sharpenButton.addActionListener(e -> guiController.applySharpenEffect());
    greyscaleButton.addActionListener(e -> guiController.applyGreyscaleEffect());
    brightenButton.addActionListener(e -> guiController.applyBrightenEffect());
    verticalFlipButton.addActionListener(e -> guiController.applyVerticalFlip());
    horizontalFlipButton.addActionListener(e -> guiController.applyHorizontalFlip());
    levelsAdjustButton.addActionListener(e -> guiController.applyLevelsAdjust());
    colorCorrectButton.addActionListener(e -> guiController.applyColorCorrection());
    compressButton.addActionListener(e -> guiController.applyCompress());
    downscaleButton.addActionListener(e -> guiController.applyDownscale());
    redButton.addActionListener(e -> guiController.visualizeRedComponent());
    greenButton.addActionListener(e -> guiController.visualizeGreenComponent());
    blueButton.addActionListener(e -> guiController.visualizeBlueComponent());
    ditherButton.addActionListener(e -> guiController.applyDither());
  }

  /**
   * Makes the GUI visible to the user.
   */
  public void showGUI() {
    frame.setVisible(true);
  }

  /**
   * Getter for the current image name.
   *
   * @return the name of the current image.
   */
  public String getCurrentImageName() {
    return currentImageName;
  }

  /**
   * Setter for the current image name. This updates the image name in the controller.
   *
   * @param imageName the new image name.
   */
  public void setCurrentImageName(String imageName) {
    this.currentImageName = imageName;
  }

  /**
   * Getter for the frame of the window.
   *
   * @return the JFrame of the application.
   */
  public JFrame getFrame() {
    return frame;
  }

  /**
   * Opens a file chooser to load an image file.
   *
   * @return the file path of the selected image, or null if the user cancels the selection.
   */
  @Override
  public String load() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(
        new FileNameExtensionFilter("Image Files", "ppm", "jpg", "png", "jpeg"));

    int result = fileChooser.showOpenDialog(frame);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      return file.getAbsolutePath();
    }
    return null;
  }


  /**
   * Opens a file chooser to save the image to a file.
   *
   * @return the file path where the image will be saved, or null if the user cancels the selection.
   */
  @Override
  public String save() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png",
        "ppm");
    fileChooser.setFileFilter(filter);
    int result = fileChooser.showSaveDialog(frame);
    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filePath = selectedFile.getAbsolutePath();
      if (!filePath.contains(".")) {
        String extension = JOptionPane.showInputDialog(frame,
            "Please enter the file extension (e.g., .png, .jpg, .ppm):");
        if (extension == null || extension.trim().isEmpty()) {
          extension = ".png"; // Default to PNG if no extension is given
        }
        if (!extension.startsWith(".")) {
          extension = "." + extension;
        }
        filePath = filePath + extension;
      }
      return filePath;
    }
    return null;
  }

  /**
   * Displays an error message in a dialog box.
   *
   * @param error the error message to be displayed.
   */
  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays a success message in a dialog box.
   *
   * @param message the success message to be displayed.
   */
  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(frame, message);
  }

  /**
   * Displays an image represented by a 2D array of pixels.
   *
   * @param imageData the pixel data of the image to display.
   */
  public void displayImage(Pixel[][] imageData) {
    if (imageData == null || imageData.length == 0 || imageData[0].length == 0) {
      displayError("Invalid image data.");
      return;
    }

    try {
      int width = imageData[0].length;
      int height = imageData.length;
      BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int red = imageData[i][j].get(0);
          int green = imageData[i][j].get(1);
          int blue = imageData[i][j].get(2);
          int rgb = (red << 16) | (green << 8) | blue;
          img.setRGB(j, i, rgb);
        }
      }

      ImageIcon icon = new ImageIcon(img);
      imageLabel.setIcon(icon);
      imageLabel.revalidate();
      imageLabel.repaint();
    } catch (Exception e) {
      e.printStackTrace();
      displayError("Error displaying the image.");
    }
  }


  /**
   * Displays an image from a given file path.
   *
   * @param imagePath the path to the image file.
   */
  @Override
  public void displayImage(String imagePath) {
    try {
      File imgFile = new File(imagePath);
      if (!imgFile.exists()) {
        displayError("Image file does not exist at path: " + imagePath);
        return;
      }

      if (imagePath.endsWith(".ppm")) {
        // Load the PPM image using your custom method
        Pixel[][] pixelData = ImageUtil.loadPPM(imagePath);
        if (pixelData == null) {
          displayError("Unable to load the PPM image.");
          return;
        }

        // Convert the Pixel[][] to BufferedImage
        BufferedImage img = new BufferedImage(pixelData[0].length, pixelData.length,
            BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < pixelData.length; row++) {
          for (int col = 0; col < pixelData[row].length; col++) {
            Pixel pixel = pixelData[row][col];
            int rgb = (pixel.get(0) << 16) | (pixel.get(1) << 8) | pixel.get(2);
            img.setRGB(col, row, rgb);
          }
        }

        // Display the image
        ImageIcon icon = new ImageIcon(img);
        imageLabel.setIcon(icon);
        imageLabel.revalidate();
        imageLabel.repaint();

      } else {
        // Load other image formats like JPG, PNG
        BufferedImage img = ImageIO.read(imgFile);
        if (img == null) {
          displayError("Unable to load the image. Please check the file format.");
          return;
        }

        ImageIcon icon = new ImageIcon(img);
        imageLabel.setIcon(icon);
        imageLabel.revalidate();
        imageLabel.repaint();
      }
    } catch (Exception e) {
      e.printStackTrace();
      displayError("Error loading the image.");
    }
  }


  /**
   * Displays a histogram in the histogram panel.
   *
   * @param histogramImage the histogram image to display.
   */
  public void displayHistogram(BufferedImage histogramImage) {
    histogramPanel.removeAll();
    ImageIcon histogramIcon = new ImageIcon(histogramImage);
    JLabel histogramLabel = new JLabel(histogramIcon);
    histogramPanel.add(histogramLabel);
    histogramPanel.revalidate();
    histogramPanel.repaint();
  }

  /**
   * Sets the controller that the view will use.
   *
   * @param guiController the ImageController to be used for this view
   */
  public void setController(ImageProcessingGUIController guiController) {
    this.guiController = guiController;
  }

  /**
   * Prompts the user to enter a split percentage for the image effect.
   *
   * @return the percentage as a string.
   */
  public String getSplitPercentageFromUser() {
    return JOptionPane.showInputDialog(this.frame, "Enter split percentage (0-100):");
  }


}
