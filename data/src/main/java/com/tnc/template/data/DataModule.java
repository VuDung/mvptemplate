package com.tnc.template.data;

import com.tnc.template.data.api.HackerNewsManager;
import com.tnc.template.data.storage.FavoriteManager;
import com.tnc.template.data.storage.SessionManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by CUSDungVT on 12/22/2016.
 */
@Module(includes = NetworkModule.class)
public class DataModule {

  @Provides
  @Singleton
  SessionManager provideSessionManager(){
    return new SessionManager();
  }

  @Singleton
  @Provides
  FavoriteManager provideFavoriteManager(){
    return new FavoriteManager();
  }

  @Singleton
  @Provides
  HackerNewsManager provideHackerNewsManager(HackerNewsManager hackerNewsManager){
    return hackerNewsManager;
  }

}
