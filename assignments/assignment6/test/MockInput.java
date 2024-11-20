import java.util.Objects;
import java.util.Optional;
import java.util.function.IntConsumer;

import exception.ImageProcessorException;
import model.request.ImageProcessingRequest;
import view.input.UserInput;

/**
 * A mock input class that provides input to the program.
 */
public class MockInput implements UserInput {

  private final Readable readable;
  private final boolean confirmSplitView;
  private final Integer sliderInput;
  private final String interactiveImageLoadPathInput;
  private final String interactiveImageSavePathInput;
  private final ImageProcessingRequest.Levels interactiveThreeLevelInput;

  /**
   * Constructor for the MockInput class.
   *
   * @param readable the readable input
   * @param confirmSplitView the confirm split view input
   * @param sliderInput the slider input
   * @param interactiveImageLoadPathInput the interactive image load path input
   * @param interactiveImageSavePathInput the interactive image save path input
   * @param interactiveThreeLevelInput the interactive three level input
   */
  public MockInput(Readable readable, boolean confirmSplitView,
                   Integer sliderInput,
                   String interactiveImageLoadPathInput,
                   String interactiveImageSavePathInput,
                   ImageProcessingRequest.Levels interactiveThreeLevelInput) {
    this.readable = readable;
    this.confirmSplitView = confirmSplitView;
    this.sliderInput = sliderInput;
    this.interactiveImageLoadPathInput = interactiveImageLoadPathInput;
    this.interactiveImageSavePathInput = interactiveImageSavePathInput;
    this.interactiveThreeLevelInput = interactiveThreeLevelInput;
  }


  @Override
  public Readable getUserInput() throws
          ImageProcessorException {
    return this.readable;
  }

  @Override
  public boolean confirmSplitView(IntConsumer updateImageCallback) throws
          ImageProcessorException {
    if(getSliderInput().isPresent() && Objects.nonNull(updateImageCallback)){
      updateImageCallback.accept(getSliderInput().get());
    }
    return this.confirmSplitView;
  }

  @Override
  public Optional<Integer> getSliderInput() {
    return Optional.ofNullable(this.sliderInput);
  }

  @Override
  public String interactiveImageLoadPathInput() throws
          ImageProcessorException {
    return this.interactiveImageLoadPathInput;
  }

  @Override
  public String interactiveImageSavePathInput() throws
          ImageProcessorException {
    return this.interactiveImageSavePathInput;
  }

  @Override
  public ImageProcessingRequest.Levels interactiveThreeLevelInput() throws
          ImageProcessorException {
    return this.interactiveThreeLevelInput;
  }
}
