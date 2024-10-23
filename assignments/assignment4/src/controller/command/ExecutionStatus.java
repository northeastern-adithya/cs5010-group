package controller.command;

public class ExecutionStatus {

  private final boolean success;
  private final String message;

  public ExecutionStatus(boolean success, String message) {
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
