package com.example.k13006kk.mylibrary;

/**
 * Created by k13006kk on 2016/02/22.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BeaconApplication{

    public String[] beforebeacon = {"A","A","A"};

    public static String surl;
    public static ContentResolver resolver1;

    // 同じbeaconが連続何回受信されたかカウントする変数（入室判定用）
    //public int entercount = 1;
    // しきい値を越えているかチェックするフラグ
    public boolean threshold = false;
    // 入室通知を出したかチェックするフラグ
    //public static boolean entercheck = false;

    // 入室判定が始まっているかチェックするフラグ
    public boolean timercheck = false;
    // 退室判定が始まっているかチェックするフラグ
    public boolean exettimercheck = false;
    // 退室判定が始まっているかチェックするフラグ(ビーコン見つからない場合)
    public boolean exettimercheck2 = false;

    // 入室タイマーのインスタンスがセットされているかチェックするフラグ
    public boolean timersetcheck = false;
    // 退室タイマーのインスタンスがセットされているかチェックするフラグ
    public boolean exittimersetcheck = false;
    // 退室タイマーのインスタンスがセットされているかチェックするフラグ(ビーコン見つからない場合)
    public boolean exittimersetcheck2 = false;
    // ビーコンが見つかるかどうかのフラグ
    public static boolean beaconwatch = false;
    public static boolean beaconwatchbefor = false;
    public static int scanstopcount = 0;
    private final Handler handler = new Handler();

    // 入室タイマー宣言
    MyCountDownTimer mcdt;
    // 退室タイマー宣言
    ExitCountDownTimer exitCountDownTimer;
    // 退室タイマー宣言(ビーコン見つからない場合)
    ExitCountDownTimer2 exitCountDownTimer2;

    // 直前に通知したbeacon情報を保存する配列
    //public String[] enterbefore = {"B","B","B"};
    // enterbeforeと新たに受信したものの一致を判定する配列
    //public boolean[] notifycheck = new boolean[3];

    // UUID,major,minor,RSSIの4つの情報を入れる
    public String[] nearbeacon2 = new String[4];
    // 10個のBeacon(さらにそれぞれを10個、４つの情報を持つ)を監視する
    public String[][][] beaconlist2 = new String[10][10][4];
    // ソート用配列
    public String[][][] sortbeacon = new String[10][10][4];

    // 入室・退室状態を管理するフラグ　入室＝true　退室＝false
    public static boolean enex = false;
    // 入室したビーコン情報を保持する変数
    public static String[] enterbeacon = new String[4];
    // 入室した部屋情報を保持する配列（退室記録用）
    public static String[] enterroom = new String[3];

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

    // 入室判定用カウントダウンタイマー
    static class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
            //直前が退室状態だったら　入室判定を出す
            if(!enex){
                // データベースに通知
                enterroom(surl, resolver1);
                //入室状態に変更
                enex = true;
            }
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            // 残り時間をcountMillisに代入
            //countMillis = millisUntilFinished;
        }
    }

    // 退室判定用カウントダウンタイマー
    static class ExitCountDownTimer extends CountDownTimer {

        //BeaconHolder holder = BeaconHolder.getInstance();

        public ExitCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
            //直前が退室状態だったら　入室判定を出す
            if(enex){
                //Log.d("exit","exit");
                // データベースに通知
                exitroom(resolver1);
                //入室状態に変更
                enex = false;
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            // 残り時間をcountMillisに代入
            //countMillis = millisUntilFinished;
            beaconwatch = true;
        }
    }
    // 退室判定用カウントダウンタイマー(ビーコン見つからない場合)
    static class ExitCountDownTimer2 extends CountDownTimer {

        //BeaconHolder holder = BeaconHolder.getInstance();

        public ExitCountDownTimer2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
            //直前が退室状態だったら　入室判定を出す
            if(enex){
                //Log.d("exit","exit");
                // データベースに通知
                exitroom(resolver1);
                //入室状態に変更
                enex = false;
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            // 残り時間をcountMillisに代入
            //countMillis = millisUntilFinished;
        }
    }

    public void BeaconScan(ContentResolver resolver, BluetoothManager bluetoothManager2, int scan1, int scan2, String url){

        //this.context = contexts;
        resolver1 = resolver;
        surl = url;

        //final DBaccess db = new DBaccess();

        // Bluetooth Adapter の取得
        BluetoothManager bluetoothManager = bluetoothManager2;
        final BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();

        final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

                // ここに結果に対して行う処理を記述する

                // 交互にtrueとfalseが入れ替わるようにする
                beaconwatch = !beaconwatch;

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

                        String scan_rssi = String.valueOf(rssi);

                        String[] beacon_info = new String[4];

                        BeaconHolder holder = BeaconHolder.getInstance();

                        beacon_info[0] = uuid;
                        beacon_info[1] = major;
                        beacon_info[2] = minor;
                        beacon_info[3] = scan_rssi;

                        // 最も強いbeacon情報を取得
                        String[] beacon_info2 = strongIdentify(beacon_info);

                        //if("A".equals(beacon_info2[1])) {
                        //}else{
                            holder.setTestString(beacon_info2);
                        //}

                        /*
                        // 通知したものと同じものか判定
                        boolean[] notifycheck = new boolean[3];
                        for( int i = 0; i < 3; i++){
                            if( enterbefore[i].equals(beacon_info2[i])){
                                notifycheck[i] = true;
                            }
                        }*/

                        // 直前に通知したものと同じならなにもしない
                        //if (notifycheck[0] && notifycheck[1] && notifycheck[2]) {
                        //}// そうでないなら以下の処理
                        //else {
                            // 前回と同じものなら


                        // ここから↓入室判定されてないor体質判定された後の処理　enex=false ////////////////
                        if(!enex) {
                            exettimercheck = false;
                            exittimersetcheck = false;
                            exettimercheck2 = false;
                            exittimersetcheck2 = false;
                            scanstopcount = 0;

                            // 前回と同じものか判定
                            boolean[] check = new boolean[3];
                            for( int i = 0; i < 3; i++){
                                if( beforebeacon[i].equals(beacon_info2[i])){
                                    check[i] = true;
                                }
                            }
                            // 入室タイマーの設定
                            if (!timersetcheck) {
                                mcdt = new MyCountDownTimer(12000, 100);
                            }
                            if (check[0] && check[1] && check[2]) {
                                // 初めてしきい値を越えたら
                                if (!threshold && Integer.parseInt(beacon_info2[3]) >= -82) {
                                    threshold = true;
                                }
                                // タイマーがスタートしておらず　かつ　/*入室判定がされていなかったら*/　かつしきい値を越えていたら
                                if (!timercheck /*&& !entercheck*/ && threshold) {
                                    // タイマーがスタートしてなかったらタイマースタート
                                    mcdt.start();
                                    timersetcheck = true;
                                    timercheck = true;
                                }
                                // 前回と異なるビーコンが受信されたらタイマー止める
                            }else if (threshold && Integer.parseInt(beacon_info2[3]) >= -82){
                                mcdt.cancel();
                                timersetcheck = false;
                                timercheck = false;
                                threshold = false;
                            }
                            for (int i = 0; i < beacon_info2.length; i++) {
                                enterbeacon[i] = beacon_info2[i];
                            }
                            check = null;
                            //Log.d("exit",enterbeacon[0] + " " + enterbeacon[1] + " " + enterbeacon[2]);
                        }
                        //}

                        // ここから↓入室判定された後の処理　enex=true //////////////////////////////////
                        if(enex){
                            timersetcheck = false;
                            timercheck = false;
                            threshold = false;

                            // 退室タイマーの設定
                            if (!exittimersetcheck) {
                                // ５秒
                                exitCountDownTimer = new ExitCountDownTimer(5000, 100);
                            }
                            // 受信しているビーコンと入室判定したビーコンが同じものかチェック
                            boolean[] notifycheck = new boolean[3];
                            for( int i = 0; i < 3; i++){
                                if( enterbeacon[i].equals(beacon_info2[i])){
                                    notifycheck[i] = true;
                                }
                            }
                            // 同じなら　タイマー止める
                            if (notifycheck[0] && notifycheck[1] && notifycheck[2]){
                                //Log.d("exit","exit");
                                if (exettimercheck){
                                    exitCountDownTimer.cancel();
                                    exettimercheck = false;
                                    exittimersetcheck = false;
                                }
                            } else {// 違うなら　タイマースタート
                                if (!exettimercheck){
                                    exitCountDownTimer.start();
                                    exettimercheck = true;
                                    exittimersetcheck = true;
                                }
                            }
                        }

                        //Log.d("exit",beacon_info2[0] + " " + beacon_info2[1] + " " + beacon_info2[2]);
                        // 次回用に受信したビーコンを保存
                        beforebeacon = beacon_info2;

                        //Log.d("Beacon", "UUID:" + uuid + ", major:" + major + ", minor:" + minor + ", RSSI:" + scan_rssi);

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


        // ビーコンが見つからない場合の退室判定
        TimerTask exit = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (enex) {
                            if(beaconwatch == beaconwatchbefor){
                                scanstopcount++;
                            }else {
                                scanstopcount = 0;
                            }
                            beaconwatchbefor = beaconwatch;

                            // 退室タイマーの設定
                            if (!exittimersetcheck2) {
                                // ５秒
                                exitCountDownTimer2 = new ExitCountDownTimer2(5000, 100);
                            }
                            if (scanstopcount < 10) {
                                if (exettimercheck2) {
                                    exitCountDownTimer2.cancel();
                                    exettimercheck2 = false;
                                    exittimersetcheck2 = false;
                                    //Log.d("exit", Integer.toString(scanstopcount));
                                }
                            } else{
                                if (!exettimercheck2) {
                                    exitCountDownTimer2.start();
                                    exettimercheck2 = true;
                                    exittimersetcheck2 = true;
                                }
                            }
                        }
                    }
                });
            }
        };

        Timer exittask = new Timer();
        exittask.scheduleAtFixedRate(exit, 0, 100);


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


        // beacon更新フラグを10.0//6.8//秒毎にリセットする
        TimerTask flgreset = new TimerTask() {
            public void run() {
                // リセット関数
                updatecheck = bupdcheckset();
            }
        };
        // beacon更新フラグを1周期//＋スキャン1回//毎にチェックして更新がないものを削除する
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
        timer4.scheduleAtFixedRate(remove, 0, (scan1+scan2));//1周期に変更 scan1+2*(scan1+scan2)

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean beaconlistflg2 = false;

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

    // 送られてきたBeacon情報から最も強いビーコンを特定して返す関数
    public String[] strongIdentify(String[] newbeaconinfo){

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
                }
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
                //Log.d("scan2",emparray + ":" + returnbeacon[emparray][1] + "," + returnbeacon[emparray][2]);
            }
            // もし10個の配列すべてに既に情報が入っていて、newbeaconinfoのRSSIがoldbeaconのものより大きければ
            // それに対応するbeaconlist2の中身をすべて空("A")にしてそこの９個目の配列にnewbeaconinfoを入れる
            else if(Integer.parseInt(oldbeacon[3]) < Integer.parseInt(newbeaconinfo[3])){
                for( int i = 0; i < 10; i++){
                    for( int j = 0; j < 4; j++){
                        beaconlist2[oldarray][i][j] = "A";
                    }
                }
                beaconlist2[oldarray][9] = newbeaconinfo;
                if(!updatecheck[oldarray]){
                    updatecheck[oldarray] = true;
                }
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
        return finalbeacon;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void enterroom(String url, ContentResolver resolver){

        final DBaccess db = new DBaccess();
        final ContentResolver resolver1 = resolver;

        //Log.d("SCANEVENT", "call");
        // Beacon情報を受け取るための配列
        final String[] beacon_info;
        // Beacon情報をもとに部屋情報を入れる配列
        final String[] stringArray = new String[3];

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

            // 通信成功したときの処理
            @Override
            public void onSuccess(/*View view, */int i, Header[] headers, byte[] bytes) {
                //InputStream input;
                try {

                    String json = new String(bytes);
                    System.out.println(json);
                    JSONObject jsonObject = new JSONObject(json);

                    stringArray[0] = jsonObject.getString("building_name");
                    stringArray[1] = jsonObject.getString("room_name");
                    stringArray[2] = jsonObject.getString("roomnumber_no");

                    enterroom[0] = jsonObject.getString("building_name");
                    enterroom[1] = jsonObject.getString("room_name");
                    enterroom[2] = jsonObject.getString("roomnumber_no");

                    // データベース操作
                    db.dbEnter(resolver1, stringArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                }

            }

            // 通信失敗したときの処理
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                stringArray[0] = beacon_info[0];
                stringArray[1] = beacon_info[1];
                stringArray[2] = beacon_info[2];

                enterroom[0] = beacon_info[0];
                enterroom[1] = beacon_info[1];
                enterroom[2] = beacon_info[2];

                // databaseクラスにBeacon情報とデータベースの情報を渡す
                db.dbEnter(resolver1, stringArray);
                //Log.d("db", stringArray[8] + ":" + stringArray[9]);
            }
        });
    }

    public static void exitroom(ContentResolver resolver){

        final DBaccess db = new DBaccess();
        final ContentResolver resolver1 = resolver;

        db.dbExit(resolver1, enterroom);

    }

}