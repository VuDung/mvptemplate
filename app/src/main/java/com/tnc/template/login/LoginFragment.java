package com.tnc.template.login;

import com.tnc.template.common.base.BaseFragment;

/**
 * Created by CUSDungVT on 1/11/2017.
 */

public class LoginFragment extends BaseFragment{
  @Override protected int layoutRes() {
    return 0;
  }

  @Override protected void dependencyInjection() {
    injector().inject(this);
  }
}
