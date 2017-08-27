package com.example.juanvilla.easytest;

import android.app.Application;

import com.example.api.Error.RestErrorHandler;
import com.example.api.Helpers.ApiServices;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

/**
 * Created by juan.villa on 26/08/17.
 */
@EApplication
public class EasyApp extends Application {
  @Bean
  RestErrorHandler errorHandler;
  @Override
  public void onCreate() {
    super.onCreate();
    ApiServices.initializeServices(this,errorHandler);
  }
}
