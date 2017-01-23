package com.tnc.template.main;

import com.tnc.template.data.DataModule;
import com.tnc.template.data.api.HackerNewsManager;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.main.adapter.StoriesAdapter;
import com.tnc.template.main.list.ListStoryPresenter;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by CUSDungVT on 1/10/2017.
 */

@Module
public class MainModule {

  @Provides
  ListStoryPresenter provideListStoryPresenter(@Named(DataModule.HN) ItemManager hackerNewsManager){
    return new ListStoryPresenter(hackerNewsManager);
  }

}
