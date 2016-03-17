package com.example.k13006kk.libraryprj_test;

/**
 * Created by k13006kk on 2016/02/27.
 */

import android.app.Service;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.k13006kk.mylibrary.BeaconApplication;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Timer;
import java.util.TimerTask;

public class Background  extends Service/* implements BootstrapNotifier */{


    Context context;

    //BeaconManager beaconManager2;

    //RegionBootstrap regionBootstrap2;

    //Region region;

    BeaconApplication beaconApplication = new BeaconApplication();


    @Override
    public void onCreate() {
        super.onCreate();


        final BluetoothManager bluetoothManager2 = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);

        //beaconManager2 = BeaconManager.getInstanceForApplication(this);

        //region = new Region("uuid-region-bootstrap-001", null, null, null);

        //regionBootstrap2 = new RegionBootstrap(this, region);

        //beaconApplication.BeaconScan(context, beaconManager2, regionBootstrap2);

        beaconApplication.BeaconScan2(context, bluetoothManager2);

        /*
        TimerTask scan = new TimerTask() {
            public void run() {

                beaconApplication.BeaconScan2(context, bluetoothManager2);

            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(scan, 0, 4600);
        */

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
/*
    @Override
    public void didEnterRegion(Region region) {
        // 領域に入場した

        Log.d(TAG, "Enter Region");

        try {
            // レンジング開始
            beaconManager.startRangingBeaconsInRegion(region);
        } catch(RemoteException e) {
            // 例外が発生した場合
            e.printStackTrace();
        }

    }

    @Override
    public void didExitRegion(Region region) {
        // 領域から退場した

        Log.d(TAG, "Exit Region");

        try {
            // レンジング停止
            beaconManager.stopRangingBeaconsInRegion(region);
        } catch(RemoteException e) {
            // 例外が発生した場合
            e.printStackTrace();
        }

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {
        // 入退場状態が変更された

        Log.d(TAG, "Determine State: " + i);

    }

*/
}
