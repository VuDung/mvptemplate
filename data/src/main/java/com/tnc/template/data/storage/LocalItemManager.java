package com.tnc.template.data.storage;

import android.content.Context;
import android.support.v4.app.LoaderManager;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public interface LocalItemManager<T> {

  int getSize();

  T getItem(int position);

  void attach(Context context, LoaderManager loaderManager, Observer observer, String filter);

  void detach();

  interface Observer{
    void onChanged();
  }
}
