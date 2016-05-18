package com.example.k13006kk.mylibrary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.EventListener;

/**
 * Created by k13006kk on 2016/03/25.
 */
public interface ScanListener extends EventListener {

    //Beaconがスキャンされたことを通知する
    public void scanEvent(String url);



}