// CommandComponent.java
package view.components;

import controller.Features;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;*
import java.util.List;

/**
 * A component that maps UI button actions to Features interface methods.
 */
public class CommandComponent extends JPanel {
  private final List<Features> featureListeners = new ArrayList<>();

  public CommandComponent() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
  }

  /**
   * Adds a features listener to receive notifications of user actions
   */
  public void addFeatures(Features features) {
    this.featureListeners.add(features);
  }

  /**
   * Adds a command button with the specified action command
   */
  public void addCommandButton(String commandName) {
    JButton button = new JButton(commandName);
    button.setActionCommand(commandName.toLowerCase());
    button.addActionListener(this::handleAction);
    this.add(button);
  }

  private void handleAction(ActionEvent e) {
    String command = e.getActionCommand();
    for (Features f : featureListeners) {
      try {
        switch (command) {
          case "load":
            f.loadImage();
            break;
          case "save":
            f.saveImage();
            break;
          case "sepia":
            f.applySepia();
            break;
          case "clear":
            f.clearMemory();
            break;
        }
      } catch (Exception ex) {
        // Exceptions are handled by the Features implementation
      }
    }
  }
}