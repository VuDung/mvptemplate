package com.tnc.template;

import com.tnc.template.data.DataModule;
import com.tnc.template.login.LoginComponent;
import com.tnc.template.login.LoginModule;
import com.tnc.template.main.MainComponent;
import com.tnc.template.main.MainModule;
import com.tnc.template.splash.SplashActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by CUSDungVT on 12/26/2016.
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
  //plus
  LoginComponent plus(LoginModule loginModule);
  MainComponent plus(MainModule mainModule);
  //inject
  void inject(SplashActivity splashActivity);
}
