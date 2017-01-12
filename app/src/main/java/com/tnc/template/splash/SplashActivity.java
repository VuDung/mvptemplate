package com.tnc.template.splash;

import android.os.Bundle;
import android.os.Handler;
import com.tnc.template.R;
import com.tnc.template.common.base.BaseActivity;
import com.tnc.template.common.navigator.Navigator;
import javax.inject.Inject;

/**
 * Created by CUSDungVT on 12/26/2016.
 */

public class SplashActivity extends BaseActivity{
  @Inject Navigator navigator;

  @Override protected int layoutRes() {
    return R.layout.activity_splash;
  }

  @Override protected void dependencyInjection(Bundle savedInstanceState) {
    injector().inject(this);
  }

  @Override protected void onResume() {
    super.onResume();
    new Handler().postDelayed(() -> {
      startActivity(navigator.goToMainActivity(SplashActivity.this));
      finish();
    }, 2000);
  }
}
