package com.example.k13006kk.mylibrary;

/**
 * Created by k13006kk on 2016/03/27.
 */
public class ScanNotify {

    private ScanListener listener = null;

    public void scanCheck(String url){
        if(this.listener != null){

            //スキャンされたことを通知する
            listener.scanEvent(url);

        }
    }

    //リスナーを追加する
    public void setListener(ScanListener listener){
        this.listener = listener;
    }

    //リスナーを削除する
    public void removeListener(){
        this.listener = null;
    }
}
