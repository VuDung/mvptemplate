package com.tnc.template.data.storage;

import android.content.Context;
import com.tnc.template.data.util.PreferenceUtils;

/**
 * Created by CUSDungVT on 12/26/2016.
 */

public class AppPreference {
  private PreferenceUtils preferenceUtils;

  public AppPreference(Context context) {
    this.preferenceUtils = new PreferenceUtils(context);
  }

}
