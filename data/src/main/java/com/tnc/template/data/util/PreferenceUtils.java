package com.tnc.template.data.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by CUSDungVT on 12/26/2016.
 */

public class PreferenceUtils {

  private Context context;
  public PreferenceUtils (Context context){
    this.context = context;
  }

  /********* BOOLEAN *************/
  public boolean getBooleanPreference(String key, boolean defaultValue){
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
  }
  public void setBooleanPrefenence(String key, boolean value){
    PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
  }
  /********* INTEGER *************/
  public int getIntPreference(String key, int defaultValue){
    return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
  }
  public void setIntPrefenence(String key, int value){
    PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
  }
  /********* LONG *************/
  public long getLongPreference(String key, long defaultValue){
    return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, defaultValue);
  }
  public void setLongPrefenence(String key, long value){
    PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).apply();
  }
  /********* STRING *************/
  public String getStringPreference(String key, String defaultValue){
    return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
  }
  public void setStringPrefenence(String key, String value){
    PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
  }
  /********* FLOAT *************/
  public float getFloatPreference(String key, float defaultValue){
    return PreferenceManager.getDefaultSharedPreferences(context).getFloat(key, defaultValue);
  }
  public void setFloatPrefenence(String key, float value){
    PreferenceManager.getDefaultSharedPreferences(context).edit().putFloat(key, value).apply();
  }

  /********* CLEAR *************/
  public void clearPreference(String key){
    PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).apply();
  }
  /********* CLEAR ALL *************/
  public void getClearAllPreference(){
    PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
  }
}
