package com.example.k13006kk.mylibrary;

/**
 * Created by k13006kk on 2016/02/22.
 */

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BeaconApplication{

    //private ScanNotify sn = null;

    String allbeaconinfo1 = "a";
    String allbeaconinfo2 = "b";

    BeaconManager beaconManager;
    RegionBootstrap mRegionBootstrap;

    /*
    public void Beaconscanalt2(Context context, String url){

        int i = 0;

        //this.context = contexts;
        List<Region> list = new ArrayList<>();

        String beacon_uuid = null;

        final String surl = url;

        // iBeaconのデータを認識するためのParserフォーマット
        final String IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";
        beaconManager = BeaconManager.getInstanceForApplication(context);//abeaconManager;//
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_FORMAT));
        beaconManager.setForegroundBetweenScanPeriod(1000);
        beaconManager.setBackgroundBetweenScanPeriod(1000);



        Identifier identifier = null;
        //UUIDの作成
        try {
            identifier = Identifier.parse(beacon_uuid.toUpperCase());
        }catch (Exception e){
            e.printStackTrace();
        }

        //今回はmajor, minorの指定はしない
        Region region = new Region("uuid-region-bootstrap" + i, identifier, null, null);

        list.add(region);
        i++;

        //mRegionBootstrap = new RegionBootstrap(context, list);

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                BeaconHolder holder = BeaconHolder.getInstance();
                String[] beacon_info = new String[4];
                // 検出したビーコンの情報を全部みる
                double lastDistance = Double.MAX_VALUE;
                Beacon nearBeacon = null;

                for (Beacon beacon : beacons) {
                    if (lastDistance > beacon.getDistance()) {
                        nearBeacon = beacon;

                        beacon_info[0] = beacon.getId1().toString();
                        beacon_info[1] = beacon.getId2().toString();
                        beacon_info[2] = beacon.getId3().toString();
                        beacon_info[3] = "RSSI";
                    }

                    //Log.d(TAG, "UUID:" + beacon.getId1() + ", major:" + beacon.getId2() + ", minor:" + beacon.getId3() + ", Distance:" + beacon.getDistance() + ",RSSI" + beacon.getRssi() + ", TxPower" + beacon.getTxPower());
                }

                if (nearBeacon != null && matchBeacon(nearBeacon) == false) {
                    //mLastBeacon = new Beacon.Builder().copyBeaconFields(nearBeacon).build();
                    holder.setTestString(beacon_info);
                    scanEvent(surl);
                }
            }
        });
    }

    Beacon matchbeacon1 = null;
    Beacon matchbeacon2 = null;

    public boolean matchBeacon(Beacon nearBeacon){

        boolean match = false;

        matchbeacon2 = matchbeacon1;
        matchbeacon1 = nearBeacon;

        if(matchbeacon2 == matchbeacon1){
            match = true;
        }else if(matchbeacon2 != matchbeacon1){
        }
        return match;
    }


    public void Beaconscanalt1(Context context){

        //this.context = context;

        // iBeaconのデータを認識するためのParserフォーマット
        final String IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

        // iBeaconのデータを受信できるようにParserを設定
        beaconManager = BeaconManager.getInstanceForApplication(context);//beaconManager2;
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_FORMAT));

        //beaconManager.unbind(this);

        //beaconManager.bind(this);

    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                // 検出したビーコンの情報を全部Logに書き出す
                for (Beacon beacon : beacons) {
                    Log.d("MyActivity", "UUID:" + beacon.getId1() + ", major:" + beacon.getId2() + ", minor:" + beacon.getId3() + ", Distance:" + beacon.getDistance());
                }
            }
        });

        try {
            // 距離観測の開始
            beaconManager.startRangingBeaconsInRegion(new Region("unique-ranging-region-id", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    protected void onResume() {
        super.onResume();
        beaconManager.bind(this);
    }

    protected void onPause() {
        beaconManager.unbind(this);
        super.onPause();
    }*/


    /*
    //@Override
    public void BeaconScanalt(Context context,/* BeaconManager beaconManager2,/RegionBootstrap regionBootstrap2, String url) {
        //super.onCreate();

        final String surl = url;

        // iBeaconのデータを認識するためのParserフォーマット
        final String IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

        RegionBootstrap regionBootstrap;

        // iBeacon領域
        //Region region;

        //this.context = context;

        // iBeaconのデータを受信できるようにParserを設定
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(context);//beaconManager2;
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_FORMAT));
        // Beaconをスキャンする間隔
        beaconManager.setBackgroundBetweenScanPeriod(1000);

        // UUID, major, minorの指定はしない
        //region = new Region("uuid-region-bootstrap-001", null, null, null);
        regionBootstrap = regionBootstrap2;


        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                // 検出したビーコンの情報を全部Logに書き出す
                for(Beacon beacon : beacons) {

                    BeaconHolder holder = BeaconHolder.getInstance();
                    String[] beacon_info = new String[4];
                    beacon_info[0] = beacon.getId1().toString();
                    beacon_info[1] = beacon.getId2().toString();
                    beacon_info[2] = beacon.getId3().toString();
                    beacon_info[3] = "RSSI";
                    holder.setTestString(beacon_info);

                    scanEvent(surl);

                    //Log.d("BeaconApplication", "UUID:" + beacon.getId1() + ", major:" + beacon.getId2() + ", minor:" + beacon.getId3() + ", Distance:" + beacon.getDistance() + ",RSSI" + beacon.getRssi() + ", TxPower" + beacon.getTxPower());

                    //BeaconScan2(room_uuid);
                }
            }
        });

    }*/
    public String[] beforebeacon = {"A","A","A"};

    // 同じbeaconが連続何回受信されたかカウントする変数（入室判定用）
    public int entercount = 1;
    // 入室通知を出したかチェックする変数
    public boolean entercheck = false;
    // 直前に通知したbeacon情報を保存する配列
    public String[] enterbefore = {"B","B","B"};
    // enterbeforeと新たに受信したものの一致を判定する配列
    public boolean[] notifycheck = new boolean[3];

    // UUID,major,minor,RSSIの4つの情報を入れる
    public String[] nearbeacon2 = new String[4];
    // 10個のBeacon(さらにそれぞれを10個、４つの情報を持つ)を監視する
    public String[][][] beaconlist2 = new String[10][10][4];
    // ソート用配列
    public String[][][] sortbeacon = new String[10][10][4];

    // beaconの更新があったかをチェックする配列
    boolean[] updatecheck = new boolean[10];
    // 更新フラグをリセットする関数
    public boolean[] bupdcheckset(){
        boolean[] bupdcheck = new boolean[10];
        for(int i = 0; i < 10; i++){
            bupdcheck[i] = false;
        }
        return bupdcheck;
    }
    // 更新フラグを見て更新が無いbeacon情報を削除する関数
    public String[][][] beaconremove(boolean[] bupdatecheck, String[][][] bbeaconlist2){

        String[][][] removecheck = new String[10][10][4];
        removecheck = bbeaconlist2;

        for(int i = 0; i < 10; i++){
            if(!bupdatecheck[i]){
                for(int j = 0; j < 10; j++){
                    for(int k = 0; k < 4; k++) {
                        removecheck[i][j][k] = "A";
                    }
                }
            }else if(bupdatecheck[i]){

            }
        }

        return removecheck;
    }

    public void BeaconScan(ContentResolver resolver, BluetoothManager bluetoothManager2, int scan1, int scan2, String url){

        //this.context = contexts;
        final ContentResolver resolver1 = resolver;

        final String surl = url;

        final DBaccess db = new DBaccess();

        // Bluetooth Adapter の取得
        BluetoothManager bluetoothManager = bluetoothManager2;
        final BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();

        final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

                // ここに結果に対して行う処理を記述する

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

                        String major = String.valueOf(Integer.parseInt(IntToHex2(scanRecord[25] & 0xff) + IntToHex2(scanRecord[26] & 0xff), 16));
                        String minor = String.valueOf(Integer.parseInt(IntToHex2(scanRecord[27] & 0xff) + IntToHex2(scanRecord[28] & 0xff), 16));

                        /*
                        byte[] by1 = new byte[2];
                        byte[] by2 = new byte[2];

                        by1[0] = scanRecord[25];
                        by1[1] = scanRecord[26];
                        by2[0] = scanRecord[27];
                        by2[1] = scanRecord[28];

                        String major = IntToHex3(by1);
                        String minor = IntToHex3(by2);
                        */

                        String scan_rssi = String.valueOf(rssi);

                        String[] beacon_info = new String[4];

                        BeaconHolder holder = BeaconHolder.getInstance();

                        beacon_info[0] = uuid;
                        beacon_info[1] = major;
                        beacon_info[2] = minor;
                        beacon_info[3] = scan_rssi;

                        // 最も強いbeacon情報を取得
                        String[] beacon_info2 = beaconlist2(beacon_info);

                        //String roomtest = beacon_info2[1] + ":" + beacon_info2[2];

                        // 最終的にここはDBに通知するプログラムになる

                        //db.dbaccess(resolver1,roomtest);

                        holder.setTestString(beacon_info2);

                        // 画面表示用
                        beaconlog();

                        // 前回と同じものか判定
                        boolean[] check = new boolean[3];
                        for( int i = 0; i < 3; i++){
                            if( beforebeacon[i].equals(beacon_info2[i])){
                                check[i] = true;
                            }
                        }

                        //scanEvent(surl);

                        // 通知したものと同じものか判定
                        for( int i = 0; i < 3; i++){
                            if( enterbefore[i].equals(beacon_info2[i])){
                                notifycheck[i] = true;
                            }
                        }

                        // 直前に通知したものと同じならなにもしない
                        if(notifycheck[0] && notifycheck[1] && notifycheck[2]){
                        }
                        // そうでないなら以下の処理
                        else {
                            // 前回と同じものならカウントアップ
                            if (check[0] && check[1] && check[2]) {
                                entercount++;
                                // 46回連続（約11秒）第1位のbeacon　かつまだ通知していないもの　ならその部屋にいるとする
                                if (entercount == 46 && !entercheck) {
                                    // データベースに通知
                                    scanEvent(surl, resolver1);
                                    // 入室判定をtrueに
                                    entercheck = true;
                                    // 通知したbeacon情報を保存
                                    for (int i = 0; i < 3; i++) {
                                        enterbefore[i] = beacon_info2[i];
                                    }
                                    // カウントリセット
                                    entercount = 1;
                                    Log.d("ENTER", "ENTER:" + enterbefore[2]);
                                }
                                // 違うものならカウントリセット
                            } else {
                                // 入室判定をfalseに
                                entercheck = false;
                                // カウントリセット
                                entercount = 1;
                                // 通信処理
                                //scanEvent(surl, resolver1);
                                // データベース操作
                                //db.dbaccess(resolver1,roomtest);
                            }
                        }
                        beforebeacon = beacon_info2;
                        Log.d("Beacon", "UUID:" + uuid + ", major:" + major + ", minor:" + minor + ", RSSI:" + scan_rssi);

                        uuid = null;
                        major = null;
                        minor = null;
                        scan_rssi = null;
                        beacon_info = null;

                    }
                }

            }

            // intデータを 2桁16進数に変換するメソッド
            public String IntToHex2(int i) {
                char hex_2[] = {Character.forDigit((i>>4) & 0x0f,16),Character.forDigit(i&0x0f, 16)};
                String hex_2_str = new String(hex_2);
                return hex_2_str.toUpperCase();
            }

        };

        TimerTask scanStart = new TimerTask() {
            public void run() {

                // スキャン開始
                mBluetoothAdapter.startLeScan(mLeScanCallback);
                Log.d("scanStart","START");

            }
        };

        TimerTask scanStop = new TimerTask() {
            public void run() {

                // スキャン停止
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                Log.d("scanStop", "STOP");

            }
        };
        // タイマー２つを動かす
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        timer1.scheduleAtFixedRate(scanStart, 0, scan1);
        timer2.scheduleAtFixedRate(scanStop, scan2, scan1);


        // beacon更新フラグを6.8秒毎にリセットする
        TimerTask flgreset = new TimerTask() {
            public void run() {
                // リセット関数
                updatecheck = bupdcheckset();
            }
        };
        // beacon更新フラグを1周期＋スキャン1回毎にチェックして更新がないものを削除する
        TimerTask remove = new TimerTask() {
            public void run() {
                // 削除関数
                beaconlist2 = beaconremove(updatecheck, beaconlist2);
            }
        };
        // タイマーを動かす
        Timer timer3 = new Timer();
        Timer timer4 = new Timer();
        timer3.scheduleAtFixedRate(flgreset, 0, 2*(scan1+scan2));
        timer4.scheduleAtFixedRate(remove, 0, scan1+2*(scan1+scan2));

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    public String[] nearbeacon = new String[4];
    public String[][] beaconlist = new String[20][4];
    boolean beaconlistflg = false;

    public String[] beaconlistArray1(){
        String[] nbArray = {"A","A","A","-500"};
        return nbArray;
    }

    public String[][] beaconlistArray2(){
        String[][] sArray = new String[20][4];
        for( int i = 0; i < 20; i++){
            for( int j = 0; j < 4; j++){
                sArray[i][j] = "-500";
            }
        }
        return sArray;
    }

    // 送られてきたBeacon情報を常に20個保持しRSSIが最も大きいものを返す関数
    public String[] beaconlist(String[] beacon_info){

        //初回のみ配列を用意する
        if(beaconlistflg == false){
            nearbeacon = beaconlistArray1();
            beaconlist = beaconlistArray2();
            beaconlistflg = true;
        }else{
        }
        // 一番古いものは破棄する
        //beaconlist[19] = null;
        // 常に20個の情報を保持する
        // ビーコンが見つかるたびに19個目までは情報を一つずつ繰り上げる
        for( int i = 0; i < 19; i++){
            for( int j = 0; j < 4; j++){
                beaconlist[i][j] = beaconlist[i+1][j];
            }
        }
        // 20個目に最新の情報を入れる
        for( int k = 0; k < 4; k++){
            beaconlist[19][k] = beacon_info[k];
        }
        // 保持している20個の中にnearbeaconと一致するものが無ければリセットしてRSSIが最も大きいものを探す
        // 指定したRSSI値より小さければ通知せずに探索し直し
        do {
            for (int n = 0; n < 20; n++) {
                if (nearbeacon != beaconlist[n]) {
                    nearbeacon[3] = "-500";
                    for (int l = 0; l < 20; l++) {
                        if (Integer.parseInt(nearbeacon[3]) <= Integer.parseInt(beaconlist[l][3])) {
                            for (int m = 0; m < 4; m++) {
                                nearbeacon[m] = beaconlist[l][m];
                            }
                        } else {
                        }
                    }
                }
            }
        }while (Integer.parseInt(nearbeacon[3]) < -100);

        // 最も近い(であろう)Beacon情報を返す
        return nearbeacon;
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    // UUID,major,minor,RSSIの4つの情報を入れる
    public String[] nearbeacon2 = new String[4];
    // 10個のBeacon(さらにそれぞれを10個、４つの情報を持つ)を監視する
    public String[][][] beaconlist2 = new String[10][10][4];
    // ソート用配列
    public String[][][] sortbeacon = new String[10][10][4];
    // CountDownの初期値 10秒を1秒毎にカウント
    /*
    public MyCountDownTimer[] mcdt = new MyCountDownTimer[10];
    boolean[] mcdtcheck = new boolean[10];
    // mcdtのキー用配列　　　　　　　　　　　　必要ないかも
    int[] mcdtkey = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int timeoutbeacon;
    long tobtime;
    static boolean mcdtfinish = false;*/

    // beaconの更新があったかをチェックする配列
    //boolean[] updatecheck = new boolean[10];

    boolean beaconlistflg2 = false;

    public String[] beaconlistArray21(){
        String[] nbArray = {"A","A","A","A"};
        return nbArray;
    }

    public String[][][] beaconlistArray22(){
        String[][][] sArray = new String[10][10][4];
        for( int i = 0; i < 10; i++){
            for( int j = 0; j < 10; j++){
                for( int k = 0; k < 4; k++){
                    sArray[i][j][k] = "A";
                }
            }
        }
        return sArray;
    }

    /*
    public boolean[] bupdcheckset(){
        boolean[] bupdcheck = new boolean[10];
        for(int i = 0; i < 10; i++){
            bupdcheck[i] = false;
        }
        return bupdcheck;
    }*/
    /*
    // 10個のタイマーの初期化
    public MyCountDownTimer[] countdowntimerset(){

        final MyCountDownTimer[] cdt = new MyCountDownTimer[10];
        for(int i = 0; i < 10; i++){
            cdt[i] = new MyCountDownTimer(10000, 100);
        }

        return cdt;
    }
    // それぞれのタイマーが起動しているかのチェック用
    public boolean[] countdowntimercheckset(){

        boolean[] cdtcheck = new boolean[10];
        for(int i = 0; i < 10; i++){
            cdtcheck[i] = false;
        }

        return cdtcheck;
    }*/


    /*
    // カウントダウンの残り時間（初期値）
    private static long countMillis = 10000;
    // カウントダウンタイマー
    static class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        // キーを渡す//////////////////////////////////////////////////////////////////////////////////
        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
            mcdtfinish = true;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            // 残り時間をcountMillisに代入
            countMillis = millisUntilFinished;
        }

        //残り時間を返す。
        public final Long getCount()
        {
            return countMillis;
        }
    }*/


    // 送られてきたBeacon情報から部屋認識をする関数
    public String[] beaconlist2(String[] newbeaconinfo){

        // String[4] newbeaconinfo          :受信したiBeacon情報が入っている仮引数。中身はUUID,major,minor,RSSI
        // String[4] nearbeacon2            :newbeaconinfoが入る配列。初回のみbeaconlistArray21()で中身がA,A,A,Aに初期化される
        // String[10][10][4] beaconlist2    :取得済みのiBeaocn情報が入っている配列。最大10種類のiBeacon情報をそれぞれ最新の10個保存している
        // boolean beaconlistflg2           :初回のみ配列を用意するためのフラグ。
        // String[3] newbeaconinfo2         :newbeaconinfoのRSSI以外の情報を入れる配列。これによりUUID,major,minorでの一致の管理をしやすくする
        // String[10][10][3] beaconlist22   :beaconlist2のRSSI以外の情報を入れる配列。同上
        // boolean[3] check                 :UUID,major,minorの一致を真偽で管理する配列。3個すべて正なら一致とみなし一致したところに加える
        // boolean check2                   :一致したものがなければtrueになりその処理をする。
        // String[10][4] retrunbeacon       :RSSIの中央値を取った後の最大10種類のiBeacon情報を入れる配列。中身はUUID,major,minor,RSSIの中央値

        //初回のみ配列を用意する
        if(!beaconlistflg2){
            nearbeacon2 = beaconlistArray21();
            beaconlist2 = beaconlistArray22();
            //mcdt = countdowntimerset();
            //mcdtcheck = countdowntimercheckset();
            beaconlistflg2 = true;
            updatecheck = bupdcheckset();
        }else{
        }
        sortbeacon = beaconlistArray22();

        //updatecheck = bupdcheckset();

        /*
        // 残り時間が少ないものをキープ
        timeoutbeacon = -2;
        tobtime = 20000;
        for(int i = 0; i < 10; i++){
            if(mcdtcheck[i]){
                if(tobtime > mcdt[i].getCount()){
                    timeoutbeacon = i;
                    tobtime = mcdt[i].getCount();
                }
            }
        }
        //Log.d("TIMEOUT", timeoutbeacon + ":" + tobtime);
        // 10秒たったものを削除
        if(mcdtfinish){
            for( int i = 0; i < 10; i++){
                for( int j = 0; j < 4; j++){
                    beaconlist2[timeoutbeacon][i][j] = "A";
                }
            }
            mcdt[timeoutbeacon].cancel();
            mcdtcheck[timeoutbeacon] = false;
            mcdtkey[timeoutbeacon] = -1;
            mcdt[timeoutbeacon] = null;
            mcdtfinish = false;
        }*/


        // RSSIをいったんカットする
        // それぞれRSSI以外の情報が入る
        /*
        String[] newbeaconinfo2 = new String[3];
        String[][][] beaconlist22 = new String[10][10][3];
        for( int i = 0; i < 3; i++){
            newbeaconinfo2[i] = newbeaconinfo[i];
        }
        for( int i = 0; i < 10; i++){
            for( int j = 0; j < 10; j++){
                for( int k = 0; k < 3; k++){
                    beaconlist22[i][j][k] = beaconlist2[i][j][k];
                }
            }
        }*/

        // UUID,major,minorの一致を真偽で管理する配列
        boolean[] check = new boolean[3];
        check[0] = false;
        check[1] = false;
        check[2] = false;
        // 一致したものがあったかをチェックする
        boolean check2 = true;

        // 新たに送られてきたBeacon情報が10個の中にあればそこに追加する
        // 新たに送られてきたBeacon情報が10個の中に無ければ最も古いor弱いものと入れ替える
        for( int i = 0; i < 10; i++){
            for( int j = 0; j < 3; j++){
                check[j] = beaconlist2[i][9][j].equals(newbeaconinfo[j]);
            }
            // UUID,major,minorがすべて一致しているもの(checkが全てtrue)のみ以下のif処理を行う
            // 一致したところに新しく追加　最も古いものは破棄して一つずつ繰り上げる
            if(check[0] && check[1] && check[2]){
                // 9個目まで入れ替え
                for( int k = 0; k < 9; k++){
                    beaconlist2[i][k] = beaconlist2[i][k+1];
                }
                // 10個目に新しく追加
                beaconlist2[i][9] = newbeaconinfo;
                check2 = false;
                if(!updatecheck[i]){
                    updatecheck[i] = true;
                }
                /*
                // 更新があればカウントリセット　初回のみカウントスタートだけ
                mcdt[i].cancel();
                mcdtcheck[i] = false;
                mcdtkey[i] = -1;
                mcdt[i] = null;
                // キャンセルしたらインスタンス再生成
                mcdt[i] = new MyCountDownTimer(10000, 100);
                mcdt[i].start();
                mcdtcheck[i] = true;
                mcdtkey[i] = i;*/
                break;
            }
        }
        // ソート用配列にコピー
        for( int i = 0; i < 10; i++){
            for( int j = 0; j < 10; j++){
                sortbeacon[i][j] = beaconlist2[i][j];
            }
        }

        // 最終判定用配列１
        String[][] returnbeacon = new String[10][4];
        // 中央値算出
        for( int i = 0; i < 10; i++){
            // 空ではない要素数
            int length = 0;
            // 10個のうちデータが入ってるものをカウントする
            for( int j = 0; j < 10; j++){
                if(!"A".equals(sortbeacon[i][j][0])){
                    // "A"が含まれている(＝データが入ってない)場合は何もしない
                    // データが入っている場合のみカウントする(lengthは0~10)
                    length++;
                }/*else{
                    // データが入っている場合のみカウントする(lengthは0~10)
                    length++;
                }*/
            }
            // RSSI値で降順ソート [10-length]
            if(!"A".equals(sortbeacon[i][9][0])){
                for (int k = 0; k < length - 1; k++) {
                    for (int l = 0; l < length - 1 - k; l++) {
                        if (Integer.parseInt(sortbeacon[i][10 - length + l][3]) < Integer.parseInt(sortbeacon[i][10 - length + l + 1][3])) {
                            String[] temp = sortbeacon[i][10 - length + l];
                            sortbeacon[i][10 - length + l] = sortbeacon[i][10 - length + l + 1];
                            sortbeacon[i][10 - length + l + 1] = temp;
                        }
                    }
                }
            }
            if(length > 0) {
                switch (length % 2) {
                    case 0:
                        // 中央値
                        returnbeacon[i] = sortbeacon[i][(9 - length) + ((length / 2) + 1)];
                        break;
                    case 1:
                        // 中央値
                        returnbeacon[i] = sortbeacon[i][(9 - length) + (length / 2)];
                        break;
                }
            }else{
                returnbeacon[i] = sortbeacon[i][9];
            }
            Log.d("scan3",i + ":" + returnbeacon[i][1] + "," + returnbeacon[i][2] + "," + returnbeacon[i][3]);
        }

        // check2がtrue(一致したものが無い)なら電波がよわいものか古いものと入れ替え
        if(check2){
            String[] oldbeacon = new String[4];
            oldbeacon = returnbeacon[0];
            boolean[] check3 = new boolean[3];
            boolean empcheck = false;
            int emparray = 0;
            int oldarray = 0;
            for( int i = 1; i < 10; i++){
                // まず最もRSSIが弱いものを見つける
                if(!"A".equals(returnbeacon[i][0]) && !"A".equals(oldbeacon[0])){
                    if (Integer.parseInt(oldbeacon[3]) > Integer.parseInt(returnbeacon[i][3])) {
                        oldbeacon = returnbeacon[i];
                        oldarray = i;
                    }
                }
            }
            for( int i = 0; i < 10; i++){
                // それとは別に情報の入っていない配列があるかチェックする
                if("A".equals(beaconlist2[i][9][0]) && !empcheck){
                    emparray = i;
                    empcheck = true;
                }
            }
            // もし10個の配列に一つでも情報が入っていないものがあれば
            // そのうち最も若いものの10個目に新しく追加
            if(empcheck){
                beaconlist2[emparray][9] = newbeaconinfo;
                if(!updatecheck[emparray]){
                    updatecheck[emparray] = true;
                }
                // 空の最終判定用配列のうち最も若いものにnewbeaconinfoを入れる
                returnbeacon[emparray] = newbeaconinfo;
                /*
                // タイマースタート
                mcdt[emparray] = new MyCountDownTimer(10000, 100);
                mcdt[emparray].start();
                mcdtcheck[emparray] = true;
                mcdtkey[emparray] = emparray;*/
                //Log.d("scan2",emparray + ":" + returnbeacon[emparray][1] + "," + returnbeacon[emparray][2]);
            }
            // もし10個の配列すべてに既に情報が入っていて、newbeaconinfoのRSSIがoldbeaconのものより大きければ
            // それに対応するbeaconlist2の中身をすべて空("A")にしてそこの９個目の配列にnewbeaconinfoを入れる
            else if(Integer.parseInt(oldbeacon[3]) < Integer.parseInt(newbeaconinfo[3])){
                /*
                // 対応するタイマーを一度キャンセル
                mcdt[oldarray].cancel();
                mcdtcheck[oldarray] = false;
                mcdtkey[oldarray] = -1;
                mcdt[oldarray] = null;*/
                for( int i = 0; i < 10; i++){
                    for( int j = 0; j < 4; j++){
                        beaconlist2[oldarray][i][j] = "A";
                    }
                }
                beaconlist2[oldarray][9] = newbeaconinfo;
                if(!updatecheck[oldarray]){
                    updatecheck[oldarray] = true;
                }
                /*
                // タイマースタート
                mcdt[oldarray] = new MyCountDownTimer(10000, 100);
                mcdt[oldarray].start();
                mcdtcheck[oldarray] = true;
                mcdtkey[oldarray] = oldarray;*/

                //mcdt[oldarray].getCount();
                /*
                for( int i = 0; i < 10; i++){
                    for( int j = 0; j < 3; j++){
                        check3[j] = beaconlist2[i][9][j].equals(oldbeacon[j]);
                    }
                    // UUID,major,minorがすべて一致しているもの(checkが全てtrue)のみ以下のif処理を行う
                    if(check3[0] && check3[1] && check3[2]){
                        // 9個目まで空("A")にする
                        for( int k = 0; k < 9; k++){
                            for( int l = 0; l < 4; l++){
                                beaconlist2[i][k][l] = "A";
                            }
                        }
                        // 10個目に新しく追加
                        beaconlist2[i][9] = newbeaconinfo;
                        break;
                    }else{
                    }
                }*/
                // 最もRSSIが弱いものが入っていた最終判定用配列を上書き
                returnbeacon[oldarray] = newbeaconinfo;
                //Log.d("scan2",oldarray + ":" + returnbeacon[oldarray][1] + "," + returnbeacon[oldarray][2]);
            }

        }

        // ここで初めて返すべき情報が出揃う
        // 10個のreturnbeaconから空のものを除いたものからRSSIが最も大きいものを選んで返す
        String[] finalbeacon = {"A","A","A","-500"};
        //finalbeacon = returnbeacon[0];
        for( int i = 0; i < 10; i++){
            if("A".equals(returnbeacon[i][0])){
            }
            else if(Integer.parseInt(finalbeacon[3]) < Integer.parseInt(returnbeacon[i][3])) {
                finalbeacon = returnbeacon[i];
            }
        }

        /*
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 4; j++) {
                returnbeacon[i][j] = "A";
            }
        }*/

        //Log.d("TIMEOUT", mcdtkey[0] + "," + mcdt[0].getCount() + ":" + mcdtkey[1] + "," + mcdt[1].getCount());

        Log.d("scan2",finalbeacon[0]+","+finalbeacon[1]+","+finalbeacon[2]+","+finalbeacon[3]);

        return finalbeacon;
    }

/*
    // カウントダウンの残り時間（初期値）
    private static long countMillis = 10000;
    // カウントダウンタイマー
    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        // キーを渡す//////////////////////////////////////////////////////////////////////////////////
        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            // 残り時間をcountMillisに代入
            countMillis = millisUntilFinished;
        }
    }*/
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void scanEvent(String url, ContentResolver resolver){

        final DBaccess db = new DBaccess();
        final ContentResolver resolver1 = resolver;

        Log.d("SCANEVENT", "call");
        // Beacon情報を受け取るための配列
        final String[] beacon_info;
        // 受け取ったBeacon情報とデータベースの情報をまとめるための配列
        final String[] stringArray = new String[11];

        // DtaHolderクラスからBeacon情報を受け取る
        BeaconHolder holder = BeaconHolder.getInstance();
        beacon_info = holder.getTestString();

        // サーバとの通信準備
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(2000);
        // リクエストパラメータ
        final RequestParams params = new RequestParams();
        // 送るパラメータ(UUID)
        //テスト用 beacon_info[1] = "0514";
        params.put("mj_number", beacon_info[1]);//UUIDじゃなくmajorをセットするようにする

        String server_url = url;

        // サーバと通信
        client.get(server_url, params, new AsyncHttpResponseHandler() {

            BeaconinfoHolder beaconinfoHolder = BeaconinfoHolder.getinstance();

            // 通信成功したときの処理
            @Override
            public void onSuccess(/*View view, */int i, Header[] headers, byte[] bytes) {
                //InputStream input;
                try {

                    String json = new String(bytes);
                    System.out.println(json);
                    JSONObject jsonObject = new JSONObject(json);

                    //Log.d("Server2",jsonObject.getString("room_number") + "," + jsonObject.getString("room_name"));

                    //Log.d("!!!!!!!",jsonObject.getString("room_number") + "," + jsonObject.getString("building_id") + "," + jsonObject.getString("building_name") + "," + jsonObject.getString("roomnumber_id") + "," + jsonObject.getString("roomnumber_no") + "," + jsonObject.getString("room_id") + "," + jsonObject.getString("beacon_identifier") + "," + jsonObject.getString("room_name"));

                    //String[] stringArray2 = new String[7];

                    //Log.d("Server2",jsonObject.getString("room_number"));

                    //stringArray[0] = jsonObject.getString("building_id");
                    //stringArray[1] = jsonObject.getString("building_name");
                    //stringArray[2] = jsonObject.getString("roomnumber_id");
                    //stringArray[3] = jsonObject.getString("roomnumber_no");
                    //stringArray[4] = jsonObject.getString("room_id");
                    //stringArray[5] = jsonObject.getString("beacon_identifier");
                    //stringArray[6] = jsonObject.getString("");

                    stringArray[0] = "A";
                    stringArray[1] = "B";
                    stringArray[2] = "C";
                    stringArray[3] = "D";
                    //stringArray[3] = jsonObject.getString("roomnumber_no");
                    stringArray[4] = "E";
                    stringArray[5] = "F";
                    stringArray[6] = "G";

                    stringArray[7] = beacon_info[0];
                    stringArray[8] = beacon_info[1];
                    stringArray[9] = beacon_info[2];
                    stringArray[10] = beacon_info[3];
                    // databaseクラスにBeacon情報とデータベースの情報を渡す
                    //beaconinfoHolder.setData(stringArray);
                    // データベース操作
                    db.dbaccess(resolver1, stringArray[3]);

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                }

            }

            // 通信失敗したときの処理
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                stringArray[0] = "A";
                stringArray[1] = "B";
                stringArray[2] = "C";
                stringArray[3] = "D";
                stringArray[4] = "E";
                stringArray[5] = "F";
                stringArray[6] = "G";

                stringArray[7] = beacon_info[0];
                stringArray[8] = beacon_info[1];
                stringArray[9] = beacon_info[2];
                stringArray[10] = beacon_info[3];

                // databaseクラスにBeacon情報とデータベースの情報を渡す
                //beaconinfoHolder.setData(stringArray);
                db.dbaccess(resolver1, "roomname");

            }


        });

    }

    // 確認用に画面に表示するための関数
    public void beaconlog(){
        // Beacon情報を受け取るための配列
        final String[] beacon_info;
        // DtaHolderクラスからBeacon情報を受け取る
        BeaconHolder holder = BeaconHolder.getInstance();
        beacon_info = holder.getTestString();
        // databaseクラスにBeacon情報とデータベースの情報を渡す
        BeaconinfoHolder beaconinfoHolder = BeaconinfoHolder.getinstance();
        beaconinfoHolder.setData(beacon_info);
    }

}
