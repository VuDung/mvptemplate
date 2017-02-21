package com.tnc.template.main;

import com.tnc.template.data.entity.WebItem;

/**
 * Created by CUSDungVT on 1/23/2017.
 */

public interface ItemListener {
  void onItemSelected(WebItem item, int cacheMode);
}
