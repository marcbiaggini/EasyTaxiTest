package com.example.juanvilla.easytest.Beans;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.juanvilla.easytest.Activities.MainActivity;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

/**
 * Created by juan.villa on 26/08/17.
 */
@EBean
public class LocationHelper {
  @RootContext
  MainActivity context;

  public void onFinishLocation(Location location) {
    context.onGPSfinisehd(location);
  }

  public void checkGPS() {
    final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    final LocationListener locationListener = new LocationListener() {
      boolean checked = false;

      public void onLocationChanged(Location location) {
        if (!checked) {
          locationManager.removeUpdates(this);
          checked = true;
          onFinishLocation(location);
        }
      }

      public void onStatusChanged(String provider, int status, Bundle extras) {

      }

      public void onProviderEnabled(String provider) {

      }

      public void onProviderDisabled(String provider) {
        onFinishLocation(null);
      }
    };

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      return;
    }

    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
  }

}
