package com.example.k13006kk.libraryprj_test;

/**
 * Created by k13006kk on 2016/02/27.
 */

import android.app.Service;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.k13006kk.mylibrary.BeaconApplication;
import com.example.k13006kk.mylibrary.DBaccess;
import com.example.k13006kk.mylibrary.UserColumns;

/*
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;
*/
import java.util.Timer;
import java.util.TimerTask;

public class Background  extends Service/* implements BootstrapNotifier */{

    public Context context;

    //public String url = "http://192.168.54.167:60000/beacon_load.php";//http://192.168.100.211/beacon_load.php
    public String url = "http://192.168.100.211/beacon_load.php";//http://192.168.100.211/beacon_load.php


    ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            // 変更された時の処理を書く
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        ContentResolver resolver = getContentResolver();

        this.context = new Background();

        BeaconApplication beaconApplication = new BeaconApplication();
        //DBaccess dBaccess = new DBaccess();

        //beaconApplication.setNotify();

        final BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);

        int scan1 = 3000;//基本は4.6秒間隔//2600
        int scan2 = 2000;//基本は0.4秒間スキャン//800

        beaconApplication.BeaconScan(resolver, bluetoothManager, scan1, scan2, url);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
