package com.example.juanvilla.easytest.Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

/**
 * Created by juan.villa on 26/08/17.
 */

public class DialogHelper {

  public static void show(AlertDialog dialog, Activity activity) {
    if (activity != null && !activity.isFinishing()) {
      try {
        dialog.show();
      } catch (Exception e) {
        Log.d("Log", "Dialog error: " + e.getMessage());
      }
    }
  }
}
