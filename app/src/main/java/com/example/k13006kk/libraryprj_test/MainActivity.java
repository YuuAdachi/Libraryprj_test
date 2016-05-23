package com.example.k13006kk.libraryprj_test;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.k13006kk.mylibrary.BeaconinfoHolder;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


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

    public void beaconinfoView(){

            stringArray = beaconinfo.getData();

            TextView tv = (TextView) findViewById(R.id.uuid);
            tv.setText(stringArray[0]);

            TextView tv2 = (TextView) findViewById(R.id.major);
            tv2.setText(stringArray[1]);

            TextView tv3 = (TextView) findViewById(R.id.minor);
            tv3.setText(stringArray[2]);

            TextView tv4 = (TextView) findViewById(R.id.rssi);
            tv4.setText(stringArray[3]);

    }

    public void onClick1(View view){

        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                beaconinfoView();

                _handler.postDelayed(this, 0);

            }
        }, 0);

        //String[] stringArray;

        //BeaconinfoHolder beaconinfo = new BeaconinfoHolder();


        /*
        mainActivity main = new mainActivity();

        BeaconinfoHolder database = BeaconinfoHolder.getinstance();
        */


        //main.data_com(url);


        /*
        stringArray = beaconinfo.getData();

        TextView tv = (TextView) findViewById(R.id.uuid);
        tv.setText(stringArray[7]);

        TextView tv2 = (TextView) findViewById(R.id.major);
        tv2.setText(stringArray[8]);

        TextView tv3 = (TextView) findViewById(R.id.minor);
        tv3.setText(stringArray[9]);

        TextView tv4 = (TextView) findViewById(R.id.rssi);
        tv4.setText(stringArray[10]);

        /*
        System.out.println(stringArray[0]);
        System.out.println(stringArray[1]);
        System.out.println(stringArray[2]);
        System.out.println(stringArray[3]);
        System.out.println(stringArray[4]);
        System.out.println(stringArray[5]);
        System.out.println(stringArray[6]);
        */

        //Log.d("Server",stringArray[0] + "," + stringArray[1] + "," + stringArray[2] + "," + stringArray[3] + "," + stringArray[4] + "," + stringArray[5] + "," + stringArray[6]);
        //Log.d("Server2",stringArray[6]);

    }

    public void onClick2(View view){
        _handler.removeCallbacksAndMessages(null);
    }



}