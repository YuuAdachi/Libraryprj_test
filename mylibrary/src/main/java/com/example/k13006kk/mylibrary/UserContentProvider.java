package com.example.k13006kk.mylibrary;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

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
                queryBuilder.appendWhere(UserColumns._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        //通知機能
        //cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
    /*
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(UserColumns.TABLE);
        switch (sUriMatcher.match(uri)) {
            case USERS:
                //qb.setProjectionMap(personProjectionMap);
                break;
            case USER_ID:
                //qb.setProjectionMap(personProjectionMap);
                qb.appendWhere(UserColumns._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }*/

    /*
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //checkUri(uri);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query(uri.getPathSegments().get(0), projection, appendSelection(uri, selection), appendSelectionArgs(uri, selectionArgs), null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }*/


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
            //getContext().getContentResolver().notifyChange(returnUri, null);
            return returnUri;
        } else {
            throw new IllegalArgumentException("Failed to insert row into " + uri);
        }
    }

    // update実行

    /*
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;
        String id = uri.getPathSegments().get(0);
        count = db.update(UserColumns.TABLE, values, selection, selectionArgs);
        //final int count = db.update(uri.getPathSegments().get(0), values, appendSelection(uri, selection), appendSelectionArgs(uri, selectionArgs));
        // 通知機能
        getContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }*/
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case USERS:
                count = db.update(UserColumns.TABLE, values, selection,
                        selectionArgs);
                break;
            case USER_ID:
                String id = uri.getPathSegments().get(1);
                count = db.update(UserColumns.TABLE, values, UserColumns._ID
                                + "=" + id
                                + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                                + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    /*
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //isValidUri(uri);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        // URIからテーブル名を取得
        String tableName = uri.getPathSegments().get(0);
        int updatedCount = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updatedCount;
    }
    /*
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //checkUri(uri);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int count = db.update(uri.getPathSegments().get(0), values, appendSelection(uri, selection), appendSelectionArgs(uri, selectionArgs));
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }*/
    private String appendSelection(Uri uri, String selection) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 1) {
            return selection;
        }
        return UserColumns._ID + " = ?" + (selection == null ? "" : " AND (" + selection + ")");
    }
    private String[] appendSelectionArgs(Uri uri, String[] selectionArgs) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 1) {
            return selectionArgs;
        }
        if (selectionArgs == null || selectionArgs.length == 0) {
            return new String[] {pathSegments.get(1)};
        }
        String[] returnArgs = new String[selectionArgs.length + 1];
        returnArgs[0] = pathSegments.get(1);
        System.arraycopy(selectionArgs, 0, returnArgs, 1, selectionArgs.length);
        return returnArgs;
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
        //getContext().getContentResolver().notifyChange(uri, null);
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
