package com.tnc.template.data.entity;

import android.content.Context;
import android.text.Spannable;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class HackerNewsItem implements Item{
  private long id;

  public HackerNewsItem(long id) {
    this.id = id;
  }

  @Override public String getDisplayedTitle() {
    return null;
  }

  @Override public String getUrl() {
    return null;
  }

  @Override public String getItemId() {
    return String.valueOf(id);
  }

  @Override public long getLongItemId() {
    return id;
  }

  @Override public boolean isStoryType() {
    return false;
  }

  @Override public String getSource() {
    return null;
  }

  @Override public Spannable getDisplayAuthor(Context context, boolean isHyperLink, int color) {
    return null;
  }

  @Override public Spannable getDisplayTime(Context context) {
    return null;
  }

  @Override public String getType() {
    return null;
  }

  @Override public void setFavorite(boolean favorite) {

  }

  @Override public boolean isFavorite() {
    return false;
  }
}
