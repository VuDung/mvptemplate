package com.tnc.template.detail;

import com.tnc.template.data.DataModule;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.detail.comment.ItemPresenter;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by CUSDungVT on 1/24/2017.
 */
@Module
public class ItemDetailModule {
  @Provides
  ItemPresenter provideItemPresenter(@Named(DataModule.HN)ItemManager itemManager){
    return new ItemPresenter(itemManager);
  }
}
