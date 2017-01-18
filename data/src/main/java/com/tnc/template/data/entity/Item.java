package com.tnc.template.data.entity;

import android.content.Context;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public interface Item extends WebItem{

  void populate(Item info);

  /**
   * Get raw type, used to be parsed by {@link #getType()}
   * @return string type or null
   * @see Type
   */
  String getRawType();

  /**
   * Get raw url
   * @return string url or null
   * @see #getUrl()
   */
  String getRawUrl();

  /**
   * Get array kids by IDs
   * @return array of kids or null
   * @see #getKidCount()
   * @see #getKidItems()
   */
  long[] getKids();

  /**
   * Get number of kids, contained in {@link #getKids()}
   * @return number of kids
   * @see #getKids()
   * @see #getKidItems()
   */
  int getKidCount();

  /**
   * Get array of kids, with corresponding IDs in {@link #getKids()} ()}
   * @return array of kids or null
   * @see #getKids()
   * @see #getKidCount()
   */
  Item[] getKidItems();

  /**
   * Get author name
   * @return Author name or null
   * @see #getDisplayAuthor(Context, boolean, int)
   */
  String getBy();

  /**
   * Get posted time
   * @return posted time by timemiliseconds
   * @see #getDisplayAuthor(Context, boolean, int)
   */
  long getTime();

  /**
   * Get title
   * @return title or null
   * @see #getDisplayedTitle()
   */
  String getTitle();

  /**
   * Get item text
   * @return item text or null
   * @see #getDisplayedTitle()
   */
  String getText();

  /**
   * Get Item's current revision. A revision can be used to determined if item state is stale
   * and needs updated
   * @return #setLocalRevision(int)
   * @see #populate(Item)
   * @see #setFavorite(boolean)
   */
  int getLocalRevision();

  /**
   * Update Item's current revision to new one
   * @param revision
   * @see #getLocalRevision()
   */
  void setLocalRevision(int revision);

  /**
   * Indicated if this item has been viewed
   * @return true if viewed, false if not, null if unknown
   */
  boolean isViewed();

  /**
   * Set Item view status
   * @param viewed true if has been viewed, false if otherwise
   */
  void setViewed(boolean viewed);

  /**
   * Get Item's score
   * @return item's score
   */
  int getScore();

  /**
   * Check if item is dead
   * @return true if dead, false if otherwise
   */
  boolean isDead();

  boolean isDeleted();

  /**
   * Get item's rank among it's siblings
   * @return item's rank
   */
  int getRank();

  int getDescendants();

  CharSequence getDisplayedText();
}
