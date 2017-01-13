package com.tnc.template.main.list;

import com.tnc.template.data.entity.Item;

/**
 * Created by CUSDungVT on 1/13/2017.
 */

public interface ListStoryView {
  void showLoading();
  void hideLoading();
  void showErrorView(String message);
  void hideErrorView();
  void showErrorNetwork();
  void hideErrorNetwork();
  void showEmptyView();
  void hideEmptyView();

  void hideContent();
  void swapContent(Item[] items);

}
