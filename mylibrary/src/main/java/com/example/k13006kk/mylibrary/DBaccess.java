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

    //Uri uri;
    ContentResolver contentResolver;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    // 入室判定されたときにDBに書き込む
    public void dbEnter(ContentResolver resolver, String[] roominfo){

        // テーブルにデータ投入.
        Uri uri = ContentUris.withAppendedId(UserColumns.CONTENT_URI, 1);
        ContentValues values = new ContentValues();
        values.clear();

        values.put(UserColumns.BUILDING_NAME, roominfo[0]);
        values.put(UserColumns.ROOM_NAME, roominfo[1]);
        values.put(UserColumns.ROOM_NUMBER, roominfo[2]);
        values.put(UserColumns.DATETIME, getNowDate());
        resolver.update(uri, values, null, null);
        uri = null;
    }

    // 退室判定されたときにDBに書き込む
    public void dbExit(ContentResolver resolver, String[] roominfo){

        // テーブルにデータ投入.
        Uri uri = ContentUris.withAppendedId(UserColumns.CONTENT_URI, 2);
        ContentValues values = new ContentValues();
        values.clear();

        values.put(UserColumns.BUILDING_NAME, roominfo[0]);
        values.put(UserColumns.ROOM_NAME, roominfo[1]);
        values.put(UserColumns.ROOM_NUMBER, roominfo[2]);
        values.put(UserColumns.DATETIME, getNowDate());
        resolver.update(uri, values, null, null);
        uri = null;
        //Log.d("exit", roominfo[0] + " " + roominfo[1]);
    }

    //現在時刻を取得する関数（DBに送るため）
    public static String getNowDate(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    // データベースを読む側で使うメソッド
    public String[] getenterroom(ContentResolver resolver) {
        contentResolver = resolver;
        //resolver.registerContentObserver(UserColumns.CONTENT_URI, true, mContentObserver);

        // テーブルのデータを全件検索. 表示.
        //final Cursor c = resolver.query(UserColumns.CONTENT_URI, null, null, null, null);

        String[] dbstr = new String[6];
        String[] dbstr2 = new String[6];

        //Uri uri = UserColumns.CONTENT_URI;

        Uri uri = ContentUris.withAppendedId(UserColumns.CONTENT_URI, 1);

        //Cursor c = managedQuery(uri, null, null, null, null);

        Cursor c = resolver.query(uri, null, null, null, null);
        c.moveToFirst();

        //while (c.moveToNext()) {
            //Log.d(TAG, "call:" + cursor.getString(cursor.getColumnIndexOrThrow("title")));
            for (int i = 0; i < c.getColumnCount(); i++) {
                //if (i < 5){
                    dbstr[i] = c.getString(i);
                //}
            }
        //}
        /*
        //（.moveToFirstの部分はとばして）for文を回す
        for (int i = 0; i <= c.getColumnCount(); i++) {
            //SQL文の結果から、必要な値を取り出す

            if (i < 5){
                dbstr[i] = c.getString(i);
            }
            /*else {
                dbstr2[i] = c.getString(i);
            }
            //dbstr[i] = c.getString(i);
            c.moveToNext();
        }*/
        // 処理が完了したらCursorを閉じます
        c.close();
        return dbstr;
    }
    public String[] getexitroom(ContentResolver resolver) {
        contentResolver = resolver;
        //resolver.registerContentObserver(UserColumns.CONTENT_URI, true, mContentObserver);

        // テーブルのデータを全件検索. 表示.
        //final Cursor c = resolver.query(UserColumns.CONTENT_URI, null, null, null, null);

        String[] dbstr = new String[6];
        String[] dbstr2 = new String[6];

        //Uri uri = UserColumns.CONTENT_URI;

        Uri uri = ContentUris.withAppendedId(UserColumns.CONTENT_URI, 2);

        //Cursor c = managedQuery(uri, null, null, null, null);

        Cursor c = resolver.query(uri, null, null, null, null);
        c.moveToFirst();

        //while (c.moveToNext()) {
            //Log.d(TAG, "call:" + cursor.getString(cursor.getColumnIndexOrThrow("title")));
            for (int i = 0; i < c.getColumnCount(); i++) {
                /*
                if (i > 4){
                    dbstr[i] = c.getString(i);
                }*/
                dbstr[i] = c.getString(i);
            }
        //}

        /*
        //（.moveToFirstの部分はとばして）for文を回す
        for (int i = 1; i <= c.getColumnCount(); i++) {
            //SQL文の結果から、必要な値を取り出す
            if (i == 1){
                dbstr2[i] = c.getString(i);
            }else {
                dbstr[i] = c.getString(i);
            }
            dbstr[i] = c.getString(i);
            c.moveToNext();
        }*/

        // 処理が完了したらCursorを閉じます
        c.close();
        return dbstr;
    }

    /*
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
