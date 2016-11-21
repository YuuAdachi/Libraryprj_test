package com.example.k13006kk.libraryprj_test;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.k13006kk.mylibrary.BeaconHolder;
import com.example.k13006kk.mylibrary.DBaccess;
import com.example.k13006kk.mylibrary.UserColumns;

public class MainActivity extends Activity {


    /*
    データホルダーからビーコンIDを受け取る
    通信準備
    （URL、パラメータ等を指定する）
    通信開始
    （取得した情報を画面に表示する）
     */

    static BeaconHolder beaconinfo = BeaconHolder.getInstance();
    DBaccess dBaccess = new DBaccess();
    public String[] stringArray = {" "," "," "," "};
    public String[] backup = {" "," "," "," "};
    public String[] room = {" "," "," "," "," "," "};
    public String[] enterroom = new String[6];
    public String[] exitroom = new String[6];

    //public String[] exitroom = {" "," "," "," "," "," "};

    Handler _handler = new Handler();


    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12;

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

                _handler.postDelayed(this, 100);

            }
        }, 0);

        final ContentResolver resolver = getContentResolver();
        for (int i = 0; i < dBaccess.getenterroom(resolver).length; i++) {
            enterroom[i] = dBaccess.getenterroom(resolver)[i];
        }
        for (int i = 0; i < dBaccess.getexitroom(resolver).length; i++) {
            exitroom[i] = dBaccess.getexitroom(resolver)[i];
        }
            /*
            for (int i = 0; i < dBaccess.monitoring(resolver).length; i++) {
                exitroom[i] = dBaccess.monitoring(resolver)[i];
            }*/

        tv5 = (TextView) findViewById(R.id.toumei);
        tv5.setText(enterroom[1]);

        tv6 = (TextView) findViewById(R.id.heyamei);
        tv6.setText(enterroom[2]);

        tv7 = (TextView) findViewById(R.id.heyaban);
        tv7.setText(enterroom[3]);

        tv8 = (TextView) findViewById(R.id.nichiji);
        tv8.setText(enterroom[4]);

        tv9 = (TextView) findViewById(R.id.toumei2);
        tv9.setText(exitroom[1]);

        tv10 = (TextView) findViewById(R.id.heyamei2);
        tv10.setText(exitroom[2]);

        tv11 = (TextView) findViewById(R.id.heyaban2);
        tv11.setText(exitroom[3]);

        tv12 = (TextView) findViewById(R.id.nichiji2);
        tv12.setText(exitroom[4]);
    }

    ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            // 変更された時の処理を書く
            final ContentResolver resolver = getContentResolver();
            for (int i = 0; i < dBaccess.getenterroom(resolver).length; i++) {
                enterroom[i] = dBaccess.getenterroom(resolver)[i];
            }
            for (int i = 0; i < dBaccess.getexitroom(resolver).length; i++) {
                exitroom[i] = dBaccess.getexitroom(resolver)[i];
            }
            /*
            for (int i = 0; i < dBaccess.monitoring(resolver).length; i++) {
                exitroom[i] = dBaccess.monitoring(resolver)[i];
            }*/

            tv5 = (TextView) findViewById(R.id.toumei);
            tv5.setText(enterroom[1]);

            tv6 = (TextView) findViewById(R.id.heyamei);
            tv6.setText(enterroom[2]);

            tv7 = (TextView) findViewById(R.id.heyaban);
            tv7.setText(enterroom[3]);

            tv8 = (TextView) findViewById(R.id.nichiji);
            tv8.setText(enterroom[4]);

            tv9 = (TextView) findViewById(R.id.toumei2);
            tv9.setText(exitroom[1]);

            tv10 = (TextView) findViewById(R.id.heyamei2);
            tv10.setText(exitroom[2]);

            tv11 = (TextView) findViewById(R.id.heyaban2);
            tv11.setText(exitroom[3]);

            tv12 = (TextView) findViewById(R.id.nichiji2);
            tv12.setText(exitroom[4]);
        }
    };

    public void beaconinfoView(){

        for (int i = 0; i < beaconinfo.getTestString().length; i++) {
            stringArray[i] = beaconinfo.getTestString()[i];
        }
        //stringArray = beaconinfo.getTestString();
        //Log.d("scan2",stringArray[0]+","+stringArray[1]+","+stringArray[2]+","+stringArray[3]);
        //Log.d("scan2",backup[0]+","+backup[1]+","+backup[2]+","+backup[3]);

        if(!"A".equals(stringArray[0])) {
            tv1 = (TextView) findViewById(R.id.uuid);
            tv1.setText(stringArray[0]);

            tv2 = (TextView) findViewById(R.id.major);
            tv2.setText(stringArray[1]);

            tv3 = (TextView) findViewById(R.id.minor);
            tv3.setText(stringArray[2]);

            tv4 = (TextView) findViewById(R.id.rssi);
            tv4.setText(stringArray[3]);

            for (int i = 0; i < stringArray.length; i++) {
                backup[i] = stringArray[i];
            }

        }else if("A".equals(stringArray[0])){
            tv1 = (TextView) findViewById(R.id.uuid);
            tv1.setText(backup[0]);

            tv2 = (TextView) findViewById(R.id.major);
            tv2.setText(backup[1]);

            tv3 = (TextView) findViewById(R.id.minor);
            tv3.setText(backup[2]);

            tv4 = (TextView) findViewById(R.id.rssi);
            tv4.setText(backup[3]);
        }

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