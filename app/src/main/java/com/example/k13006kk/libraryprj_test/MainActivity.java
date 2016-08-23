package com.example.k13006kk.libraryprj_test;

import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.k13006kk.mylibrary.BeaconinfoHolder;
import com.example.k13006kk.mylibrary.UserColumns;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {


    /*
    データホルダーからビーコンIDを受け取る
    通信準備
    （URL、パラメータ等を指定する）
    通信開始
    （取得した情報を画面に表示する）
     */

    BeaconinfoHolder beaconinfo = new BeaconinfoHolder();
    String[] stringArray;
    Handler _handler = new Handler();

    TextView tv1, tv2, tv3, tv4, tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //バックグラウンド処理のためのServiceの開始
        startService(new Intent(MainActivity.this, Background.class));

        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                beaconinfoView();

                _handler.postDelayed(this, 500);

            }
        }, 0);

    }

    ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            // 変更された時の処理を書く
            tv5 = (TextView) findViewById(R.id.state);
            tv5.setText("入室　");
            tv5 = (TextView) findViewById(R.id.state);
            // オレンジ色
            tv5.setTextColor(0xffff8c00);
        }
    };

    public void beaconinfoView(){

            stringArray = beaconinfo.getData();

            tv1 = (TextView) findViewById(R.id.uuid);
            tv1.setText(stringArray[0]);

            tv2 = (TextView) findViewById(R.id.major);
            tv2.setText(stringArray[1]);

            tv3 = (TextView) findViewById(R.id.minor);
            tv3.setText(stringArray[2]);

            tv4 = (TextView) findViewById(R.id.rssi);
            tv4.setText(stringArray[3]);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(UserColumns.CONTENT_URI,true,mContentObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mContentObserver);
    }

}