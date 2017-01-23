package com.tnc.template.main;

import com.tnc.template.main.adapter.StoriesAdapter;
import com.tnc.template.main.list.ListStoryFragment;
import dagger.Subcomponent;

/**
 * Created by CUSDungVT on 1/10/2017.
 */
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
  void inject(MainActivity mainActivity);
  void inject(ListStoryFragment listStoryFragment);
  void inject(StoriesAdapter storiesAdapter);
}
