package com.tnc.template.main.list;

import android.support.annotation.Nullable;
import com.tnc.template.common.base.MvpPresenter;
import com.tnc.template.data.api.HackerNewsManager;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.api.response.ResponseListener;
import com.tnc.template.data.entity.Item;
import com.tnc.template.data.exception.NetworkException;
import java.lang.ref.WeakReference;

/**
 * Created by CUSDungVT on 1/13/2017.
 */

public class ListStoryPresenter extends MvpPresenter<ListStoryView>{
  private HackerNewsManager hackerNewsManager;

  public ListStoryPresenter(HackerNewsManager hackerNewsManager){
    this.hackerNewsManager = hackerNewsManager;
  }

  public void getStories(String fetchMode){
    if(getView() == null){
      return;
    }
    getView().showLoading();
    hackerNewsManager.getStories(
        fetchMode,
        ItemManager.MODE_CACHE,
        new ListResponseListener(this));
  }

  public void onItemLoaded(Item[] items){
    if(getView() == null) return;
    if(items.length > 0) {
      getView().swapContent(items);
    }else{
      getView().showEmptyView();
    }
  }
  public void onFailedItemLoad(Throwable throwable){
    if(getView() == null) return;
    if(throwable instanceof NetworkException){
      getView().showErrorNetwork();
    }else {
      getView().showErrorView(throwable.getMessage());
    }
  }

  private static class ListResponseListener implements ResponseListener<Item[]>{
    private final WeakReference<ListStoryPresenter> ref;
    public ListResponseListener(ListStoryPresenter presenter){
      ref = new WeakReference<>(presenter);
    }
    @Override public void onResponse(@Nullable Item[] data) {
      if(ref.get() != null && ref.get().isAttached()){
        ref.get().onItemLoaded(data);
      }
    }

    @Override public void onError(Throwable throwable) {
      if(ref.get() != null && ref.get().isAttached()){
        ref.get().onFailedItemLoad(throwable);
      }
    }
  }
}
