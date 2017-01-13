package com.tnc.template.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import com.tnc.template.data.annotation.WorkerThread;
import com.tnc.template.data.entity.Favorite;
import com.tnc.template.data.entity.WebItem;
import java.util.Collection;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class FavoriteManager implements LocalItemManager<Favorite>{
  private static final int LOADER_ID = 0;
  private static final String URI_PATH_ADD = "add";
  private static final String URI_PATH_REMOVE = "remove";
  private static final String URI_PATH_CLEAR = "clear";

  private CursorFavorite cursor;

  /**
   * static methods
   */
  public static boolean isAdded(Uri uri){
    return uri.toString().startsWith(buildAdd().toString());
  }
  public static boolean isRemoved(Uri uri){
    return uri.toString().startsWith(buildRemove().toString());
  }
  public static boolean isCleared(Uri uri){
    return uri.toString().startsWith(buildClear().toString());
  }
  private static Uri.Builder buildAdd(){
    return TemplateProvider.URI_FAVORITE.buildUpon().appendPath(URI_PATH_ADD);
  }
  private static Uri.Builder buildRemove(){
    return TemplateProvider.URI_FAVORITE.buildUpon().appendPath(URI_PATH_REMOVE);
  }
  private static Uri.Builder buildClear(){
    return TemplateProvider.URI_FAVORITE.buildUpon().appendPath(URI_PATH_CLEAR);
  }

  public void add(Context context, WebItem item){
    Observable.defer(()->Observable.just(item))
        .map(story -> {
          insert(context, story);
          return story.getItemId();
        })
        .map(itemId->buildAdd().appendPath(itemId).build())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(uri->context.getContentResolver().notifyChange(uri, null));
  }

  public void clear(Context context, String query){
    Observable.defer(()->Observable.just(query))
        .map(filter->deleteMultiple(context, filter))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(count->context.getContentResolver().notifyChange(buildClear().build(), null));
  }

  public void remove(Context context, String itemId){
    Observable.defer(()->Observable.just(itemId))
        .map(id->{
          delete(context, id);
          return id;
        })
        .map(id->buildRemove().appendPath(id).build())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(uri->context.getContentResolver().notifyChange(uri, null));
  }

  public void removeMultiple(Context context, Collection<String> ids){
    Observable.defer(()->Observable.from(ids))
        .subscribeOn(Schedulers.io())
        .map(itemId->{
          delete(context, itemId);
          return itemId;
        })
        .map(itemId->buildRemove().appendPath(itemId).build())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(uri->context.getContentResolver().notifyChange(uri, null));
  }

  @WorkerThread
  private Uri insert(Context context, WebItem item){
    ContentValues contentValues = new ContentValues();
    contentValues.put(TemplateProvider.FavoriteEntry.COLUMN_ITEM_ID, item.getItemId());
    contentValues.put(TemplateProvider.FavoriteEntry.COLUMN_URL, item.getUrl());
    contentValues.put(TemplateProvider.FavoriteEntry.COLUMN_TITLE, item.getDisplayedTitle());
    contentValues.put(TemplateProvider.FavoriteEntry.COLUMN_TIME,
        item instanceof Favorite ? String.valueOf(((Favorite)item).getTime()) : String.valueOf(System.currentTimeMillis())
        );
    return context.getContentResolver().insert(TemplateProvider.URI_FAVORITE, contentValues);
  }

  @WorkerThread
  public Observable<Boolean> check(Context context, String itemId){
    if (TextUtils.isEmpty(itemId)) {
      return Observable.just(false);
    }
    Cursor cursor = context.getContentResolver().query(
        TemplateProvider.URI_FAVORITE,
        null,
        TemplateProvider.FavoriteEntry.COLUMN_ITEM_ID + " = ?",
        new String[] { String.valueOf(itemId) },
        null);
    boolean result = false;
    if (cursor != null) {
      result = cursor.getCount() > 0;
      cursor.close();
    }
    return Observable.just(result);
  }

  @WorkerThread
  private Cursor query(Context context, String filter){
    String selection;
    String[] selectionArgs;
    if(TextUtils.isEmpty(filter)){
      selection = null;
      selectionArgs = null;
    }else{
      selection = TemplateProvider.FavoriteEntry.COLUMN_TITLE + " LIKE ?";
      selectionArgs = new String[]{"%" + filter + "%"};
    }
    return context.getContentResolver().query(TemplateProvider.URI_FAVORITE,
        null,
        selection,
        selectionArgs,
        null);
  }
  @WorkerThread
  private int delete(Context context, String itemId){
    return context.getContentResolver().delete(TemplateProvider.URI_FAVORITE,
        TemplateProvider.FavoriteEntry.COLUMN_ITEM_ID + " = ?",
        new String[]{itemId});
  }

  @WorkerThread
  private int deleteMultiple(Context context, String query){
    String selection;
    String[] selectionArgs;
    if(TextUtils.isEmpty(query)){
      selection = null;
      selectionArgs = null;
    }else{
      selection = TemplateProvider.FavoriteEntry.COLUMN_TITLE + " LIKE ?";
      selectionArgs = new String[]{"%" + query + "%"};
    }
    return context.getContentResolver().delete(TemplateProvider.URI_FAVORITE, selection, selectionArgs);
  }

  @Override public int getSize() {
    return cursor != null ? cursor.getCount() : 0;
  }

  @Override public Favorite getItem(int position) {
    return cursor.moveToPosition(position) ? cursor.getFavorite() : null;
  }

  @Override public void attach(Context context, LoaderManager loaderManager,
      Observer observer, String filter) {
    loaderManager.restartLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
      @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(TextUtils.isEmpty(filter)){
          return new CursorFavoriteLoader(context);
        }else {
          return new CursorFavoriteLoader(context, filter);
        }
      }

      @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null){
          data.setNotificationUri(context.getContentResolver(), TemplateProvider.URI_FAVORITE);
          cursor = new CursorFavorite(data);
        }else{
          cursor = null;
        }
        observer.onChanged();
      }

      @Override public void onLoaderReset(Loader<Cursor> loader) {
        cursor = null;
        observer.onChanged();
      }
    });
  }

  @Override public void detach() {
    if(cursor != null) cursor.close();
  }

  private static class CursorFavorite extends CursorWrapper{
    public CursorFavorite(Cursor cursor) {
      super(cursor);
    }

    Favorite getFavorite(){
      String itemId = getString(getColumnIndexOrThrow(TemplateProvider.FavoriteEntry.COLUMN_ITEM_ID));
      String url = getString(getColumnIndexOrThrow(TemplateProvider.FavoriteEntry.COLUMN_URL));
      String title = getString(getColumnIndexOrThrow(TemplateProvider.FavoriteEntry.COLUMN_TITLE));
      String time = getString(getColumnIndexOrThrow(TemplateProvider.FavoriteEntry.COLUMN_TIME));
      return new Favorite(itemId, url, title, Long.valueOf(time));
    }
  }

  private static class CursorFavoriteLoader extends CursorLoader{
    public CursorFavoriteLoader(Context context) {
      super(context, TemplateProvider.URI_FAVORITE, null, null, null, null);
    }

    CursorFavoriteLoader(Context context, String query) {
      super(context, TemplateProvider.URI_FAVORITE, null,
          TemplateProvider.FavoriteEntry.COLUMN_TITLE + " LIKE ?",
          new String[]{"%" + query + "%"}, null);
    }
  }
}
