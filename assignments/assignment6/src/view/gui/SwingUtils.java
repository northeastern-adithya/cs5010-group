package view.gui;

import javax.swing.*;

/**
 * A utility class to help with Swing components.
 */
public class SwingUtils {

  /**
   * Private constructor to prevent instantiation.
   */
  private SwingUtils() {
    // Prevent instantiation
  }

  /**
   * Creates a slider with a value label.
   *
   * @param valueLabel the label to display the value of the slider
   * @return the slider
   */
  public static JSlider createSlider(JLabel valueLabel) {
    JSlider slider = new JSlider(0, 100, 100);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.addChangeListener(e -> valueLabel.setText("Value: " + slider.getValue()));
    return slider;
  }
}
