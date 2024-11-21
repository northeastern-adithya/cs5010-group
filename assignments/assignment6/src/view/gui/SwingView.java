package view.gui;

import controller.Features;
import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import model.request.ImageProcessingRequest;
import model.visual.Image;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

import model.enumeration.UserCommand;
import utility.IOUtils;
import view.DisplayMessageType;
import view.components.SwingFeatureComponent;

/**
 * Represents the gui view which is implemented using the swing library.
 */
public class SwingView extends JFrame implements GUIView {
  private final JPanel mainPanel;
  private final SwingFeatureComponent featurePanel;
  private final JPanel imagePanel;
  private final JPanel histogramPanel;

  /**
   * Constructs a SwingView object.
   * This will initialise the required panels and build the layout of the GUI.
   */
  public SwingView() {
    super();
    this.setTitle("Image Processing Application");
    this.setSize(600, 900);
    // Initialising panels
    mainPanel = new JPanel();
    featurePanel = new SwingFeatureComponent();
    imagePanel = new JPanel();
    histogramPanel = new JPanel();

    // Building the layout
    buildMainPanel();
    buildImagePanel();
    buildCommandPanel();
    buildHistogramPanel();
    addScrollPanes();

    this.add(mainPanel);
    addWindowListener(
            new WindowAdapter() {
              @Override
              public void windowClosing(WindowEvent e) {
                featurePanel.closeWindow();
              }
            }
    );
    this.setVisible(true);
  }

  /**
   * Builds the main panel of the GUI.
   */
  private void buildMainPanel() {
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
  }


  /**
   * Builds the image panel of the GUI.
   */
  private void buildImagePanel() {
    buildPanel(imagePanel, "Image", 400, 600);
  }

  /**
   * Builds the command panel of the GUI.
   */
  private void buildCommandPanel() {
    featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));
    featurePanel.setBorder(new TitledBorder("Commands"));
  }

  /**
   * Builds the histogram panel of the GUI.
   */
  private void buildHistogramPanel() {
    buildPanel(histogramPanel, "Histogram", 200, 600);
  }

  /**
   * Builds a panel with the specified title, width and height.
   *
   * @param panel  the panel to be built
   * @param title  the title of the panel
   * @param width  the width of the panel
   * @param height the height of the panel
   */
  private void buildPanel(JPanel panel, String title, int width, int height) {
    panel.setLayout(new BorderLayout());
    panel.setPreferredSize(new Dimension(width, height));
    panel.add(new JLabel(), BorderLayout.NORTH);
    panel.setBorder(new TitledBorder(title));
  }

  /**
   * Adds scroll panes to the main panel.
   */
  private void addScrollPanes() {
    mainPanel.add(new JScrollPane(featurePanel));
    mainPanel.add(new JScrollPane(imagePanel));
    mainPanel.add(new JScrollPane(histogramPanel));
  }


  @Override
  public void displayMessage(String message, DisplayMessageType messageType)
          throws
          ImageProcessingRunTimeException.DisplayException {
    if (Objects.requireNonNull(messageType) == DisplayMessageType.INFO) {
      JOptionPane.showMessageDialog(this, message, "Info",
              JOptionPane.INFORMATION_MESSAGE);
    } else if (messageType == DisplayMessageType.ERROR) {
      JOptionPane.showMessageDialog(this, message, "Error",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void displayCommands(List<UserCommand> commands) {
    featurePanel.removeAll();
    for (UserCommand command : commands) {
      featurePanel.addCommandButton(command);
    }
    featurePanel.revalidate();
    featurePanel.repaint();
  }

  @Override
  public void addFeatures(Features features) {
    featurePanel.addFeatures(features);
  }

  @Override
  public void displayImage(Image image, Image histogram) {
    displayImageToPanel(imagePanel, image);
    displayImageToPanel(histogramPanel, histogram);
  }

  @Override
  public void clearImage() throws
          ImageProcessingRunTimeException.DisplayException {
    clearPanel(imagePanel);
    clearPanel(histogramPanel);
  }

  @Override
  public void closeWindow() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void doNotCloseWindow() {
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  }

  /**
   * Displays a dialog with a slider to configure split view settings and
   * returns whether
   * the user confirmed the operation.
   *
   * @param updateImageCallback A callback function that accepts an integer
   *                            value representing
   *                            the split position when the slider value changes
   * @return true if the user clicks OK, false if they click Cancel
   * @throws ImageProcessorException If there's an error displaying the dialog
   */
  @Override
  public boolean confirmSplitView(IntConsumer updateImageCallback) throws
          ImageProcessorException {
    JLabel value = new JLabel("Value: 100");
    JSlider slider = SwingUtils.createSlider(value);
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

  @Override
  public ImageProcessingRequest.ScalingFactors interactiveScalingFactorsInput() throws
          ImageProcessorException {
    JLabel widthValue = new JLabel("Width: 100%");
    JLabel heightValue = new JLabel("Height: 100%");

    JSlider widthSlider = createPercentageSlider(widthValue, "Width: ");
    JSlider heightSlider = createPercentageSlider(heightValue, "Height: ");

    JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));

    // Add components to panel
    panel.add(widthValue);
    panel.add(widthSlider);
    panel.add(heightValue);
    panel.add(heightSlider);

    // Show dialog
    int result = JOptionPane.showConfirmDialog(null, panel,
            "Downscale Image", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
      return new ImageProcessingRequest.ScalingFactors(
              widthSlider.getValue(),
              heightSlider.getValue());
    } else {
      throw new ImageProcessorException("Downscale cancelled");
    }
  }

  private JSlider createPercentageSlider(JLabel valueLabel,
                                         String labelPrefix) {
    JSlider slider = new JSlider(0, 100, 100);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(5);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.addChangeListener(e -> {
      valueLabel.setText(labelPrefix + slider.getValue() + "%");
    });
    return slider;
  }

  /**
   * Clears the panel.
   *
   * @param panel the panel to be cleared
   */
  private void clearPanel(JPanel panel) {
    panel.removeAll();
    panel.revalidate();
    panel.repaint();
  }

  /**
   * Displays the image to the panel.
   * If the image is null, the panel will be stay the same.
   *
   * @param panel the panel to which the image is to be displayed
   * @param image the image to be displayed
   */
  private void displayImageToPanel(JPanel panel, Image image) {
    if (Objects.nonNull(image)) {
      panel.removeAll();
      JLabel imageLabel =
              new JLabel(new ImageIcon(IOUtils.toBufferedImage(image)));
      imageLabel.setHorizontalAlignment(JLabel.CENTER);
      panel.add(imageLabel);
      panel.revalidate();
      panel.repaint();
    }
  }
}