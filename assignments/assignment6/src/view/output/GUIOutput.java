package view.output;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import exception.ImageProcessingRunTimeException;
import model.enumeration.UserCommand;
import model.visual.Image;
import utility.IOUtils;

public class GUIOutput extends JFrame implements UserOutput {

  private JPanel mainPanel;
  private JPanel commandPanel;

  private JPanel imagePanel;
  private JPanel histogramPanel;

  public GUIOutput() {
    super();
    this.setTitle("Image Processing Application");
    this.setSize(600, 900);
    buildMainPanel();
    buildCommandPanel();
    buildImagePanel();
    buildHistogramPanel();
    this.add(mainPanel);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  private void buildMainPanel() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
  }

  private void buildCommandPanel() {
    commandPanel = new JPanel();
    commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
    commandPanel.add(new JLabel("Commands: "));
    JScrollPane scrollPane = new JScrollPane(commandPanel);
    mainPanel.add(scrollPane);
  }

  private void buildImagePanel() {
    imagePanel = new JPanel();
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
    imagePanel.add(new JLabel("Image: "));
    JScrollPane scrollPane = new JScrollPane(imagePanel);
    mainPanel.add(scrollPane);
  }

  private void buildHistogramPanel() {
    histogramPanel = new JPanel();
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.Y_AXIS));
    histogramPanel.add(new JLabel("Histogram: "));
    JScrollPane scrollPane = new JScrollPane(histogramPanel);
    mainPanel.add(scrollPane);
  }

  @Override
  public void displayMessage(String message) throws ImageProcessingRunTimeException.DisplayException {
    JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void displayCommands(List<UserCommand> commands) throws ImageProcessingRunTimeException.DisplayException {
    commandPanel.removeAll();
    for (UserCommand command : commands) {
      JButton button = new JButton(command.getCommand());
      button.setActionCommand(command.getCommand());
      commandPanel.add(button);
    }
    commandPanel.revalidate();
    commandPanel.repaint();
  }

  @Override
  public void addActionListener(ActionListener listener) {
    for (Component component : commandPanel.getComponents()) {
      if (component instanceof JButton button) {
        button.addActionListener(listener);
      }
    }
  }

  @Override
  public void displayImage(Image image, Image histogram) throws ImageProcessingRunTimeException.DisplayException {
    displayImageToPanel(imagePanel, image);
    displayImageToPanel(histogramPanel, histogram);
  }

  private void displayImageToPanel(JPanel panel, Image image) {
    if (Objects.isNull(image)) {
      panel.removeAll();
      panel.revalidate();
      panel.repaint();
      return;
    }
    panel.removeAll();
    JLabel imageLabel =
            new JLabel(new ImageIcon(IOUtils.toBufferedImage(image)));
    panel.add(imageLabel);
    panel.revalidate();
    panel.repaint();
  }
}
