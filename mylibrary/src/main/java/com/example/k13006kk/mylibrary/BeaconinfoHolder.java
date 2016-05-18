package com.example.k13006kk.mylibrary;

/**
 * Created by k13006kk on 2016/03/21.
 */

// ライブラリを使う側へ提供するクラス
public class BeaconinfoHolder {

    static BeaconinfoHolder _instance = null;
    //String test ="A";

    static String[] stringArray = new String[11];

    /**
     * 常にこのメソッドから呼び出すようにする
     *
     *
     * @return
     */

    static public BeaconinfoHolder getinstance(){
        if(_instance==null){
            _instance = new BeaconinfoHolder();
        }
        return _instance;
    }

    public String[] getData(){
        //return test;

        //Log.d("TEST", stringArray[6]);

        return stringArray;

    }

    public void setData(String[] string){
        //this.test = txt;
        stringArray = string;

        //Log.d("TEST", stringArray[6]);

    }

}

