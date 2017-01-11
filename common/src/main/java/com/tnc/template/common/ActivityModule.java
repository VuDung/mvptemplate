package com.tnc.template.common;

import android.content.Context;
import com.tnc.template.common.di.Names;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

@Module
public class ActivityModule {
  private Context context;

  public ActivityModule(Context context) {
    this.context = context;
  }

  @Provides
  @Named(Names.NAME_ACTIVITY)
  public Context provideContext(){
    return context;
  }
}
