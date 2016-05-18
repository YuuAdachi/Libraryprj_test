package com.example.k13006kk.mylibrary;


/**
 * Created by k13006kk on 2016/02/22.
 */
public class BeaconHolder {

    static BeaconHolder _instance = null;
    //String test ="A";

    String[] stringArray = new String[4];
    //stringArray = new String[8];
    /**
     * 常にこのメソッドから呼び出すようにする
     *
     *
     * @return
     */

    static public BeaconHolder getInstance(){
        if(_instance==null){
            _instance = new BeaconHolder();
        }
        return _instance;
    }



    public String[] getTestString(){
        //return test;
        return stringArray;
    }

    public void setTestString(String[] string){
        //this.test = txt;
        this.stringArray = string;
    }
}
