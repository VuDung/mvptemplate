package com.tnc.template;

import com.tnc.template.common.TheApp;
import com.tnc.template.data.DataModule;
import com.tnc.template.login.LoginModule;
import com.tnc.template.main.MainModule;
import com.tnc.template.splash.SplashActivity;

/**
 * Created by CUSDungVT on 12/26/2016.
 */

public class TemplateApplication extends TheApp{
  private AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .dataModule(new DataModule())
        .build();
  }

  @Override public void inject(Object object) {
    if(object instanceof SplashActivity){
      appComponent.inject((SplashActivity)object);
    }
  }

  @SuppressWarnings("unchecked")
  @Override public <T> T plus(Object module) {
    if(module instanceof LoginModule){
      return (T)appComponent.plus((LoginModule)module);
    }
    if(module instanceof MainModule){
      return (T)appComponent.plus((MainModule)module);
    }
    return null;
  }
}
