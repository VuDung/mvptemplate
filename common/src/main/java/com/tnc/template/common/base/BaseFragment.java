package com.tnc.template.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.tnc.template.common.annotation.Injection;
import com.tnc.template.common.di.Injector;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

public abstract class BaseFragment extends Fragment {

  protected abstract @LayoutRes int layoutRes();

  protected abstract @Injection void dependencyInjection(Bundle savedInstanceState);

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    int layoutRes = layoutRes();
    if (layoutRes != 0) {
      View view = inflater.inflate(layoutRes, container, false);
      ButterKnife.bind(this, view);
      dependencyInjection(savedInstanceState);
      return view;
    } else {
      dependencyInjection(savedInstanceState);
      return super.onCreateView(inflater, container, savedInstanceState);
    }
  }

  protected Injector injector() {
    if (getActivity() instanceof Injector) {
      return ((Injector) getActivity());
    } else {
      throw new IllegalStateException("host activity must implement "
          + Injector.class.getSimpleName());
    }
  }
}
