package com.example.api.Error;

import android.util.Log;

import com.example.api.Helpers.BusProvider;

import org.androidannotations.annotations.EBean;
import org.springframework.core.NestedRuntimeException;

@EBean
public class RestErrorHandler implements org.androidannotations.rest.spring.api.RestErrorHandler {

  @Override
  public void onRestClientExceptionThrown(NestedRuntimeException e) {
    ErrorCode code;

    switch (e.getClass().getSimpleName()) {
      case "ResourceAccessException":
        code = ErrorCode.ConnectionError;
        break;

      case "RestClientException":
        code = ErrorCode.ConvertJSonError;
        break;

      case "HttpClientErrorException":
        code = ErrorCode.NoHostSolved;
        break;

      case "HttpMessageNotReadableException":
        code = ErrorCode.SerializeError;
        break;

      default:
        code = ErrorCode.UnknownError;
        break;
    }

    BusProvider.getInstance().post(new RestErrorEvent(code, e.getLocalizedMessage(), e.getMostSpecificCause()));
    Log.e("AMAROlog", code + ": " + e.getLocalizedMessage());
  }
}

