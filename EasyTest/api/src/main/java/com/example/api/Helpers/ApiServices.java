package com.example.api.Helpers;

import android.content.Context;

import com.example.api.Error.RestErrorHandler;
import com.example.api.Interfaces.ServiceInterface;
import com.example.api.Interfaces.ServiceInterface_;

public class ApiServices {

  private static ServiceInterface serviceInterface = null;
  /**
   * Initialize services.
   *
   * @param context      the context
   * @param errorHandler the error handler
   */
  public static void initializeServices(Context context, RestErrorHandler errorHandler) {
    serviceInterface = new ServiceInterface_(context);
    serviceInterface.setRestErrorHandler(errorHandler);
  }

  public static ServiceInterface getServiceInterface() {
    return serviceInterface;
  }
}
