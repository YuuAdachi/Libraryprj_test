package com.example.k13006kk.libraryprj_test;

/**
 * Created by k13006kk on 2016/02/27.
 */

import android.app.Service;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.k13006kk.mylibrary.BeaconApplication;
import com.example.k13006kk.mylibrary.DBaccess;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Timer;
import java.util.TimerTask;

public class Background  extends Service/* implements BootstrapNotifier */{


    //Context context;

    BeaconManager beaconManager;

    //RegionBootstrap regionBootstrap2;

    //Region region;

    //BeaconApplication beaconApplication = new BeaconApplication();

    String url = "http://192.168.100.211/beacon_load.php";



    @Override
    public void onCreate() {
        super.onCreate();

        ContentResolver resolver = getContentResolver();


        //this.context = new Background();

        BeaconApplication beaconApplication = new BeaconApplication();
        DBaccess dBaccess = new DBaccess();

        //beaconApplication.setNotify();

        final BluetoothManager bluetoothManager2 = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);

        int scan1 = 2600;//基本は4.6秒間隔
        int scan2 = 800;//基本は0.4秒間スキャン

        beaconApplication.BeaconScan(resolver, bluetoothManager2, scan1,scan2,url);
        //dBaccess.monitoring(resolver);

        //beaconManager = BeaconManager.getInstanceForApplication(this);

        //beaconApplication.Beaconscanalt2(context,url);

        //beaconApplication.Beaconscanalt1(context);
        //beaconApplication.onBeaconServiceConnect();

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
