package com.tnc.template.data.storage;

import android.content.Context;
import com.tnc.template.data.R;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.util.PreferenceUtils;

/**
 * Created by CUSDungVT on 12/26/2016.
 */

public class AppPreference {
  private Context context;
  private PreferenceUtils preferenceUtils;

  public AppPreference(Context context) {
    this.context = context;
    this.preferenceUtils = new PreferenceUtils(context);
  }

  public String getFetchMode(){
    return preferenceUtils.getStringPreference(context.getString(R.string.pref_fetch_mode), ItemManager.TOP_FETCH_MODE);
  }

  public void setFetchMode(@ItemManager.FetchMode String fetchMode){
    preferenceUtils.setStringPrefenence(context.getString(R.string.pref_fetch_mode), fetchMode);
  }
}
