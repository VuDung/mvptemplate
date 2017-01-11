package com.tnc.template;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tnc.template.data.Network;

/**
 * Created by CUSDungVT on 12/26/2016.
 */

public class NetworkImplement implements Network {
  private final ConnectivityManager connectivityManager;

  NetworkImplement(Application application) {
    connectivityManager =
        (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  @Override public boolean isNetworkAvailable() {
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    return activeNetwork != null && activeNetwork.isConnected();
  }
}
