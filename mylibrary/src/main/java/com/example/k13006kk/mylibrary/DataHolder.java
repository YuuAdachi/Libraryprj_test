package com.example.k13006kk.mylibrary;

/**
 * Created by k13006kk on 2016/02/22.
 */
public class DataHolder {

    static DataHolder _instance = null;
    String test ="A";

    String[] stringArray = new String[3];
    //stringArray = new String[8];
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



    public String[] getTestString(){
        //return test;
        return stringArray;
    }

    public void setTestString(/*String txt*/String[] string){
        //this.test = txt;
        this.stringArray = string;
    }
}
