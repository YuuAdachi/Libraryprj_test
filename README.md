# Libraryprj_test
  
BLEビーコンをスキャンして部屋認識をするライブラリです。  
利用できる機能は以下です。  
　・ビーコンのスキャン（バックグラウンド動作）  
　・スキャンしているビーコンの情報（UUID,major,minor等）の取得  
　・入室判定（ビーコンスキャンで自動で行われます）  
　・このライブラリを使用したアプリで部屋情報の共有  
  
! ライブラリを使用するには**Android 5.0**以上でお願いします。!  
! クラス名、関数名は変更予定です。!  
  
  
***  
## ビーコンのスキャン  
バックグラウンドで動作させてください。  
  
*受信開始コード*  
**`beaconApplication.BeaconScan(resolver, bluetoothManager, scan1,scan2,url);`**  
  
・`resolver`  
　　`ContentResolver resolver = getContentResolver();`  
　　としておいてください。  
・`bluetoothManager`  
　　`BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);`  
　　としておいてください。  
・`scan1`  
　　受信間隔です。ミリ秒で指定してください。  
・`scan2`  
　　受信時間です。ミリ秒で指定してください。  
　 （推奨は`scan1=3000, scan2=2000`ですが決まりは無いです。）  
・`url`  
　　部屋情報データベスへのアドレスです。  
　　研究室内からは　`http://192.168.100.211/beacon_load.php`  
　　愛工大内のwifiからは　`http://192.168.54.167:60000/beacon_load.php`  
  
  
***  
## ビーコン情報の取得  
*ビーコン情報取得コード*  

    BeaconHolder beaconinfo = BeaconHolder.getInstance();
    for (int i = 0; i < beaconinfo.getTestString().length; i++) {
        stringArray[i] = beaconinfo.getTestString()[i];
    }
  
[0] はUUID、[1]はmajor、[2]はminor、[3]はrssi　です。

***  
## 部屋情報の取得  
データベースには**0:キー番号 1:棟名 2:部屋名 3:部屋番号 4:日時**の順番で入っています。  
入室判定がされたときにデータベースに書き込まれます。  
! 同じ部屋（ビーコン）は２回連続入室判定されないようになっています。!  
! テストの際は２つ以上のビーコンを用意することが望ましいです。!  
### (1)データベースから部屋情報を取得  
*部屋情報取得コード*  

    ContentResolver resolver = getContentResolver();
    stringArray = dBaccess.monitoring(resolver);
### (2)データベースの変更を監視  
*データベース変更監視コード*  

    ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            // 変更された時の処理を書く
        }
    };

***  
## その他  
###改善、変更予定  
・クラス、関数名の変更  
・入室判定関連はこれでいいのか  
　（精度はどうか、同じ部屋を２回続けて認識するかしないか、．．．）  
・退室判定をどうするか  
・データベース監視をバックグラウンドで行う方法  
