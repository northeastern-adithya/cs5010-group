// GUIOutput.java
package view.output;

import controller.Features;
import model.visual.Image;
import view.components.CommandComponent;
import javax.swing.*;
import java.util.List;
import java.util.Objects;
import model.enumeration.UserCommand;
import utility.IOUtils;

public class GUIOutput extends JFrame implements UserOutput {
  private final JPanel mainPanel;
  private final CommandComponent commandPanel;
  private final JPanel imagePanel;
  private final JPanel histogramPanel;

  public GUIOutput() {
    super();
    this.setTitle("Image Processing Application");
    this.setSize(600, 900);

    // Initialize panels
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

    commandPanel = new CommandComponent();
    imagePanel = new JPanel();
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
    imagePanel.add(new JLabel("Image: "));

    histogramPanel = new JPanel();
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.Y_AXIS));
    histogramPanel.add(new JLabel("Histogram: "));

    // Add scroll panes
    mainPanel.add(new JScrollPane(commandPanel));
    mainPanel.add(new JScrollPane(imagePanel));
    mainPanel.add(new JScrollPane(histogramPanel));

    this.add(mainPanel);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void displayCommands(List<UserCommand> commands) {
    commandPanel.removeAll();
    for (UserCommand command : commands) {
      commandPanel.addCommandButton(command.getCommand());
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

  private void displayImageToPanel(JPanel panel, Image image) {
    panel.removeAll();
    if (Objects.nonNull(image)) {
      JLabel imageLabel = new JLabel(new ImageIcon(IOUtils.toBufferedImage(image)));
      panel.add(imageLabel);
    }
    panel.revalidate();
    panel.repaint();
  }
}