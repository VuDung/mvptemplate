package com.tnc.template.data.entity;

import android.content.Context;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import com.tnc.template.data.R;
import com.tnc.template.data.util.AppUtils;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class Favorite implements WebItem{
  private String itemId;
  private String url;
  private String title;
  private long time;
  private boolean isFavorite;
  private Spannable displayTime;
  private Spannable displayAuthor = new SpannableString("");

  public Favorite(String itemId, String url, String title, long time) {
    this.itemId = itemId;
    this.url = url;
    this.title = title;
    this.time = time;
    this.isFavorite = true;
  }

  @Override public String getDisplayedTitle() {
    return title;
  }

  @Override public String getUrl() {
    return url;
  }

  @Override public String getItemId() {
    return itemId;
  }

  @Override public long getLongItemId() {
    return Long.valueOf(itemId);
  }

  @Override public boolean isStoryType() {
    return true;
  }

  @Override public String getSource() {
    return TextUtils.isEmpty(url) ? null : Uri.parse(url).getHost();
  }

  @Override public Spannable getDisplayAuthor(Context context, boolean isHyperLink, int color) {
    return displayAuthor;
  }

  @Override public Spannable getDisplayTime(Context context) {
    if(displayTime == null){
      displayTime = new SpannableString(context.getString(R.string.saved, AppUtils.getAbbreviatedTimeSpan(time)));
    }
    return displayTime;
  }

  @Override public String getType() {
    return STORY_TYPE;
  }

  @Override public void setFavorite(boolean favorite) {
    this.isFavorite = favorite;
  }

  @Override public boolean isFavorite() {
    return isFavorite;
  }

  public long getTime(){
    return time;
  }
}
