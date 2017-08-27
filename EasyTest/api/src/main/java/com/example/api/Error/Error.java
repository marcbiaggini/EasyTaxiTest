package com.example.api.Error;

import org.springframework.web.client.RestClientException;

/**
 * The type Error.
 */
public final class Error extends RestClientException {
  private static final long serialVersionUID = -710488268845379700L;

  private final ErrorCode errorCode;

  /**
   * Instantiates a new Error.
   *
   * @param errorCode the error code
   * @param exception the exception
   */
  public Error(final ErrorCode errorCode, final Throwable exception) {
    super("", exception);
    this.errorCode = errorCode;
  }

  /**
   * Instantiates a new Error.
   *
   * @param errorCode the error code
   * @param message   the message
   */
  public Error(final ErrorCode errorCode, final String message) {
    super(message);
    this.errorCode = errorCode;
  }

  /**
   * Gets error code.
   *
   * @return the error code
   */
  public ErrorCode getErrorCode() {
    return this.errorCode;
  }
}
