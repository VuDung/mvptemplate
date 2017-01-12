package com.tnc.template.data.api;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import com.tnc.template.data.api.response.ResponseListener;
import com.tnc.template.data.entity.Item;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public interface ItemManager {
  /**
   * CacheMode
   */
  int MODE_DEFAULT = 0;
  int MODE_CACHE = 1;
  int MODE_NETWORK = 2;

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({
    MODE_DEFAULT,
    MODE_CACHE,
    MODE_NETWORK
  })
  @interface CacheMode{}

  /**
   * FetchMode
   */
  String TOP_FETCH_MODE = "top";
  String NEW_FETCH_MODE = "new";
  String ASK_FETCH_MODE = "ask";
  String SHOW_FETCH_MODE = "show";
  String JOBS_FETCH_MODE = "jobs";
  String BEST_FETCH_MODE = "best";

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({
      TOP_FETCH_MODE,
      NEW_FETCH_MODE,
      ASK_FETCH_MODE,
      SHOW_FETCH_MODE,
      JOBS_FETCH_MODE,
      BEST_FETCH_MODE
  })
  @interface FetchMode {}


  void getStories(String filter, @CacheMode int mode, ResponseListener<Item[]> listener);
  void getItem(String itemId, @CacheMode int mode, ResponseListener<Item> listener);
}
