package com.tnc.template.common;

import android.app.Application;
import android.content.Context;
import com.tnc.template.common.di.Injector;
import com.tnc.template.common.di.SuperComponent;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

public abstract class TheApp extends Application implements SuperComponent, Injector{

  public static TheApp get(Context context){
    return (TheApp)context.getApplicationContext();
  }

}
