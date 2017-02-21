package com.tnc.template.common;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

/**
 * Created by CUSDungVT on 1/17/2017.
 */

public abstract class RecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
  private Context context;
  private RecyclerView recyclerView;
  private LayoutInflater layoutInflater;

  @CallSuper
  public void attach(Context context, RecyclerView recyclerView){
    this.context = context;
    this.recyclerView = recyclerView;
    this.layoutInflater = LayoutInflater.from(context);

  }

  @CallSuper
  public void detach(){
    this.context = null;
    this.recyclerView = null;
  }

  protected boolean isAttached(){
    return context != null && recyclerView != null;
  }

  protected Context getContext(){
    return context;
  }

  protected LayoutInflater getLayoutInflater(){
    return layoutInflater;
  }
}
