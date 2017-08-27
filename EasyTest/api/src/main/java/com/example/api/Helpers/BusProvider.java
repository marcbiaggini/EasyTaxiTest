package com.example.api.Helpers;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

public final class BusProvider extends Bus {

  private final Handler mainThread = new Handler(Looper.getMainLooper());
  private static BusProvider mInstance;

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static synchronized BusProvider getInstance() {
    if (mInstance == null) {
      mInstance = new BusProvider();
    }
    return mInstance;
  }

  @Override
  public void post(final Object event) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      super.post(event);
    } else {
      mainThread.post(new Runnable() {
        @Override
        public void run() {
          BusProvider.super.post(event);
        }
      });

    }
  }

}
