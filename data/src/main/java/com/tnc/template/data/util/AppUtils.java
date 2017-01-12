package com.tnc.template.data.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class AppUtils {
  private static final String ABBR_YEAR = "y";
  private static final String ABBR_WEEK = "w";
  private static final String ABBR_DAY = "d";
  private static final String ABBR_HOUR = "h";
  private static final String ABBR_MINUTE = "m";

  public static String getAbbreviatedTimeSpan(long timeMillis) {
    long span = Math.max(System.currentTimeMillis() - timeMillis, 0);
    if (span >= DateUtils.YEAR_IN_MILLIS) {
      return (span / DateUtils.YEAR_IN_MILLIS) + ABBR_YEAR;
    }
    if (span >= DateUtils.WEEK_IN_MILLIS) {
      return (span / DateUtils.WEEK_IN_MILLIS) + ABBR_WEEK;
    }
    if (span >= DateUtils.DAY_IN_MILLIS) {
      return (span / DateUtils.DAY_IN_MILLIS) + ABBR_DAY;
    }
    if (span >= DateUtils.HOUR_IN_MILLIS) {
      return (span / DateUtils.HOUR_IN_MILLIS) + ABBR_HOUR;
    }
    return (span / DateUtils.MINUTE_IN_MILLIS) + ABBR_MINUTE;
  }

  public static boolean isOnWiFi(Context context) {
    NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    return activeNetwork != null &&
        activeNetwork.isConnectedOrConnecting() &&
        activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
  }

  public static boolean hasConnection(Context context) {
    NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
  }
}
