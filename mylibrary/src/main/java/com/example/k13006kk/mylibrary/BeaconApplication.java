package com.example.k13006kk.mylibrary;

/**
 * Created by k13006kk on 2016/02/22.
 */

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeaconApplication /*extends Service implements BootstrapNotifier */{

    /*
    String room_uuid;

    public static final String TAG = org.altbeacon.beacon.service.BeaconService.class.getSimpleName();

    // iBeaconのデータを認識するためのParserフォーマット
    public static final String IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

    private RegionBootstrap regionBootstrap;

    private BeaconManager beaconManager;

    // iBeacon領域
    private Region region;
    */

    Context context;

    /*

    //@Override
    public void BeaconScan(Context context, BeaconManager beaconManager2, RegionBootstrap regionBootstrap2) {
        //super.onCreate();
        //String room_uuid;

        //final String TAG = org.altbeacon.beacon.service.BeaconService.class.getSimpleName();

        // iBeaconのデータを認識するためのParserフォーマット
        final String IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

        RegionBootstrap regionBootstrap;

        BeaconManager beaconManager;

        // iBeacon領域
        //Region region;

        this.context = context;

        // iBeaconのデータを受信できるようにParserを設定
        beaconManager = beaconManager2;
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_FORMAT));
        // Beaconをスキャンする間隔
        beaconManager.setBackgroundBetweenScanPeriod(1000);

        // UUID, major, minorの指定はしない
        //region = new Region("uuid-region-bootstrap-001", null, null, null);
        regionBootstrap = regionBootstrap2;


        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                String room_uuid;
                // 検出したビーコンの情報を全部Logに書き出す
                for(Beacon beacon : beacons) {

                    DataHolder holder = DataHolder.getInstance();
                    room_uuid = beacon.getId1().toString();
                    holder.setTestString(room_uuid);

                    //Log.d("BeaconApplication", "UUID:" + beacon.getId1() + ", major:" + beacon.getId2() + ", minor:" + beacon.getId3() + ", Distance:" + beacon.getDistance() + ",RSSI" + beacon.getRssi() + ", TxPower" + beacon.getTxPower());

                    //BeaconScan2(room_uuid);
                }
            }
        });

    }*/

    public void BeaconScan2(String room_uuid){

        DataHolder holder = DataHolder.getInstance();
        holder.setTestString(room_uuid);

    }



    public void BeaconScan3(Context context, BluetoothManager bluetoothManager2) {

        this.context = context;

        final BluetoothManager bluetoothManager = bluetoothManager2;
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        BluetoothLeScanner mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        // (何もフィルタリングしない) スキャンフィルタの作成
        List<ScanFilter> mScanFilters = new ArrayList<ScanFilter>();
        // スキャンモードの作成
        ScanSettings.Builder mScanSettingBuiler = new ScanSettings.Builder();
        mScanSettingBuiler.setScanMode(android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_POWER);
        ScanSettings mScanSettings = mScanSettingBuiler.build();

        ScanCallback mScanCallback = new ScanCallback() {

            public void onBatchScanResults(List<ScanResult> results) {
            }

            public void onScanFailed(int errorCode) {
            }

            // 通常このメソッドがコールバックされます
            public void onScanResult(int callbackType, ScanResult result) {

                byte[] scanRecord = result.getScanRecord().getManufacturerSpecificData(76);
                if (scanRecord == null){
                    return;
                }
/*
                String msg = "";

                for (int i = 2; i < scanRecord.length; i++) {
                    msg += Byte.toString(scanRecord[i]) + "";
                }*/

                String uuid = IntToHex2(scanRecord[2] & 0xff)
                        + IntToHex2(scanRecord[3] & 0xff)
                        + IntToHex2(scanRecord[4] & 0xff)
                        + IntToHex2(scanRecord[5] & 0xff)
                        + "-"
                        + IntToHex2(scanRecord[6] & 0xff)
                        + IntToHex2(scanRecord[7] & 0xff)
                        + "-"
                        + IntToHex2(scanRecord[8] & 0xff)
                        + IntToHex2(scanRecord[9] & 0xff)
                        + "-"
                        + IntToHex2(scanRecord[10] & 0xff)
                        + IntToHex2(scanRecord[11] & 0xff)
                        + "-"
                        + IntToHex2(scanRecord[12] & 0xff)
                        + IntToHex2(scanRecord[13] & 0xff)
                        + IntToHex2(scanRecord[14] & 0xff)
                        + IntToHex2(scanRecord[15] & 0xff)
                        + IntToHex2(scanRecord[16] & 0xff)
                        + IntToHex2(scanRecord[17] & 0xff);

                String major = IntToHex2(scanRecord[18] & 0xff) + IntToHex2(scanRecord[19] & 0xff);
                String minor = IntToHex2(scanRecord[20] & 0xff) + IntToHex2(scanRecord[21] & 0xff);

                String scan_rssi = IntToHex2(scanRecord[22] & 0xff);

                DataHolder holder = DataHolder.getInstance();
                String room_uuid = uuid;
                holder.setTestString(room_uuid);

                //Log.d("ibeacon msg", msg);

                //Log.d("Beacon", "UUID:" + uuid);
                Log.d("Beacon", "UUID:" + uuid + ", major:" + major + ", minor:" + minor + ", RSSI:" + scan_rssi);

            }

                //intデータを 2桁16進数に変換するメソッド

            public String IntToHex2(int i) {
                char hex_2[] = {Character.forDigit((i >> 4) & 0x0f, 16), Character.forDigit(i & 0x0f, 16)};
                String hex_2_str = new String(hex_2);
                return hex_2_str.toUpperCase();
            }


        };

        mBluetoothLeScanner.startScan(mScanFilters, mScanSettings, mScanCallback);

        /*
        BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

                //ここに結果に対して行う処理を記述する

                if(scanRecord.length > 30)
                {
                    //iBeacon の場合 6 byte 目から、 9 byte 目はこの値に固定されている。
                    if((scanRecord[5] == (byte)0x4c) && (scanRecord[6] == (byte)0x00) &&
                            (scanRecord[7] == (byte)0x02) && (scanRecord[8] == (byte)0x15))
                    {
                        String uuid = IntToHex2(scanRecord[9] & 0xff)
                                + IntToHex2(scanRecord[10] & 0xff)
                                + IntToHex2(scanRecord[11] & 0xff)
                                + IntToHex2(scanRecord[12] & 0xff)
                                + "-"
                                + IntToHex2(scanRecord[13] & 0xff)
                                + IntToHex2(scanRecord[14] & 0xff)
                                + "-"
                                + IntToHex2(scanRecord[15] & 0xff)
                                + IntToHex2(scanRecord[16] & 0xff)
                                + "-"
                                + IntToHex2(scanRecord[17] & 0xff)
                                + IntToHex2(scanRecord[18] & 0xff)
                                + "-"
                                + IntToHex2(scanRecord[19] & 0xff)
                                + IntToHex2(scanRecord[20] & 0xff)
                                + IntToHex2(scanRecord[21] & 0xff)
                                + IntToHex2(scanRecord[22] & 0xff)
                                + IntToHex2(scanRecord[23] & 0xff)
                                + IntToHex2(scanRecord[24] & 0xff);

                        String major = IntToHex2(scanRecord[25] & 0xff) + IntToHex2(scanRecord[26] & 0xff);
                        String minor = IntToHex2(scanRecord[27] & 0xff) + IntToHex2(scanRecord[28] & 0xff);

                        String scan_rssi = IntToHex2(scanRecord[29] & 0xff);

                        DataHolder holder = DataHolder.getInstance();
                        String room_uuid = uuid;
                        holder.setTestString(room_uuid);

                        Log.d("Beacon",  "UUID:" + uuid + ", major:" + major + ", minor:" + minor + ",RSSI" + scan_rssi);
                    }
                }

            }

            //intデータを 2桁16進数に変換するメソッド
            public String IntToHex2(int i) {
                char hex_2[] = {Character.forDigit((i>>4) & 0x0f,16),Character.forDigit(i&0x0f, 16)};
                String hex_2_str = new String(hex_2);
                return hex_2_str.toUpperCase();
            }

        };*/

        //mBluetoothAdapter.startLeScan(mLeScanCallback);


    }


/*
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


    @Override
    public void didEnterRegion(Region region) {
        // 領域に入場した
        //Log.d(TAG, "Enter Region");


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
        //Log.d(TAG, "Exit Region");


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
        //Log.d(TAG, "Determine State: " + i);
    }
*/
}
