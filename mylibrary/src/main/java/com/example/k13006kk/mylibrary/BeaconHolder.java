package com.example.k13006kk.mylibrary;


import android.util.Log;

/**
 * Created by k13006kk on 2016/02/22.
 */
public class BeaconHolder {

    static BeaconHolder _instance = null;

    String[] stringArray = new String[4];
    //stringArray = new String[8];


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
        for (int i = 0; i < string.length; i++) {   //(3)
            this.stringArray[i] = string[i];
        }
        //this.stringArray = string;
        //Log.d("scan2",stringArray[0]+","+stringArray[1]+","+stringArray[2]+","+stringArray[3]);
    }
}
