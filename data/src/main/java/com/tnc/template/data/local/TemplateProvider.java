package com.tnc.template.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by CUSDungVT on 1/11/2017.
 */

public class TemplateProvider extends ContentProvider{
  public static final String PROVIDER_AUTHORITY = "com.tnc.template.provider";
  private static final Uri URI_BASE = Uri.parse("content://" + PROVIDER_AUTHORITY);
  public static final Uri URI_FAVORITE = URI_BASE.buildUpon().appendPath(FavoriteEntry.TABLE_NAME).build();
  public static final Uri URI_VIEWED = URI_BASE.buildUpon().appendPath(ViewedEntry.TABLE_NAME).build();
  private DBHelper dbHelper;

  @Override public boolean onCreate() {
    dbHelper = new DBHelper(getContext());
    return true;
  }

  @Nullable @Override
  public Cursor query(Uri uri, String[] projections, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    if(URI_FAVORITE.equals(uri)){
      return db.query(FavoriteEntry.TABLE_NAME, projections, selection, selectionArgs, null, null, sortOrder);
    }
    if(URI_VIEWED.equals(uri)){
      return db.query(ViewedEntry.TABLE_NAME, projections, selection, selectionArgs, null, null, sortOrder);
    }
    return null;
  }

  @Nullable @Override public String getType(Uri uri) {
    if(URI_FAVORITE.equals(uri)){
      return FavoriteEntry.MIME_TYPE;
    }
    if(URI_VIEWED.equals(uri)){
      return ViewedEntry.MIME_TYPE;
    }
    return null;
  }

  @Nullable @Override public Uri insert(Uri uri, ContentValues contentValues) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    if(URI_FAVORITE.equals(uri)){
      int updated = db.update(FavoriteEntry.TABLE_NAME,
          contentValues,
          FavoriteEntry.COLUMN_ITEM_ID + " = ? ",
          new String[]{contentValues.getAsString(FavoriteEntry.COLUMN_ITEM_ID)});
      long id = -1;
      if(updated == 0){
        id = db.insert(FavoriteEntry.TABLE_NAME, null, contentValues);
      }
      return id == -1 ? null : ContentUris.withAppendedId(URI_FAVORITE, id);
    }

    if(URI_VIEWED.equals(uri)){
      int updated = db.update(ViewedEntry.TABLE_NAME,
          contentValues,
          ViewedEntry.COLUMN_ITEM_ID + " = ? ",
          new String[]{contentValues.getAsString(ViewedEntry.COLUMN_ITEM_ID)});
      long id = -1;
      if(updated == 0){
        id = db.insert(ViewedEntry.TABLE_NAME, null, contentValues);
      }
      return id == -1 ? null : ContentUris.withAppendedId(URI_VIEWED, id);
    }

    return null;
  }

  @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    String table = null;
    if(URI_FAVORITE.equals(uri)){
      table = FavoriteEntry.TABLE_NAME;
    }
    if(URI_VIEWED.equals(uri)){
      table = ViewedEntry.TABLE_NAME;
    }
    if(TextUtils.isEmpty(table)){
      return 0;
    }
    return db.delete(table, selection, selectionArgs);
  }

  @Override public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    String table = null;
    if(URI_FAVORITE.equals(uri)){
      table = FavoriteEntry.TABLE_NAME;
    }
    if(URI_VIEWED.equals(uri)){
      table = ViewedEntry.TABLE_NAME;
    }
    if(TextUtils.isEmpty(table)){
      return 0;
    }
    return db.update(table, contentValues, selection, selectionArgs);
  }

  interface FavoriteEntry extends BaseColumns{
    String TABLE_NAME = "favorite";
    String MIME_TYPE = "vn.android.cursor.dir/vn." + PROVIDER_AUTHORITY + "." + TABLE_NAME;
    String COLUMN_ITEM_ID = "item_id";
    String COLUMN_URL = "url";
    String COLUMN_TITLE = "title";
    String COLUMN_TIME = "time";
  }

  interface ViewedEntry extends BaseColumns{
    String TABLE_NAME = "viewed";
    String MIME_TYPE = "vn.android.cursor.dir/vn." + PROVIDER_AUTHORITY + "." + TABLE_NAME;
    String COLUMN_ITEM_ID = "item_id";
  }

  private static class DBHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "template_db";
    private static final int DB_VERSION = 1;

    private static final String TEXT_TYPE = " text";
    private static final String INTEGER_TYPE = " integer";
    private static final String PRIMARY_KEY = " primary key";
    private static final String COMMA_SEP = ",";
    private static final String ORDER_DESC = " desc";

    private static final String SQL_CREATE_FAVORITE_TABLE = "create table " + FavoriteEntry.TABLE_NAME + "(" +
        FavoriteEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
        FavoriteEntry.COLUMN_ITEM_ID + TEXT_TYPE + COMMA_SEP +
        FavoriteEntry.COLUMN_URL + TEXT_TYPE + COMMA_SEP +
        FavoriteEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
        FavoriteEntry.COLUMN_TIME + TEXT_TYPE + ")";
    private static final String SQL_CREATE_VIEWED_TABLE = "create table " + ViewedEntry.TABLE_NAME + "(" +
        ViewedEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
        ViewedEntry.COLUMN_ITEM_ID + TEXT_TYPE + ")";
    private static final String SQL_DROP_FAVORITE_TABLE = "drop table if exists " + FavoriteEntry.TABLE_NAME;
    private static final String SQL_DROP_VIEWED_TABLE = "drop table if exists " + ViewedEntry.TABLE_NAME;

    DBHelper(Context context){
      super(context, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
      sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
      sqLiteDatabase.execSQL(SQL_CREATE_VIEWED_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
      switch (oldVersion){
        default:
          sqLiteDatabase.execSQL(SQL_DROP_FAVORITE_TABLE);
          sqLiteDatabase.execSQL(SQL_DROP_VIEWED_TABLE);
          break;
      }
    }
  }
}
