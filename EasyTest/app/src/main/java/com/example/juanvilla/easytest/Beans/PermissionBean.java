package com.example.juanvilla.easytest.Beans;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.juanvilla.easytest.Activities.MainActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

/**
 * Created by juan.villa on 27/08/17.
 */
@EBean
public class PermissionBean {
  @RootContext
  MainActivity context;

  GoogleApiClient googleApiClient;

  @Bean
  LocationHelper locationHelper;

  @UiThread
  public void onLocationPermissionGranted() {
    askEnableGPS();
  }

  public void askPermission() {
    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
  }

  public boolean grantedLocationPermission() {
    int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
    return result == PackageManager.PERMISSION_GRANTED;
  }

  public void askEnableGPS() {
    if (googleApiClient == null) {
      googleApiClient = new GoogleApiClient.Builder(context)
          .addApi(LocationServices.API)
          .build();
      googleApiClient.connect();

      LocationRequest locationRequest = LocationRequest.create();
      locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
      locationRequest.setInterval(1000);
      locationRequest.setFastestInterval(5 * 100);
      LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

      builder.setAlwaysShow(true);

      PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

      result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
        @Override
        public void onResult(@NonNull LocationSettingsResult result) {
          final Status status = result.getStatus();

          switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
              locationHelper.checkGPS();
              break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
              try {
                status.startResolutionForResult(context, 1000);
              } catch (IntentSender.SendIntentException e) {
                Log.e("log","intent SenderError");
              }
              break;
          }
        }
      });
    }
  }

  public LocationHelper getLocationHelper() {
    return locationHelper;
  }
}
