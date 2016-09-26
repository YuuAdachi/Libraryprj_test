# Libraryprj_test
  
BLEビーコンをスキャンして部屋認識をするライブラリです。  
利用できる機能は以下です。  
　・ビーコンのスキャン（バックグラウンド動作）  
　・スキャンしているビーコンの情報（UUID,major,minor等）の取得  
　・入室判定（ビーコンスキャンで自動で行われます）  
　・このライブラリを使用したアプリで部屋情報の共有  
  
! ライブラリを使用するには**Android 5.0**以上でお願いします。  
! クラス名、関数名は変更予定です。  
  
  
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
  
***  
## 部屋情報の取得  
### (1)データベースから部屋情報を取得  
### (2)データベースの変更を監視  
***  
## その他  
