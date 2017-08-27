package com.example.juanvilla.easytest.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.api.Models.Place;
import com.example.juanvilla.easytest.Adapters.FavoriteAdapter;
import com.example.juanvilla.easytest.Beans.FavoritePlacesBean;
import com.example.juanvilla.easytest.Beans.PermissionBean;
import com.example.juanvilla.easytest.Fragments.FavoriteListFragment;
import com.example.juanvilla.easytest.Interfaces.OnListFragmentInteractionListener;
import com.example.juanvilla.easytest.R;
import com.example.juanvilla.easytest.Utils.BaseActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnMapReadyCallback, OnListFragmentInteractionListener, GoogleMap.OnMarkerDragListener {

  private GoogleMap mMap;

  FavoriteListFragment favoriteListFragment = new FavoriteListFragment();

  Place currentPlace;

  @FragmentById(R.id.map)
  SupportMapFragment mapFragment;

  @FragmentById(R.id.place_autocomplete_fragment)
  PlaceAutocompleteFragment autocompleteFragment;

  @Bean
  FavoritePlacesBean favoritePlacesBean;

  @Bean
  PermissionBean permissionBean;

  Gson gson = new Gson();

  protected Snackbar deleteSnackbar;


  @AfterViews
  public void init() {
    favoritePlacesBean.getFavoritesPlaces();
    mapFragment.getMapAsync(this);
    mapFragment.setRetainInstance(true);
    setAutocompleteFragment();
  }

  @Click(R.id.gotoCurrent)
  public void gotoCurrent(){
    if(currentPlace != null) {
      LatLng mark = new LatLng(currentPlace.getLatitude(), currentPlace.getLongitude());
      mMap.moveCamera(CameraUpdateFactory.newLatLng(mark));
      mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
  }

  @SuppressWarnings({"MissingPermission"})
  @UiThread
  public void onGPSfinisehd(Location location) {
    if (location != null) {
      findViewById(R.id.gotoCurrent).setVisibility(View.VISIBLE);
      if(currentPlace == null) {
        mMap.setMyLocationEnabled(true);
        currentPlace = new Place().withName(getString(R.string.current_place))
            .withLatitude(location.getLatitude()).withLongitude(location.getLongitude());
        addCurrentmark(currentPlace, true);
      }
    }
  }

  @Override
  public void onListFragmentInteraction(Place item) {
    favoriteListFragment.dismiss();
    addSelectedmark(item);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
    if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      permissionBean.onLocationPermissionGranted();
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setOnMarkerDragListener(this);
    markClick();
    if (permissionBean.grantedLocationPermission()) {
      permissionBean.onLocationPermissionGranted();
    } else {
      permissionBean.askPermission();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()){
      case R.id.favorites:
        favoriteListFragment.setAdapter(new FavoriteAdapter(favoritePlacesBean.getFavorites(),this));
        favoriteListFragment.setRetainInstance(true);
        favoriteListFragment.show(getSupportFragmentManager(),"fragment_favorites");
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1000) {
      if (resultCode == Activity.RESULT_OK) {
       permissionBean.getLocationHelper().checkGPS();

      } else {
        permissionBean.getLocationHelper().checkGPS();
        showNoActionSnackbar(getString(R.string.gps_message));
      }
    }
  }

  private void setAutocompleteFragment(){
    autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

      @Override
      public void onPlaceSelected(com.google.android.gms.location.places.Place place) {
        Place selectedPlace = new Place().withName(place.getName().toString())
            .withLatitude(place.getLatLng().latitude)
            .withLongitude(place.getLatLng().longitude);
        if(favoritePlacesBean.getFavorites().indexOf(selectedPlace)==-1){
          favoritePlacesBean.getFavorites().add(selectedPlace);
        }
        addSelectedmark(selectedPlace);
      }

      @Override
      public void onError(Status status) {
        // TODO: Handle the error.
        showNoActionSnackbar("An error occurred: " + status);
      }
    });
  }

  private void addCurrentmark(Place place,boolean animate) {
    LatLng mark = new LatLng(place.getLatitude(), place.getLongitude());
    MarkerOptions markerOptions = new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_head_small))
        .position(mark)
        .title(place.getName())
        .draggable(true);
    mMap.addMarker(markerOptions);
    if(animate) {
      mMap.moveCamera(CameraUpdateFactory.newLatLng(mark));
      mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
  }

  private void addSelectedmark(Place place) {
      mMap.clear();
      LatLng mark = new LatLng(place.getLatitude(), place.getLongitude());
      mMap.addMarker(new MarkerOptions().position(mark).title(place.getName()).draggable(false));
      mMap.moveCamera(CameraUpdateFactory.newLatLng(mark));
      mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
      addCurrentmark(currentPlace,false);
  }

  @Override
  public void onMarkerDragStart(Marker marker) {
  }

  @Override
  public void onMarkerDrag(Marker marker) {

  }

  @Override
  public void onMarkerDragEnd(Marker marker) {
    LatLng dragPosition = marker.getPosition();
    currentPlace = new Place().withName(getString(R.string.current_place))
        .withLatitude(dragPosition.latitude).withLongitude(dragPosition.longitude);
  }


  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    String currentSaveLocation = gson.toJson(currentPlace);
    String favoritesPlaces = gson.toJson(favoritePlacesBean.getFavorites());
    savedInstanceState.putString("CurrentLocation",currentSaveLocation);
    savedInstanceState.putString("FavoritesPlaces",favoritesPlaces);
    super.onSaveInstanceState(savedInstanceState);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    currentPlace = gson.fromJson(savedInstanceState.getString("CurrentLocation"),Place.class);
    favoritePlacesBean.setFavorites((ArrayList<Place>)gson.fromJson(savedInstanceState.getString("FavoritesPlaces"), new TypeToken<ArrayList<Place>>(){}.getType()));
  }

  public void  markClick(){
    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
      public void onInfoWindowClick(Marker marker) {

        if(!marker.getTitle().equalsIgnoreCase(getString(R.string.current_place))) {
          Place remove = new Place().withName(marker.getTitle())
              .withLatitude(marker.getPosition().latitude)
              .withLongitude(marker.getPosition().longitude);

          showDeleteSnackbar(getString(R.string.delete_places), remove);
        }
      }
    });
  }

  public void showDeleteSnackbar(final String msg, final Place remove) {

    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (deleteSnackbar == null || !deleteSnackbar.isShownOrQueued()) {
          try {
            View parentView = findViewById(android.R.id.content).getRootView();
            deleteSnackbar = Snackbar.make(parentView, msg, Snackbar.LENGTH_LONG).setAction("Sim", new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                deleteSnackbar.dismiss();
                favoritePlacesBean.getFavorites().remove(remove);
              }
            });

            View snackbarView = deleteSnackbar.getView();
            snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorLightDark));
            snackbarView.setPadding(60, 15, 60, 160);
            snackbarView.bringToFront();

            deleteSnackbar.setActionTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
            deleteSnackbar.show();

          } catch (Exception e) {
            Log.e("log", "Couldn't show snackBar: " + e);
          }
        }
      }
    });
  }

}
