package com.example.k13006kk.mylibrary;

import android.database.Cursor;

/**
 * Created by k13006kk on 2016/04/26.
 */
public class DbinfoHolder {

    static DbinfoHolder _instance = null;
    //String test ="A";

    String[] stringArray = new String[4];

    //Cursor c;
    /**
     * 常にこのメソッドから呼び出すようにする
     *
     *
     * @return
     */

    static public DbinfoHolder getInstance(){
        if(_instance==null){
            _instance = new DbinfoHolder();
        }
        return _instance;
    }



    public String[] getdb(){
        //return test;
        return stringArray;
    }

    public void setdb(String[] strings){
        //this.test = txt;
        this.stringArray = strings;
    }
}
