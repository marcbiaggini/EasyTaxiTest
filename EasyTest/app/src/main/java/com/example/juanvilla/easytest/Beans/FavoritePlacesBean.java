package com.example.juanvilla.easytest.Beans;

import android.content.Context;

import com.example.api.Helpers.ApiServices;
import com.example.api.Models.Place;
import com.example.api.Models.Places;
import com.example.juanvilla.easytest.R;
import com.example.juanvilla.easytest.Utils.BaseActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;

/**
 * Created by juan.villa on 26/08/17.
 */
@EBean
public class FavoritePlacesBean {
  @RootContext
  Context context;

  ArrayList<Place> favorites = new ArrayList<>();

  @Background
  public void getFavoritesPlaces(){
    setFavoritesPlaces(ApiServices.getServiceInterface().getFavoritesPlaces());
  }

  @UiThread
  protected void setFavoritesPlaces(Places response){
    if(response!=null) {
      if(favorites.size()==0) {
        setFavorites(response.getFavorites());
      }
    }else {
      ((BaseActivity) context).showSnackbar(context.getString(R.string.unknown_error_msg));
    }
  }

  public ArrayList<Place> getFavorites() {
    return favorites;
  }

  public void setFavorites(ArrayList<Place> favorites) {
      this.favorites.clear();
      this.favorites.addAll(favorites);
  }
}
