package com.example.k13006kk.mylibrary;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by k13006kk on 2016/04/21.
 */
public class DBaccess extends AppCompatActivity {

    Uri uri;
    ContentResolver contentResolver;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public void dbaccess(ContentResolver resolver, String[] roomtest){

        // テーブルにデータ投入.
        uri = ContentUris.withAppendedId(UserColumns.CONTENT_URI, 1);
        ContentValues values = new ContentValues();
        values.clear();

        // nameはいらないかも
        //values.put(UserColumns.NAME, "name");
        values.put(UserColumns.BUILDING_NAME, roomtest[0]);
        values.put(UserColumns.ROOM_NAME, roomtest[1]);
        values.put(UserColumns.ROOM_NUMBER, roomtest[2]);
        values.put(UserColumns.DATETIME, getNowDate());
        resolver.update(uri, values, null, null);

    }

    //現在時刻を取得する関数（DBに送るため）
    public static String getNowDate(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    // データベースを読む側で使うメソッド
    public String[] monitoring(ContentResolver resolver) {
        contentResolver = resolver;
        //resolver.registerContentObserver(UserColumns.CONTENT_URI, true, mContentObserver);

        // テーブルのデータを全件検索. 表示.
        //final Cursor c = resolver.query(UserColumns.CONTENT_URI, null, null, null, null);

        String[] dbstr = new String[6];

        Uri uri = UserColumns.CONTENT_URI;
        Cursor c = resolver.query(uri, null, null, null, null);
        while (c.moveToNext()) {
            //Log.d(TAG, "call:" + cursor.getString(cursor.getColumnIndexOrThrow("title")));
            for (int i = 0; i < c.getColumnCount(); i++) {
                dbstr[i] = c.getString(i);
            }
        }
        // 処理が完了したらCursorを閉じます
        c.close();

        return dbstr;

    }


    public void changeact(){
        // DBの情報を取ってきてHolderにストックしておく
        uri = UserColumns.CONTENT_URI;
        // 現状ではID1のデータのみ取ってきている（DBには常に一つしかないため）
        uri = ContentUris.withAppendedId(UserColumns.CONTENT_URI, 1);

        String[] dbstr = new String[5];

        // 取ってきたDB情報をそのままCursor型で保存
        //DbinfoHolder holder = DbinfoHolder.getInstance();
        Cursor c = contentResolver.query(uri, null, null, null, null);
        //holder.setdb(c);
        //c.close();

        //Cursor c = managedQuery(uri, null, null, null, null);


        while (c.moveToNext()) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                dbstr[i] = c.getString(i);
            }
        }
        c.close();
        DbinfoHolder holder = DbinfoHolder.getInstance();
        holder.setdb(dbstr);
        //Log.d("DBupdata", dbstr[1] + " , " + dbstr[2] + " , " + dbstr[3]);
    }

    /*
    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mContentObserver);
    }*/

}
