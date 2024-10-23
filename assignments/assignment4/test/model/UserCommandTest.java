package model;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserCommandTest {
  @Test
  public void testGetCommand() {
    assertEquals(Optional.of(UserCommand.LOAD), UserCommand.getCommand("load"));
    assertEquals(Optional.of(UserCommand.SAVE), UserCommand.getCommand("save"));
    assertEquals(Optional.of(UserCommand.RED_COMPONENT), UserCommand.getCommand("red-component"));
    assertEquals(Optional.of(UserCommand.GREEN_COMPONENT), UserCommand.getCommand("green-component"));
    assertEquals(Optional.of(UserCommand.BLUE_COMPONENT), UserCommand.getCommand("blue-component"));
    assertEquals(Optional.of(UserCommand.VALUE_COMPONENT), UserCommand.getCommand("value-component"));
    assertEquals(Optional.of(UserCommand.LUMA_COMPONENT), UserCommand.getCommand("luma-component"));
    assertEquals(Optional.of(UserCommand.INTENSITY_COMPONENT), UserCommand.getCommand("intensity-component"));
    assertEquals(Optional.of(UserCommand.HORIZONTAL_FLIP), UserCommand.getCommand("horizontal-flip"));
    assertEquals(Optional.of(UserCommand.VERTICAL_FLIP), UserCommand.getCommand("vertical-flip"));
    assertEquals(Optional.of(UserCommand.BRIGHTEN), UserCommand.getCommand("brighten"));
    assertEquals(Optional.of(UserCommand.RGB_SPLIT), UserCommand.getCommand("rgb-split"));
    assertEquals(Optional.of(UserCommand.RGB_COMBINE), UserCommand.getCommand("rgb-combine"));
    assertEquals(Optional.of(UserCommand.BLUR), UserCommand.getCommand("blur"));
    assertEquals(Optional.of(UserCommand.SHARPEN), UserCommand.getCommand("sharpen"));
    assertEquals(Optional.of(UserCommand.SEPIA), UserCommand.getCommand("sepia"));
    assertEquals(Optional.of(UserCommand.RUN), UserCommand.getCommand("run"));
    assertEquals(Optional.of(UserCommand.QUIT), UserCommand.getCommand("quit"));
    assertEquals(Optional.of(UserCommand.HELP), UserCommand.getCommand("help"));
    assertEquals(Optional.empty(), UserCommand.getCommand("non-existent-command"));
  }

  @Test
  public void testGetUserCommands() {
    String expectedCommands = "load image-path image-name: Load an image from the specified path and refer it to henceforth in the program by the given image name.\n" +
            "save image-path image-name: Save the image with the given name to the specified path which should include the name of the file.\n" +
            "red-component image-name dest-image-name: Create an image with the red-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
            "green-component image-name dest-image-name: Create an image with the green-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
            "blue-component image-name dest-image-name: Create an image with the blue-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
            "value-component image-name dest-image-name: Create an image with the value-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
            "luma-component image-name dest-image-name: Create an image with the luma-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
            "intensity-component image-name dest-image-name: Create an image with the intensity-component of the image with the given name, and refer to it henceforth in the program by the given destination name.\n" +
            "horizontal-flip image-name dest-image-name: Flip an image horizontally to create a new image, referred to henceforth by the given destination name.\n" +
            "vertical-flip image-name dest-image-name: Flip an image vertically to create a new image, referred to henceforth by the given destination name.\n" +
            "brighten increment image-name dest-image-name: brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).\n" +
            "rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue: split the given image into three images containing its red, green and blue components respectively. These would be the same images that would be individually produced with the red-component, green-component and blue-component commands.\n" +
            "rgb-combine image-name red-image green-image blue-image: Combine the three images that are individually red, green and blue into a single image that gets its red, green and blue components from the three images respectively.\n" +
            "blur image-name dest-image-name: blur the given image and store the result in another image with the given name.\n" +
            "sharpen image-name dest-image-name: sharpen the given image and store the result in another image with the given name.\n" +
            "sepia image-name dest-image-name: produce a sepia-toned version of the given image and store the result in another image with the given name.\n" +
            "run script-file: Load and run the script commands in the specified file.\n" +
            "quit: Quit the program.\n" +
            "help: Print this help message.\n";

    assertEquals(expectedCommands, UserCommand.getUserCommands());
  }

}