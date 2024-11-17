package view.output;

import controller.Features;
import exception.ImageProcessingRunTimeException;
import model.visual.Image;
import view.components.CommandComponent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Objects;

import model.enumeration.UserCommand;
import utility.IOUtils;

/**
 * Represents the output to the user.
 * This output is displayed through the GUI.
 */
public class GUIOutput extends JFrame implements UserOutput {
  private final JPanel mainPanel;
  private final CommandComponent commandPanel;
  private final JPanel imagePanel;
  private final JPanel histogramPanel;

  /**
   * Constructs a GUIOutput object.
   * This will initialise the required panels and build the layout of the GUI.
   */
  public GUIOutput() {
    super();
    this.setTitle("Image Processing Application");
    this.setSize(600, 900);
    // Initialising panels
    mainPanel = new JPanel();
    commandPanel = new CommandComponent();
    imagePanel = new JPanel();
    histogramPanel = new JPanel();

    // Building the layout
    buildMainPanel();
    buildImagePanel();
    buildCommandPanel();
    buildHistogramPanel();
    addScrollPanes();

    this.add(mainPanel);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
    commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
    commandPanel.setBorder(new TitledBorder("Commands"));
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
    mainPanel.add(new JScrollPane(commandPanel));
    mainPanel.add(new JScrollPane(imagePanel));
    mainPanel.add(new JScrollPane(histogramPanel));
  }


  @Override
  public void displayMessage(String message, DisplayMessageType messageType)
          throws ImageProcessingRunTimeException.DisplayException {
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
    commandPanel.removeAll();
    for (UserCommand command : commands) {
      commandPanel.addCommandButton(command);
    }
    commandPanel.revalidate();
    commandPanel.repaint();
  }

  @Override
  public void addFeatures(Features features) {
    commandPanel.addFeatures(features);
  }

  @Override
  public void displayImage(Image image, Image histogram) {
    displayImageToPanel(imagePanel, image);
    displayImageToPanel(histogramPanel, histogram);
  }

  @Override
  public void clearImage() throws ImageProcessingRunTimeException.DisplayException {
    clearPanel(imagePanel);
    clearPanel(histogramPanel);
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