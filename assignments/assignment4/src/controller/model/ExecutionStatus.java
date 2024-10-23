package controller.model;

import java.util.Objects;

public class ExecutionStatus {

  private final boolean success;
  private final String message;

  public ExecutionStatus(boolean success, String message) {
    Objects.requireNonNull(message, "Message cannot be null");
    this.success = success;
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }
}
