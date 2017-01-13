package com.tnc.template.common.base;

import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;

/**
 * Created by CUSDungVT on 1/11/2017.
 */

public class MvpPresenter<MvpView> {
  private WeakReference<MvpView> viewRef;

  @Nullable public MvpView getView(){
    return viewRef == null ? null : viewRef.get();
  }

  public void attachView(MvpView view){
    this.viewRef = new WeakReference<>(view);
  }

  public void detachView(){
    if(viewRef != null){
      this.viewRef.clear();
    }
  }

  public boolean isAttached(){
    return viewRef.get() != null;
  }

}
