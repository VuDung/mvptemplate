package com.tnc.template.data.storage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class SessionManager {
  public SessionManager() {}

  public Observable<Boolean> check(Context context, String itemId) {
    if (TextUtils.isEmpty(itemId)) {
      return Observable.just(false);
    }
    Cursor cursor = context.getContentResolver().query(
        TemplateProvider.URI_VIEWED,
        null,
        TemplateProvider.ViewedEntry.COLUMN_ITEM_ID + " = ?",
        new String[] { String.valueOf(itemId) },
        null);
    boolean result = false;
    if (cursor != null) {
      result = cursor.getCount() > 0;
      cursor.close();
    }
    return Observable.just(result);
  }

  public void view(final Context context, final String itemId) {
    if (TextUtils.isEmpty(itemId)) {
      return;
    }
    Observable.defer(() -> Observable.just(itemId))
        .map((String id) -> insert(context.getContentResolver(), id))
        .map((String id) -> TemplateProvider.URI_VIEWED.buildUpon().appendPath(id).build())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((Uri uri) -> context.getContentResolver().notifyChange(uri, null));
  }

  private String insert(ContentResolver contentResolver, String itemId) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(TemplateProvider.ViewedEntry.COLUMN_ITEM_ID, itemId);
    contentResolver.insert(TemplateProvider.URI_VIEWED, contentValues);
    return itemId;
  }
}
