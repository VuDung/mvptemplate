package com.tnc.template.data.entity;

import android.content.Context;
import android.support.annotation.StringDef;
import android.text.Spannable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public interface WebItem {
  String JOB_TYPE = "job";
  String STORY_TYPE = "story";
  String COMMENT_TYPE = "comment";
  String POLL_TYPE = "poll";

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({
    JOB_TYPE,
    STORY_TYPE,
    COMMENT_TYPE,
    POLL_TYPE
  })
  @interface Type{}

  String getDisplayedTitle();

  String getUrl();

  String getItemId();

  long getLongItemId();

  boolean isStoryType();

  String getSource();

  Spannable getDisplayAuthor(Context context, boolean isHyperLink, int color);

  Spannable getDisplayTime(Context context);

  @WebItem.Type
  String getType();

  void setFavorite(boolean favorite);

  boolean isFavorite();


}
