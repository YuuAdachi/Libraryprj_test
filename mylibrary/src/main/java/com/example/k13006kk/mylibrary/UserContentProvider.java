package com.example.k13006kk.mylibrary;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by k13006kk on 2016/04/21.
 */
public class UserContentProvider extends ContentProvider {

    // Authority
    public static final String AUTHORITY = "com.example.k13006kk.mylibrary";

    // USERS テーブル URI ID
    private static final int USERS = 1;
    // USERS テーブル 個別 URI ID
    private static final int USER_ID = 2;

    // 利用者がメソッドを呼び出したURIに対応する処理を判定処理に使用します
    private static UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, UserColumns.PATH, USERS);
        sUriMatcher.addURI(AUTHORITY, UserColumns.PATH + "/#", USER_ID);
    }

    // DBHelperのインスタンス
    private UserDBHelper mDBHelper;

    // コンテンツプロバイダの作成
    @Override
    public boolean onCreate() {
        mDBHelper = new UserDBHelper(getContext());
        return true;
    }

    // query実行
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case USERS:
            case USER_ID:
                queryBuilder.setTables(UserColumns.TABLE);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        //通知機能
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // insert実行
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String insertTable;
        Uri contentUri;
        switch (sUriMatcher.match(uri)) {
            case USERS:
                insertTable = UserColumns.TABLE;
                contentUri = UserColumns.CONTENT_URI;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        //通知機能
        //getContext().getContentResolver().notifyChange(uri, null);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long rowId = db.insert(insertTable, null, values);
        if (rowId > 0) {
            Uri returnUri = ContentUris.withAppendedId(contentUri, rowId);
            getContext().getContentResolver().notifyChange(returnUri, null);
            return returnUri;
        } else {
            throw new IllegalArgumentException("Failed to insert row into " + uri);
        }
    }

    // update実行
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;
        String id = uri.getPathSegments().get(1);
        count = db.update(UserColumns.TABLE, values, selection, selectionArgs);
        // 通知機能
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // delete実行
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case USERS:
            case USER_ID:
                count = db.delete(UserColumns.TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // コンテントタイプ取得
    @Override
    public String getType(Uri uri) {
        switch(sUriMatcher.match(uri)) {
            case USERS:
                return UserColumns.CONTENT_TYPE;
            case USER_ID:
                return UserColumns.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }
}
