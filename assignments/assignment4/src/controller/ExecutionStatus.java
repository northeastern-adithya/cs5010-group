package controller;

import java.util.Objects;

/**
 * ExecutionStatus class that represents the status of the execution of a command.
 * It contains a boolean value to indicate the success of the execution and a message.
 */
public class ExecutionStatus {

  /**
   * Boolean value to indicate the success of the execution.
   */
  private final boolean success;
  /**
   * Message to indicate the status of the execution.
   */
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ExecutionStatus)) {
      return false;
    }
    ExecutionStatus other = (ExecutionStatus) obj;
    return success == other.success && message.equals(other.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, message);
  }
}
