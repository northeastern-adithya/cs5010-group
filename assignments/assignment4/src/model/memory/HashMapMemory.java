package model.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import exception.NotFoundException;
import model.visual.Image;

public class HashMapMemory implements ImageMemory {
  private final Map<String, Image> memory;

  public HashMapMemory() {
    this.memory = new HashMap<>();
  }

  @Override
  public void addImage(String imageName, Image image) {
    memory.put(imageName, image);
  }

  @Override
  public Image getImage(String imageName) throws NotFoundException {
    return Optional.ofNullable(memory.get(imageName)).orElseThrow(
            () -> new NotFoundException(String.format("Image with name %s not found in memory", imageName))
    );
  }
}
