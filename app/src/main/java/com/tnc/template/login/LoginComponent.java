package com.tnc.template.login;

import dagger.Subcomponent;

/**
 * Created by CUSDungVT on 12/26/2016.
 */
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {
  void inject(LoginActivity loginActivity);
  void inject(LoginFragment loginFragment);
}
