package com.example.k13006kk.libraryprj_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.k13006kk.mylibrary.mainActivity;

public class MainActivity extends AppCompatActivity {


    /*
    データホルダーからビーコンIDを受け取る
    通信準備
    （URL、パラメータ等を指定する）
    通信開始
    （取得した情報を画面に表示する）
     */

    String url = "http://192.168.100.211/beacon_attendance.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //バックグラウンド処理のためのServiceの開始
        startService(new Intent(MainActivity.this, Background.class));

    }

    public void onClick(View view){
        mainActivity main = new mainActivity();

        String uuid = main.data_com(url);

        TextView tv = (TextView) findViewById(R.id.uuid);
        tv.setText(uuid);
    }



}
