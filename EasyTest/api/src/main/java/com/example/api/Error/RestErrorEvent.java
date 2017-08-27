package com.example.api.Error;

public class RestErrorEvent {

  private ErrorCode errorCode;
  private String message;
  private Throwable throwable;

  /**
   * Instantiates a new Rest error event.
   *
   * @param errorCode the error code
   * @param message   the message
   */
  public RestErrorEvent(ErrorCode errorCode, String message, Throwable throwable) {
    this.errorCode = errorCode;
    this.message = message;
    this.throwable = (throwable == null ?  new Throwable("") : throwable);
  }

  /**
   * Gets error code.
   *
   * @return the error code
   */
  public ErrorCode getErrorCode() {
    return errorCode;
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets Throwable.
   *
   * @return the throwable
   */
  public Throwable getThrowable() {
    return throwable;
  }

}
