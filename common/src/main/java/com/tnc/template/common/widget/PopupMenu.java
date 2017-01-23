package com.tnc.template.common.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by CUSDungVT on 1/21/2017.
 */

public interface PopupMenu {
  PopupMenu create(Context context, View anchor, int gravity);
  PopupMenu inflate(@MenuRes int menuRes);
  PopupMenu setMenuItemVisible(@IdRes int itemResId, boolean visible);
  PopupMenu setMenuItemTitle(@IdRes int itemResId, @StringRes int title);
  PopupMenu setMenuItemTitle(@IdRes int itemResId, String title);
  PopupMenu setOnMenuItemClickListener(OnMenuItemClickListener listener);
  void show();
  interface OnMenuItemClickListener{
    boolean onMenuItemClick(MenuItem menuItem);
  }

  class Implement implements PopupMenu{
    private android.support.v7.widget.PopupMenu supportMenu;
    @Override public PopupMenu create(Context context, View anchor, int gravity) {
      supportMenu = new android.support.v7.widget.PopupMenu(context, anchor, gravity);
      return this;
    }

    @Override public PopupMenu inflate(@MenuRes int menuRes) {
      supportMenu.inflate(menuRes);
      return this;
    }

    @Override public PopupMenu setMenuItemVisible(@IdRes int itemResId, boolean visible) {
      supportMenu.getMenu().findItem(itemResId).setVisible(visible);
      return this;
    }

    @Override public PopupMenu setMenuItemTitle(@IdRes int itemResId, @StringRes int title) {
      supportMenu.getMenu().findItem(itemResId).setTitle(title);
      return this;
    }

    @Override public PopupMenu setMenuItemTitle(@IdRes int itemResId, String title) {
      supportMenu.getMenu().findItem(itemResId).setTitle(title);
      return this;
    }

    @Override
    public PopupMenu setOnMenuItemClickListener(final OnMenuItemClickListener listener) {
      supportMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu
          .OnMenuItemClickListener() {
        @Override public boolean onMenuItemClick(MenuItem item) {
          listener.onMenuItemClick(item);
          return true;
        }
      });
      return this;
    }

    @Override public void show() {
      supportMenu.show();
    }
  }
}
