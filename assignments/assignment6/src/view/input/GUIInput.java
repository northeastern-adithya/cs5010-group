package view.input;

import java.util.function.IntConsumer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import exception.ImageProcessorException;

public class GUIInput implements UserInput {
  @Override
  public Readable getUserInput() throws
          ImageProcessorException {
    throw new ImageProcessorException("No input steam in GUI");
  }

  @Override
  public boolean confirmSplitView(IntConsumer updateImageCallback) throws
          ImageProcessorException {
    JSlider sepiaSlider = new JSlider(0, 100, 100);
    sepiaSlider.setMajorTickSpacing(10);
    sepiaSlider.setMinorTickSpacing(1);
    sepiaSlider.setPaintTicks(true);
    sepiaSlider.setPaintLabels(true);
    sepiaSlider.addChangeListener(e -> {
      if (!sepiaSlider.getValueIsAdjusting()) {
        updateImageCallback.accept(sepiaSlider.getValue());
      }
    });
    JPanel panel = new JPanel();
    panel.add(new JLabel("Split View Slider"));
    panel.add(sepiaSlider);
    int result = JOptionPane.showConfirmDialog(null, panel, "Sepia Slider",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    return result == JOptionPane.OK_OPTION;
  }
}
