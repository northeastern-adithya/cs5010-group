package view.input;

import java.awt.*;
import java.io.File;
import java.util.Optional;
import java.util.function.IntConsumer;

import exception.ImageProcessorException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.enumeration.ImageType;

public class GUIInput implements UserInput {
  @Override
  public Readable getUserInput() throws
          ImageProcessorException {
    throw new ImageProcessorException("No input steam in GUI");
  }

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

  @Override
  public int[] interactiveThreeLevelInput() throws ImageProcessorException {
    JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

    JSpinner blackSpinner = buildSpinner(0, 0, 255, 1);
    JSpinner midSpinner = buildSpinner(128, 0, 255, 1);
    JSpinner whiteSpinner = buildSpinner(255, 0, 255, 1);

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

  private void configureLevelSpinnerListeners(JSpinner blackSpinner, JSpinner midSpinner, JSpinner whiteSpinner) {
    // Add change listeners to maintain black < mid < white relationship
    blackSpinner.addChangeListener(e -> {
      int blackVal = (Integer) blackSpinner.getValue();
      int midVal = (Integer) midSpinner.getValue();
      if (blackVal >= midVal) {
        blackSpinner.setValue(midVal - 1);
      }
    });

    midSpinner.addChangeListener(e -> {
      int blackVal = (Integer) blackSpinner.getValue();
      int midVal = (Integer) midSpinner.getValue();
      int whiteVal = (Integer) whiteSpinner.getValue();

      if (midVal <= blackVal) {
        midSpinner.setValue(blackVal + 1);
      }
      if (midVal >= whiteVal) {
        midSpinner.setValue(whiteVal - 1);
      }
    });

    whiteSpinner.addChangeListener(e -> {
      int midVal = (Integer) midSpinner.getValue();
      int whiteVal = (Integer) whiteSpinner.getValue();
      if (whiteVal <= midVal) {
        whiteSpinner.setValue(midVal + 1);
      }
    });
  }

  private JSpinner buildSpinner(int value, int minimum, int maximum, int stepSize) {
    SpinnerNumberModel model = new SpinnerNumberModel(value, minimum, maximum, stepSize);
    return new JSpinner(model);
  }

  private void disableSpinnerTextEditing(JSpinner blackSpinner, JSpinner midSpinner, JSpinner whiteSpinner) {
    disableTextEditing(blackSpinner);
    disableTextEditing(midSpinner);
    disableTextEditing(whiteSpinner);
  }

  private void disableTextEditing(JSpinner spinner) {
    ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
  }

}
