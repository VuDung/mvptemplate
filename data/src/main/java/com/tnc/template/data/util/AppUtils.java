package com.tnc.template.data.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import com.tnc.template.data.BuildConfig;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class AppUtils {
  private static final String ABBR_YEAR = "y";
  private static final String ABBR_WEEK = "w";
  private static final String ABBR_DAY = "d";
  private static final String ABBR_HOUR = "h";
  private static final String ABBR_MINUTE = "m";

  private static final String HOST_USER = "user";
  public static final int HOT_FACTOR = 3;
  public static final int HOT_THRESHOLD_HIGH = 300;
  public static final int HOT_THRESHOLD_NORMAL = 100;
  public static final int HOT_THRESHOLD_LOW = 10;
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

  public static Uri createUserUri(@NonNull String userId){
    return new Uri.Builder()
        .scheme(BuildConfig.APPLICATION_ID)
        .authority(HOST_USER)
        .path(userId)
        .build();
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public static CharSequence fromHtml(CharSequence htmlText){
    return fromHtml(htmlText, false);
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public static CharSequence fromHtml(CharSequence htmlText, boolean compact){
    if(TextUtils.isEmpty(htmlText)){
      return null;
    }
    CharSequence spanned;
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
      spanned = Html.fromHtml(htmlText.toString(), compact ? Html.FROM_HTML_MODE_COMPACT : Html.FROM_HTML_MODE_LEGACY);
    }else{
      spanned = Html.fromHtml(htmlText.toString());
    }
    return spanned;
  }
}
