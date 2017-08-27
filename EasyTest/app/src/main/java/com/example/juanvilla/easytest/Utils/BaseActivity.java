package com.example.juanvilla.easytest.Utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.api.Error.Error;
import com.example.api.Error.RestErrorEvent;
import com.example.api.Helpers.BusProvider;
import com.example.juanvilla.easytest.R;
import com.squareup.otto.Subscribe;

/**
 * Created by juan.villa on 26/08/17.
 */

public class BaseActivity extends AppCompatActivity {

  protected MenuItem itemFavorites;

  protected Snackbar snackbar;
  private final Object busCallback = new Object() {
    @Subscribe
    public void handleRestErrorEvent(RestErrorEvent event) {
      onError(new Error(event.getErrorCode(), event.getMessage()));
    }
  };

  @Override
  protected void onResume() {
    super.onResume();
    BusProvider.getInstance().register(busCallback);
  }

  @Override
  protected void onPause() {
    super.onPause();
    try {
      BusProvider.getInstance().unregister(busCallback);
    } catch (Exception e) {
      Log.e("log", "busCallback is not registered!");
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    setupMenuItems(menu);



    return true;
  }

  public void setupMenuItems(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    itemFavorites = menu.findItem(R.id.favorites);
    itemFavorites.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_blackdp));
  }

  public void onError(Error e) {
    showSnackbar(getResources().getString(R.string.unknown_error_msg));
  }

  public void showNoActionSnackbar(final String msg) {

    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (snackbar == null || !snackbar.isShownOrQueued()) {
          try {
            View parentView = BaseActivity.this.findViewById(android.R.id.content).getRootView();
            snackbar = Snackbar.make(parentView, msg, Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("OK", new View.OnClickListener() {
              @Override
              public void onClick(View v) {
              }
            });

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorLightDark));
            snackbarView.setPadding(60, 15, 60, 160);

            snackbar.setActionTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
            snackbar.show();

          } catch (Exception e) {
            Log.e("log", "Couldn't show snackBar: " + e);
          }
        }
      }
    });
  }

  public void showSnackbar(final String msg) {

    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (snackbar == null || !snackbar.isShownOrQueued()) {
          try {
            View parentView = BaseActivity.this.findViewById(android.R.id.content).getRootView();
            snackbar = Snackbar.make(parentView, msg, Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("OK", new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                recreate();
                snackbar.dismiss();
              }
            });

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorLightDark));
            snackbarView.setPadding(60, 15, 60, 160);

            snackbar.setActionTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
            snackbar.show();


          } catch (Exception e) {
            Log.e("log", "Couldn't show snackBar: " + e);
          }
        }
      }
    });
  }
}
