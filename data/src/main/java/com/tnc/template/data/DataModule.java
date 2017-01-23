package com.tnc.template.data;

import android.content.Context;
import com.tnc.template.data.api.HackerNewsManager;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.api.factory.RestServiceFactory;
import com.tnc.template.data.storage.FavoriteManager;
import com.tnc.template.data.storage.SessionManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by CUSDungVT on 12/22/2016.
 */
@Module(includes = NetworkModule.class)
public class DataModule {
  public static final String HN = "hackernews";
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
  @Named(HN)
  ItemManager provideHackerNewsManager(Context context,
      RestServiceFactory restServiceFactory,
      SessionManager sessionManager,
      FavoriteManager favoriteManager){
    return new HackerNewsManager(context, restServiceFactory, sessionManager, favoriteManager);
  }

}
