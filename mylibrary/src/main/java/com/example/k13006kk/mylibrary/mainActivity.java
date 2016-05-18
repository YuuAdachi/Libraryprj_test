package com.example.k13006kk.mylibrary;

/**
 * Created by k13006kk on 2016/02/22.
 */

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class mainActivity {


    //String room_uuid;

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this, BeaconApplication.class));

    }
    */

    ScanListener listener = null;


    public void data_com(String url) {


        final String[] beacon_info; //= new String[3];

        //final String[] stringArray = new String[10];


        final String[] stringArray = new String[1];


        BeaconHolder holder = BeaconHolder.getInstance();
        beacon_info = holder.getTestString();

        //final BeaconinfoHolder database = BeaconinfoHolder.getinstance();

        /*
        TextView tv = (TextView) findViewById(R.id.uuid);//テスト用
        tv.setText(room_uuid);//テスト用
        */

        AsyncHttpClient client = new AsyncHttpClient(); //通信準備

        final RequestParams params = new RequestParams(); //リクエストパラメータ
        params.put("uuid", beacon_info[0]); //送るパラメータ1
        String server_url = url;//"http://192.168.100.211:808/beacon_server.php""""http://192.168.51.180:808/beacon_server.php

        client.get(server_url, params, new AsyncHttpResponseHandler() {

            String[] stringArray2 = new String[10];
            BeaconinfoHolder database = BeaconinfoHolder.getinstance();

            @Override
            public void onSuccess(/*View view, */int i, Header[] headers, byte[] bytes) {
                //InputStream input;
                try {

                    String json = new String(bytes);
                    System.out.println(json);
                    JSONObject jsonObject = new JSONObject(json);


                    //System.out.println(jsonObject.getString("room_number")); // "fuga"
                    //System.out.println(jsonObject.getString("room_name"));

                    /*
                    System.out.println(jsonObject.getString("building_id"));
                    System.out.println(jsonObject.getString("building_name"));
                    System.out.println(jsonObject.getString("roomnumber_id"));
                    System.out.println(jsonObject.getString("roomnumber_no"));
                    System.out.println(jsonObject.getString("room_id"));
                    System.out.println(jsonObject.getString("beacon_identifier"));
                    System.out.println(jsonObject.getString("room_name"));
                    */

                    //Log.d("Server2",jsonObject.getString("room_number") + "," + jsonObject.getString("room_name"));

                    //Log.d("!!!!!!!",jsonObject.getString("room_number") + "," + jsonObject.getString("building_id") + "," + jsonObject.getString("building_name") + "," + jsonObject.getString("roomnumber_id") + "," + jsonObject.getString("roomnumber_no") + "," + jsonObject.getString("room_id") + "," + jsonObject.getString("beacon_identifier") + "," + jsonObject.getString("room_name"));

                    //String[] stringArray2 = new String[7];


                    stringArray2[0] = jsonObject.getString("building_id");
                    stringArray2[1] = jsonObject.getString("building_name");
                    stringArray2[2] = jsonObject.getString("roomnumber_id");
                    stringArray2[3] = jsonObject.getString("roomnumber_no");
                    stringArray2[4] = jsonObject.getString("room_id");
                    stringArray2[5] = jsonObject.getString("beacon_identifier");

                    stringArray[0] = "ABCDEFG";//jsonObject.getString("room_name");


                    stringArray2[7] = beacon_info[0];
                    stringArray2[8] = beacon_info[1];
                    stringArray2[9] = beacon_info[2];


                    database.setData(stringArray2);

                    //System.out.println(stringArray[6]);
                    /*
                    TextView tv = (TextView) findViewById(R.id.room_id);//？を指定
                    tv.setText(jsonObject.getString("room_number"));//？を変更
                    TextView tv2 = (TextView) findViewById(R.id.room_name);//？を指定
                    tv2.setText(jsonObject.getString("name"));//？を変更
                    */

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                }
                /*
                TextView tv = (TextView) findViewById(R.id.myTextView);//テスト用
                tv.setText("成功");//テスト用
                */
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                /*
                TextView tv = (TextView) findViewById(R.id.myTextView);//
                tv.setText("失敗");//テスト用
                */
                //jsonObject.getString("room_name");
                //Log.d("TEST", stringArray[6]);


                stringArray2[0] = "a";
                stringArray2[1] = "b";
                stringArray2[2] = "c";
                stringArray2[3] = "d";
                stringArray2[4] = "e";
                stringArray2[5] = "f";


                stringArray[0] = "ABCDEFG";//jsonObject.getString("room_name");

                //Log.d("TEST", stringArray[0]);


                stringArray2[7] = beacon_info[0];
                stringArray2[8] = beacon_info[1];
                stringArray2[9] = beacon_info[2];

                database.setData(stringArray2);

            }


        });

        //AsyncHttpResponseHandler http = new AsyncHttpResponseHandler();

        //stringArray[6] = stringArray2[6];
        Log.d("TEST", stringArray[0]);

        //stringArray[7] = beacon_info[0];
        //stringArray[8] = beacon_info[1];
        //stringArray[9] = beacon_info[2];

        //return stringArray;


    }

    public void setListener(ScanListener listener){
        this.listener=listener;
    }

}
