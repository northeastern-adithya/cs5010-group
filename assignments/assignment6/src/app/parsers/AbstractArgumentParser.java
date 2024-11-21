package app.parsers;

import controller.services.ImageProcessingService;
import factories.Factory;
import model.memory.HashMapMemory;

/**
 * AbstractArgumentParser class that implements the ArgumentParser interface.
 * Implements common methods required by all ArgumentParsers.
 */
public abstract class AbstractArgumentParser implements ArgumentParser {

  /**
   * Constructs an AbstractArgumentParser.
   */
  protected AbstractArgumentParser() {
    // No arguments to be passed.
  }

  /**
   * Creates am image processing service.
   *
   * @return The service that was created.
   */
  protected ImageProcessingService createService() {
    return Factory.createImageProcessor(new HashMapMemory());
  }
}
