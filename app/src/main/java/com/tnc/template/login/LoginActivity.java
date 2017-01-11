package com.tnc.template.login;

import com.tnc.template.common.base.BaseActivity;
import com.tnc.template.common.di.Injector;

/**
 * Created by CUSDungVT on 12/26/2016.
 */

public class LoginActivity extends BaseActivity implements Injector{
  private LoginComponent loginComponent;

  @Override protected int layoutRes() {
    return 0;
  }

  @Override protected void dependencyInjection() {
    loginComponent().inject(this);
  }

  protected LoginComponent loginComponent(){
    if(loginComponent == null){
      loginComponent = superComponent().plus(new LoginModule());
    }
    return loginComponent;
  }

  @Override public void inject(Object object) {
    if(object instanceof LoginFragment){
      loginComponent().inject((LoginFragment)object);
    }
  }
}
