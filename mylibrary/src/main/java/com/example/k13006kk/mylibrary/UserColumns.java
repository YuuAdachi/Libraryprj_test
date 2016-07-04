package com.example.k13006kk.mylibrary;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by k13006kk on 2016/04/21.
 */
public class UserColumns implements BaseColumns {

    // URIパス
    public static final String PATH = "user";
    // コンテントURI
    public static final Uri CONTENT_URI = Uri.parse("content://" + UserContentProvider.AUTHORITY + "/user");
    // テーブル指定コンテントタイプ
    public static final String CONTENT_TYPE = "vnd.android.cursor.item/vnd.example.users";
    // レコード個別指定コンテントタイプ
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.dir/vnd.example.users";

    // テーブル名
    public static final String TABLE = "userdata";
    // カラム 名前
    //public static final String NAME = "name";
    // カラム 部屋
    public static final String ROOM = "room";
    // カラム 日時
    public static final String DATETIME = "datetime";

    // コンストラクタ(呼ばれることは無い)
    private UserColumns() {}
}
