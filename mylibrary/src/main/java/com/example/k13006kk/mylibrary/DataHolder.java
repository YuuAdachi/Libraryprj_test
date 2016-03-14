package com.example.k13006kk.mylibrary;

/**
 * Created by k13006kk on 2016/02/22.
 */
public class DataHolder {

    static DataHolder _instance = null;
    String test ="A";


    /**
     * 常にこのメソッドから呼び出すようにする
     *
     *
     * @return
     */

    static public DataHolder getInstance(){
        if(_instance==null){
            _instance = new DataHolder();
        }
        return _instance;
    }



    public String getTestString(){
        return test;
    }

    public void setTestString(String txt){
        this.test = txt;
    }
}
