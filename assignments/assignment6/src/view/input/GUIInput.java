package view.input;

import java.awt.*;
import java.io.File;
import java.util.Optional;
import java.util.function.IntConsumer;

import exception.ImageProcessorException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.enumeration.ImageType;

/**
 * A graphical user interface implementation of the UserInput interface that provides
 * various GUI components for user interaction in an image processing application.
 * This class manages dialog boxes, file choosers, sliders, and spinners for collecting
 * user input in a visual manner.
 */
public class GUIInput implements UserInput {

  /**
   * This method is not supported in the GUI implementation.
   *
   * @return Never returns as this method always throws an exception
   * @throws ImageProcessorException Always throws this exception as GUI doesn't use input streams
   */
  @Override
  public Readable getUserInput() throws
          ImageProcessorException {
    throw new ImageProcessorException("No input steam in GUI");
  }

  /**
   * Displays a dialog with a slider to configure split view settings and returns whether
   * the user confirmed the operation.
   *
   * @param updateImageCallback A callback function that accepts an integer value representing
   *                           the split position when the slider value changes
   * @return true if the user clicks OK, false if they click Cancel
   * @throws ImageProcessorException If there's an error displaying the dialog
   */
  @Override
  public boolean confirmSplitView(IntConsumer updateImageCallback) throws
          ImageProcessorException {
    JLabel value = new JLabel("Value: 100");
    JSlider slider = createSlider(value);
    slider.addChangeListener(e -> {
      if (!slider.getValueIsAdjusting()) {
        updateImageCallback.accept(slider.getValue());
      }
    });
    JPanel panel = new JPanel();
    panel.add(new JLabel("Split View"));
    panel.add(slider);
    panel.add(value);
    int result = JOptionPane.showConfirmDialog(null, panel, "Split View",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    return result == JOptionPane.OK_OPTION;
  }

  /**
   * Creates a slider with a value label.
   *
   * @param valueLabel the label to display the value of the slider
   * @return the slider
   */
  private JSlider createSlider(JLabel valueLabel) {
    JSlider slider = new JSlider(0, 100, 100);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.addChangeListener(e -> valueLabel.setText("Value: " + slider.getValue()));
    return slider;
  }

  /**
   * Displays a dialog with a slider and returns the selected value if confirmed.
   *
   * @return An Optional containing the selected slider value if OK is clicked,
   *         or an empty Optional if cancelled
   */
  @Override
  public Optional<Integer> getSliderInput() {
    JLabel value = new JLabel("Value: 100");
    JSlider slider = createSlider(value);
    JPanel panel = new JPanel();
    panel.add(new JLabel("Enter the value"));
    panel.add(slider);
    panel.add(value);
    int result = JOptionPane.showConfirmDialog(null, panel, "Slider",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      return Optional.of(slider.getValue());
    } else {
      return Optional.empty();
    }
  }

  /**
   * Displays a file chooser dialog for loading an image file.
   *
   * @return The absolute path of the selected file
   * @throws ImageProcessorException If no file is selected or the operation is cancelled
   */
  @Override
  public String interactiveImageLoadPathInput() throws ImageProcessorException {
    JFileChooser fileChooser = createFileChooseWithFilter();
    int returnState = fileChooser.showOpenDialog(null);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else {
      throw new ImageProcessorException("Invalid file path");
    }
  }

  /**
   * Displays a file chooser dialog for saving an image file.
   *
   * @return The absolute path where the file should be saved
   * @throws ImageProcessorException If no file location is selected or the operation is cancelled
   */
  @Override
  public String interactiveImageSavePathInput() throws ImageProcessorException {
    JFileChooser fileChooser = createFileChooseWithFilter();
    int returnState = fileChooser.showSaveDialog(null);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else {
      throw new ImageProcessorException("Invalid file path");
    }
  }

  /**
   * Displays a dialog for adjusting three-level image settings (black point, mid point, and white point).
   * The dialog ensures that the values maintain proper ordering (black < mid < white).
   *
   * @return An array of three integers representing the black, mid, and white points respectively
   * @throws ImageProcessorException If the operation is cancelled
   */
  @Override
  public int[] interactiveThreeLevelInput() throws ImageProcessorException {
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
      return new int[]{
              (Integer) blackSpinner.getValue(),
              (Integer) midSpinner.getValue(),
              (Integer) whiteSpinner.getValue()
      };
    } else {
      throw new ImageProcessorException("Level adjustment cancelled");
    }
  }

  /**
   * Creates a file chooser with appropriate image file filters.
   *
   * @return A configured JFileChooser that only shows supported image file types
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

  /**
   * Configures change listeners for a series of spinners to maintain proper ordering.
   * Ensures that each spinner's value is greater than the previous spinner and less
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
   * Disables text editing for multiple spinners.
   *
   * @param blackSpinner The spinner for black point
   * @param midSpinner The spinner for mid point
   * @param whiteSpinner The spinner for white point
   */
  private void disableSpinnerTextEditing(JSpinner blackSpinner, JSpinner midSpinner, JSpinner whiteSpinner) {
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

}
