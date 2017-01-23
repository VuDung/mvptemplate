package com.tnc.template.data.entity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.View;
import com.tnc.template.data.R;
import com.tnc.template.data.api.HackerNewsManager;
import com.tnc.template.data.util.AppUtils;
import java.util.Locale;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class HackerNewsItem implements Item {
  private static final String AUTHOR_SEPARATOR = " - ";
  private @Keep long id 	;//The item's unique id.
  private @Keep boolean deleted 	;//true if the item is deleted.
  private @Keep String type 	;//The type of item. One of "job", "story", "comment", "poll", or "pollopt".
  private @Keep String by 	;//The username of the item's author.
  private @Keep long time 	;//Creation date of the item, in Unix Time.
  private @Keep String text 	;//The comment, story or poll text. HTML.
  private @Keep boolean dead 	;//true if the item is dead.
  private @Keep long parent 	;//The item's parent. For comments, either another comment or the relevant story. For pollopts, the relevant poll.
  private @Keep long[] kids 	;//The ids of the item's comments, in ranked display order.
  private @Keep String url 	;//The URL of the story.
  private @Keep int score 	;//The story's score, or the votes for a pollopt.
  private @Keep String title 	;//The title of the story, poll or job.
  private @Keep long[] parts 	;//A list of related pollopts, in display order.
  private @Keep int descendants 	;//In the case of stories or polls, the total comment count.

  private boolean isFavorited;
  private boolean isViewed;
  private int localRevision = -1;
  private int level;
  private int rank;
  private long next, previous;
  private HackerNewsItem[] kidItems;
  private Spannable displayedTime;
  private Spannable disPlayedAuthor;
  private CharSequence displayedText;
  private final String TAG = HackerNewsItem.class.getSimpleName();

  public HackerNewsItem(long id) {
    this.id = id;
  }

  private HackerNewsItem(long id, int level){
    this(id);
    this.level = level;
  }

  @Override public void populate(Item info) {
    title = info.getTitle();
    time = info.getTime();
    by = info.getBy();
    kids = info.getKids();
    url = info.getRawUrl();
    text = info.getText();
    type = info.getRawType();
    descendants = info.getDescendants();
    deleted = info.isDeleted();
    dead = info.isDead();
    score = info.getScore();
    isViewed = info.isViewed();
    isFavorited = info.isFavorite();
    localRevision = 1;
  }

  @Override public int getDescendants() {
    return descendants;
  }

  @Override public String getRawType() {
    return type;
  }

  @Override public String getRawUrl() {
    return url;
  }

  @Override public long[] getKids() {
    return kids;
  }

  @Override public int getKidCount() {
    return kids != null ? kids.length : 0;
  }

  @Override public Item[] getKidItems() {
    if(kids == null || kids.length == 0){
      return new HackerNewsItem[0];
    }
    if(kidItems == null){
      kidItems = new HackerNewsItem[kids.length];
      for(int i = 0; i < kids.length; i++){
        HackerNewsItem item = new HackerNewsItem(kids[i], level + 1);
        item.rank = i + 1;
        if (i > 0) {
          item.previous = kids[i - 1];
        }
        if (i < kids.length - 1) {
          item.next = kids[i + 1];
        }
        kidItems[i] = item;
      }
    }
    return kidItems;
  }

  @Override public String getBy() {
    return by;
  }

  @Override public long getTime() {
    return time;
  }

  @Override public String getTitle() {
    switch (getType()){
      case COMMENT_TYPE:
        return text;
      default:
        return title;
    }
  }

  @Override public String getText() {
    return text;
  }

  @Override public int getLocalRevision() {
    return localRevision;
  }

  @Override public void setLocalRevision(int revision) {
    this.localRevision = revision;
  }

  @Override public boolean isViewed() {
    return isViewed;
  }

  @Override public void setViewed(boolean viewed) {
    this.isViewed = viewed;
  }

  @Override public int getScore() {
    return score;
  }

  @Override public boolean isDead() {
    return dead;
  }

  @Override public boolean isDeleted() {
    return deleted;
  }

  @Override public int getRank() {
    return rank;
  }

  public void setRank(int rank){
    this.rank = rank;
  }

  //WebItem
  @Override public String getDisplayedTitle() {
    switch (getType()) {
      case COMMENT_TYPE:
        return text;
      default:
        return title;
    }
  }

  @Override public String getUrl() {
    switch (getType()){
      case COMMENT_TYPE:
        return getItemUrl(getItemId());
      default:
        return TextUtils.isEmpty(url) ? getItemUrl(getItemId()) : url;
    }
  }

  private String getItemUrl(String itemId){
    return String.format(Locale.US, HackerNewsManager.WEB_ITEM_PATH, itemId);
  }

  @Override public String getItemId() {
    return String.valueOf(id);
  }

  @Override public long getLongItemId() {
    return id;
  }

  @Override public boolean isStoryType() {
    switch (getType()) {
      case COMMENT_TYPE:
        return false;
      default:
        return true;
    }
  }

  @Override public String getSource() {
    return TextUtils.isEmpty(getUrl()) ? null : Uri.parse(getUrl()).getHost();
  }

  @Override public Spannable getDisplayAuthor(Context context, boolean isHyperLink, int color) {
    int defaultColor = ContextCompat.getColor(context, R.color.colorPrimary);
    if(disPlayedAuthor == null){
      if(TextUtils.isEmpty(by)){
        disPlayedAuthor = new SpannableString("");
      }else{

        disPlayedAuthor = createAuthorSpannable(isHyperLink);
      }
    }
    if(disPlayedAuthor.length() == 0){
      return disPlayedAuthor;
    }
    disPlayedAuthor.setSpan(new ForegroundColorSpan(
        color != 0 ? color : defaultColor),
        AUTHOR_SEPARATOR.length(),
        disPlayedAuthor.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return disPlayedAuthor;
  }

  private SpannableString createAuthorSpannable(boolean authorLink){
    SpannableString bySpannable = new SpannableString(AUTHOR_SEPARATOR + by);
    if(!authorLink){
      return bySpannable;
    }
    bySpannable.setSpan(
        new StyleSpan(Typeface.BOLD),
        AUTHOR_SEPARATOR.length(),
        bySpannable.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    ClickableSpan clickableSpan = new ClickableSpan() {
      @Override public void onClick(View view) {
        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW).setData(AppUtils.createUserUri(getBy())));
      }

      @Override public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
      }
    };

    bySpannable.setSpan(clickableSpan,
        AUTHOR_SEPARATOR.length(),
        bySpannable.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return bySpannable;
  }

  @Override public Spannable getDisplayTime(Context context) {
    if(displayedTime == null){
      SpannableStringBuilder builder = new SpannableStringBuilder(
          isDead() ? context.getString(R.string.dead) + " " : "");
      SpannableString timeSpannable = new SpannableString(AppUtils.getAbbreviatedTimeSpan(getTime() * 1000));
      if(isDeleted()){
        timeSpannable.setSpan(new StrikethroughSpan(), 0, timeSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
      builder.append(timeSpannable);
      displayedTime = builder;
    }
    return displayedTime;
  }

  @NonNull
  @Override public String getType() {
    return (!TextUtils.isEmpty(type) ? type : STORY_TYPE);
  }

  @Override public void setFavorite(boolean favorite) {
    this.isFavorited = favorite;
  }

  @Override public boolean isFavorite() {
    return isFavorited;
  }

  @TargetApi(Build.VERSION_CODES.N)
  @Override public CharSequence getDisplayedText() {
    if(displayedText != null){
      displayedText = AppUtils.fromHtml(displayedText);
    }
    return displayedText;
  }

  public void preload(){
    getDisplayedText();
    getKidItems();
  }

  @Override public String toString() {
    return "[id: " + id + "]\n[type: " + type + "]\n[url: " + url + "]\n[by: " + by + "]";
  }

}
