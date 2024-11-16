package view.input;

import java.io.File;
import java.util.Optional;
import java.util.function.IntConsumer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import exception.ImageProcessorException;
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
  public String interactiveImageLoadPathInput() {
    JFileChooser fileChooser = createFileChooseWithFilter();
    int returnState = fileChooser.showOpenDialog(null);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else {
      throw new IllegalArgumentException("Invalid file path");
    }
  }

  @Override
  public String interactiveImageSavePathInput() {
    JFileChooser fileChooser = createFileChooseWithFilter();
    int returnState = fileChooser.showSaveDialog(null);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else {
      throw new IllegalArgumentException("Invalid file path");
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
}
