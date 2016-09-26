# Libraryprj_test
  
BLEビーコンをスキャンして部屋認識をするライブラリです  
利用できる機能は  
・ビーコンのスキャン（バックグラウンド動作可能）  
・スキャンしているビーコンの情報（UUID,major,minor等）の取得  
・入室判定（ビーコンスキャンで自動で行われます）  
・このライブラリを使用したアプリで部屋情報の共有  
  
! ライブラリを使用するには**Android 5.0**以上でお願いします  
! クラス名、関数名は変更予定です  
  
***  
## 受信開始  
**`beaconApplication.BeaconScan(resolver, bluetoothManager2, scan1,scan2,url);`**  
  
・scan1  
　　受信間隔です。ミリ秒で指定してください。  
・scan2  
　　受信時間です。ミリ秒で指定してください。  
