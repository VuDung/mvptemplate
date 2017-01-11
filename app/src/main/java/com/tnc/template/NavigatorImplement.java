package com.tnc.template;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.tnc.template.common.navigator.Navigator;
import com.tnc.template.main.MainActivity;

/**
 * Created by CUSDungVT on 12/26/2016.
 */

public class NavigatorImplement implements Navigator{

  @NonNull @Override public Intent goToMainActivity(Context context) {
    return new Intent(context, MainActivity.class);
  }
}
