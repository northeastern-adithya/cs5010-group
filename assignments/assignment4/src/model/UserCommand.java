package model;

import java.util.Arrays;

public enum UserCommand {

  LOAD("load"),
  SAVE("save"),
  RED_COMPONENT("red-component"),
  GREEN_COMPONENT("green-component"),
  BLUE_COMPONENT("blue-component"),
  VALUE_COMPONENT("value-component"),
  LUMA_COMPONENT("luma-component"),
  INTENSITY_COMPONENT("intensity-component"),
  HORIZONTAL_FLIP("horizontal-flip"),
  VERTICAL_FLIP("vertical-flip"),
  BRIGHTEN("brighten"),
  RGB_SPLIT("rgb-split"),
  RGB_COMBINE("rgb-combine"),
  BLUR("blur"),
  SHARPEN("sharpen"),
  SEPIA("sepia"),
  RUN("run"),
  INVALID("invalid");

  private final String command;

  UserCommand(String command) {
    this.command = command;
  }

  public static UserCommand getCommand(String command) {
   return Arrays.stream(UserCommand.values()).filter(
        userCommand -> userCommand.command.equals(command)).findFirst().orElse(INVALID);
  }
}
