package com.tnc.template;

import android.app.Application;
import android.content.Context;
import com.tnc.template.common.di.Names;
import com.tnc.template.common.navigator.Navigator;
import com.tnc.template.data.Network;
import com.tnc.template.data.storage.AppPreference;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by CUSDungVT on 12/26/2016.
 */
@Module
public class AppModule {
  private Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Singleton
  @Provides
  @Named(Names.NAME_APPLICATION) Context provideContext(){
    return application;
  }

  @Singleton
  @Provides
  Application provideApplication(){
    return application;
  }

  @Singleton
  @Provides
  @Named("END_POINT_URL") String provideEndPointUrl() {
    return BuildConfig.END_POINT_URL;
  }

  @Singleton
  @Provides
  Navigator provideNavigator(){
    return new NavigatorImplement();
  }

  @Singleton
  @Provides
  Network provideNetwork(){
    return new NetworkImplement(application);
  }

  @Singleton
  @Provides
  AppPreference provideAppPreference(){
    return new AppPreference(application);
  }

}
