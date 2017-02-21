package com.tnc.template.common.navigator;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.tnc.template.data.entity.WebItem;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

public interface Navigator {

  @NonNull Intent goToMainActivity(Context context);

  void goToItemDetailActivity(Context context, WebItem item, int cacheMode);
}
