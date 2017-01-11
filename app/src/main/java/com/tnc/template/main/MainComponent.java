package com.tnc.template.main;

import dagger.Subcomponent;

/**
 * Created by CUSDungVT on 1/10/2017.
 */
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
  void inject(MainActivity mainActivity);
}
