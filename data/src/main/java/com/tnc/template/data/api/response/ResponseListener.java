package com.tnc.template.data.api.response;

import android.support.annotation.Nullable;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public interface ResponseListener<T> {

  void onResponse(@Nullable T data);
  void onError(Throwable throwable);

}
