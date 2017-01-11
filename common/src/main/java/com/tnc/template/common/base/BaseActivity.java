package com.tnc.template.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.tnc.template.common.TheApp;
import com.tnc.template.common.annotation.Injection;
import com.tnc.template.common.di.Injector;
import com.tnc.template.common.di.SuperComponent;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

public abstract class BaseActivity extends AppCompatActivity{

  protected abstract @LayoutRes int layoutRes();
  protected abstract @Injection void dependencyInjection();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int layoutRes = layoutRes();
    if(layoutRes != 0){
      setContentView(layoutRes);
      ButterKnife.bind(this);
    }
    dependencyInjection();
  }

  protected Injector injector(){
    return TheApp.get(this);
  }
  protected SuperComponent superComponent(){
    return TheApp.get(this);
  }
}
