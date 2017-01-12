package com.tnc.template.main.list;

import com.tnc.template.common.base.BaseFragment;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class ListStoryFragment extends BaseFragment{
  @Override protected int layoutRes() {
    return 0;
  }

  @Override protected void dependencyInjection() {
    injector().inject(this);
  }
}
