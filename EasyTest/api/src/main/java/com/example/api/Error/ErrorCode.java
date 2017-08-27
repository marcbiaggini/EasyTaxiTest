package com.example.api.Error;

/**
 * The enum Error code.
 */
public enum ErrorCode {
  /**
   * Connection error, poor connection
   */
  ConnectionError,

  /**
   * Json couldn't be converted
   */
  ConvertJSonError,

  /**
   * Accessibility, malformed url
   */
  NoHostSolved,

  /**
   * There is a converter, but couldn't serialize
   */
  SerializeError,

  /**
   * Expectation Failed, usually null
   */
  UnexpectedResponse,

  /**
   * Unknown error
   */
  UnknownError


}