import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntConsumer;

import controller.Features;
import exception.ImageProcessingRunTimeException;
import exception.ImageProcessorException;
import model.enumeration.UserCommand;
import model.request.ImageProcessingRequest;
import model.visual.Image;
import view.DisplayMessageType;
import view.gui.GUIView;

public class MockGUIView implements GUIView {
  private final Readable readable;
  private final boolean confirmSplitView;
  private final Integer sliderInput;
  private final String interactiveImageLoadPathInput;
  private final String interactiveImageSavePathInput;
  private final ImageProcessingRequest.Levels interactiveThreeLevelInput;
  private final StringBuilder log;

  /**
   * Constructor for the MockInput class.
   *
   * @param readable                      the readable input
   * @param confirmSplitView              the confirm split view input
   * @param sliderInput                   the slider input
   * @param interactiveImageLoadPathInput the interactive image load path input
   * @param interactiveImageSavePathInput the interactive image save path input
   * @param interactiveThreeLevelInput    the interactive three level input
   * @param log                           the log to log the output
   */
  public MockGUIView(Readable readable, boolean confirmSplitView,
                     Integer sliderInput,
                     String interactiveImageLoadPathInput,
                     String interactiveImageSavePathInput,
                     ImageProcessingRequest.Levels interactiveThreeLevelInput,
                     StringBuilder log) {
    this.readable = readable;
    this.confirmSplitView = confirmSplitView;
    this.sliderInput = sliderInput;
    this.interactiveImageLoadPathInput = interactiveImageLoadPathInput;
    this.interactiveImageSavePathInput = interactiveImageSavePathInput;
    this.interactiveThreeLevelInput = interactiveThreeLevelInput;
    this.log = log;
  }

  @Override
  public boolean confirmSplitView(IntConsumer updateImageCallback) throws
          ImageProcessorException {
    if (getSliderInput().isPresent() && Objects.nonNull(updateImageCallback)) {
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

  @Override
  public ImageProcessingRequest.ScalingFactors interactiveScalingFactorsInput() throws
          ImageProcessorException {
    return null;
  }


  @Override
  public void displayMessage(String message, DisplayMessageType messageType) throws
          ImageProcessingRunTimeException.DisplayException {
    log(message);
    log(messageType.toString());
  }

  @Override
  public void displayCommands(List<UserCommand> commands) throws
          ImageProcessingRunTimeException.DisplayException {
    log(commands.toString());
  }

  @Override
  public void addFeatures(Features features) {

  }

  @Override
  public void displayImage(Image image, Image histogram) throws
          ImageProcessingRunTimeException.DisplayException {
    if (Objects.nonNull(image)) {
      log(image.toString());
    }
    if (Objects.nonNull(histogram)) {
      log(histogram.toString());
    }
  }

  @Override
  public void clearImage() throws
          ImageProcessingRunTimeException.DisplayException {
    log("Image cleared");
  }

  @Override
  public void closeWindow() throws
          ImageProcessingRunTimeException.QuitException {
    log("Window closed");

  }

  @Override
  public void doNotCloseWindow() {
    log("Window not closed");
  }

  private void log(String message) {
    log.append(message).append("\n");
  }
}
