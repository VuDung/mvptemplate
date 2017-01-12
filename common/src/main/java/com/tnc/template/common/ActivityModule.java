package com.tnc.template.common;

import android.content.Context;
import com.tnc.template.data.DataModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

@Module(includes = DataModule.class)
public class ActivityModule {
  private Context context;

  public ActivityModule(Context context) {
    this.context = context;
  }

  @Singleton
  @Provides
  Context provideContext(){
    return context;
  }
}
