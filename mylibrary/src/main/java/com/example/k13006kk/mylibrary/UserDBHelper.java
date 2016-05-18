package com.example.k13006kk.mylibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by k13006kk on 2016/04/21.
 */
public class UserDBHelper extends SQLiteOpenHelper {

    // DB名とバージョン
    private static final String DB_NAME = "userdatabase";
    private static final int DB_VERSION = 1;

    // コンストラクタ
    public UserDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // データベースのテーブルを作成する
        sqLiteDatabase.execSQL("CREATE TABLE " + UserColumns.TABLE + " ("
                + UserColumns._ID + " INTEGER PRIMARY KEY,"
                + UserColumns.NAME + " TEXT,"
                + UserColumns.ROOM + " TEXT,"
                + UserColumns.DATETIME + " TEXT"
                + ");");
        // table row insert
        sqLiteDatabase.execSQL("insert into " + UserColumns.TABLE + "("
                + UserColumns.NAME + ","
                + UserColumns.ROOM + ","
                + UserColumns.DATETIME + ") values ('NULL_NAME', 'NULL_ROOM', 'NULL_TIME');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // DBに変更があったときに呼ばれる
    }
}
