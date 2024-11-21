package view.components;

import controller.Features;
import exception.ImageProcessorException;
import model.enumeration.ImageType;
import model.enumeration.UserCommand;
import model.request.ImageProcessingRequest;
import view.gui.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A component that maps UI button actions to Features interface methods
 * using Swing.
 */
public class SwingFeatureComponent extends JPanel {
  private final List<Features> featureListeners = new ArrayList<>();

  /**
   * Constructs a CommandComponent object.
   * This object is used to map UI button actions to Features interface methods.
   */
  public SwingFeatureComponent() {
    super();
  }

  /**
   * Adds a feature to the list of feature listeners.
   */
  public void addFeatures(Features features) {
    this.featureListeners.add(features);
  }

  /**
   * Adds a command button with the specified action command.
   */
  public void addCommandButton(UserCommand command) {
    JButton button = new JButton(command.getCommand());
    button.setActionCommand(command.getCommand());
    button.addActionListener(this::handleAction);
    this.add(button);
  }


  /**
   * Calls the features to act on closing the window.
   */
  public void closeWindow() {
    for (Features feature : featureListeners) {
      feature.closeWindow();
    }
  }

  /**
   * Handles the event when a button is clicked.
   *
   * @param event the event that occurred
   */
  private void handleAction(ActionEvent event) {
    Optional<UserCommand> userCommand =
            UserCommand.getCommand(event.getActionCommand());
    userCommand.ifPresent(this::handleUserCommand);
  }

  /**
   * Handles the user command by calling the appropriate method in the
   * Features interface.
   *
   * @param command the user command to be handled
   */
  private void handleUserCommand(UserCommand command) {
    for (Features feature : featureListeners) {
      switch (command) {
        case LOAD:
          handleLoadCommand(feature);
          break;
        case SAVE:
          handleSaveCommand(feature);
          break;
        case SEPIA:
          feature.applySepia();
          break;
        case RESET:
          feature.reset();
          break;
        case VERTICAL_FLIP:
          feature.verticalFlip();
          break;
        case HORIZONTAL_FLIP:
          feature.horizontalFlip();
          break;
        case LUMA_COMPONENT:
          feature.getLuma();
          break;
        case COLOR_CORRECT:
          feature.colorCorrect();
          break;
        case RED_COMPONENT:
          feature.redComponent();
          break;
        case GREEN_COMPONENT:
          feature.greenComponent();
          break;
        case BLUE_COMPONENT:
          feature.blueComponent();
          break;
        case BLUR:
          feature.blurImage();
          break;
        case SHARPEN:
          feature.sharpenImage();
          break;
        case COMPRESS:
          handleCompression(feature);
          break;
        case LEVELS_ADJUST:
          handleLevelAdjust(feature);
          break;
        case DOWNSCALE:
          feature.downscaleImage();
          break;
        default:
          // Invalid Command - Do nothing
          break;
      }
    }
  }

  /**
   * Handles the level adjust command by calling the appropriate method in the
   * Features interface.
   * Gets the black, mid and white points from the user and calls the
   * appropriate method.
   *
   * @param features the feature to be handled
   */
  private void handleLevelAdjust(Features features) {
    JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

    JSpinner blackSpinner = buildSpinner(0);
    JSpinner midSpinner = buildSpinner(128);
    JSpinner whiteSpinner = buildSpinner(255);

    configureLevelSpinnerListeners(blackSpinner, midSpinner, whiteSpinner);

    disableSpinnerTextEditing(blackSpinner, midSpinner, whiteSpinner);

    // Add components to panel
    panel.add(new JLabel("Black Point:"));
    panel.add(blackSpinner);
    panel.add(new JLabel("Mid Point:"));
    panel.add(midSpinner);
    panel.add(new JLabel("White Point:"));
    panel.add(whiteSpinner);

    // Show dialog
    int result = JOptionPane.showConfirmDialog(null, panel,
            "Level Adjustment", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
      features.levelsAdjust(
              new ImageProcessingRequest.Levels(
                      (Integer) blackSpinner.getValue(),
                      (Integer) midSpinner.getValue(),
                      (Integer) whiteSpinner.getValue())
      );
    }
  }

  /**
   * Disables text editing for multiple spinners.
   *
   * @param blackSpinner The spinner for black point
   * @param midSpinner   The spinner for mid point
   * @param whiteSpinner The spinner for white point
   */
  private void disableSpinnerTextEditing(JSpinner blackSpinner,
                                         JSpinner midSpinner,
                                         JSpinner whiteSpinner) {
    disableTextEditing(blackSpinner);
    disableTextEditing(midSpinner);
    disableTextEditing(whiteSpinner);
  }


  /**
   * Disables text editing for a single spinner component.
   *
   * @param spinner The JSpinner component to disable text editing for
   */
  private void disableTextEditing(JSpinner spinner) {
    ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
  }

  /**
   * Configures change listeners for a series of spinners to maintain proper
   * ordering.
   * Ensures that each spinner's value is greater than the previous spinner
   * and less
   * than the next spinner.
   *
   * @param spinners Variable number of JSpinner components to be configured
   */
  private void configureLevelSpinnerListeners(JSpinner... spinners) {
    // Verify we have at least two spinners to compare
    if (spinners.length < 2) {
      return;
    }

    // Add change listeners to each spinner
    for (int i = 0; i < spinners.length; i++) {
      final int currentIndex = i;
      spinners[i].addChangeListener(e -> {
        int currentValue = (Integer) spinners[currentIndex].getValue();

        // Check against previous spinner (ensure current value is greater)
        if (currentIndex > 0) {
          int previousValue = (Integer) spinners[currentIndex - 1].getValue();
          if (currentValue <= previousValue) {
            spinners[currentIndex].setValue(previousValue + 1);
            return; // Return to avoid potential circular updates
          }
        }

        // Check against next spinner (ensure current value is lesser)
        if (currentIndex < spinners.length - 1) {
          int nextValue = (Integer) spinners[currentIndex + 1].getValue();
          if (currentValue >= nextValue) {
            spinners[currentIndex].setValue(nextValue - 1);
          }
        }
      });
    }
  }

  /**
   * Creates a number spinner with specified default value and range 0-255.
   *
   * @param defaultValue The initial value for the spinner
   * @return A configured JSpinner with appropriate model and range
   */
  private JSpinner buildSpinner(int defaultValue) {
    SpinnerNumberModel model = new SpinnerNumberModel(defaultValue, 0, 255, 1);
    return new JSpinner(model);
  }

  /**
   * Handles the compression command by calling the appropriate method in the
   * Features interface.
   *
   * @param feature the feature to be handled
   */
  private void handleCompression(Features feature) {
    JLabel value = new JLabel("Value: 100");
    JSlider slider = SwingUtils.createSlider(value);
    JPanel panel = new JPanel();
    panel.add(new JLabel("Enter the value"));
    panel.add(slider);
    panel.add(value);
    int result = JOptionPane.showConfirmDialog(null, panel, "Slider",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      feature.compressImage(slider.getValue());
    }
  }


  /**
   * Handles the load command by calling the appropriate method in the
   * Features interface.
   * Opens up the dialog to select the file to load.
   *
   * @param feature the feature to be handled
   */
  private void handleLoadCommand(Features feature) {
    JFileChooser fileChooser = createFileChooseWithFilter();
    int returnState = fileChooser.showOpenDialog(null);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      feature.loadImage(f.getAbsolutePath());
    }
  }

  /**
   * Handles the save command by calling the appropriate method in the
   * Features interface.
   * Opens up the dialog to select the file to save.
   *
   * @param feature the feature to be handled
   */
  private void handleSaveCommand(Features feature) {
    JFileChooser fileChooser = createFileChooseWithFilter();
    int returnState = fileChooser.showSaveDialog(null);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      feature.saveImage(f.getAbsolutePath());
    }
  }

  /**
   * Creates a file chooser with appropriate image file filters.
   *
   * @return A configured JFileChooser that only shows supported image file
   * types
   */
  private JFileChooser createFileChooseWithFilter() {
    JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images",
            ImageType.JPEG.getExtension(),
            ImageType.PNG.getExtension(),
            ImageType.PPM.getExtension(),
            ImageType.JPG.getExtension());
    fileChooser.setFileFilter(filter);
    return fileChooser;
  }


}