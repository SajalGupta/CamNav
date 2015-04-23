package com.example.sajal.camnav;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.List;

public class DirectionFetcher extends Service {

    public class MyLocalBinder extends Binder {
        DirectionFetcher getDirectionFetcher (){
            return DirectionFetcher.this;
        }
    }


    public static final String BROADCAST_ACTION_DIRECTION = "Hello World";
    String distance;
    String expectedDuration;
    String startAddress;
    String endAddress;
    ParsingJson task;
    HandleDirectionAPI obj;
    LatLng param1;
    LatLng param2;
    Handler handler;
    public static List<LatLng> latLngs;
    public LatLng[] stepData;
    final IBinder mServiceBinder = new MyLocalBinder();
    public DirectionFetcher() {
    }




    public int datMagic(final LatLng param1, final LatLng param2) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.i("DirectionFetcher","Service Thread Started");


                Log.i("DirectionService","inside thread");
                String startingUrl = "https://maps.googleapis.com/maps/api/directions/json?";
                String origins = "&origin=" + param1.latitude + "," + param1.longitude;
                String destinations = "&destination=" + param2.latitude + "," + param2.longitude;
                String modes = "&mode=walking";
                String key = "&key=AIzaSyCxlMg0r_bb0R07g6D0lB2dBT9lijsTB-0";
                String finalURL = startingUrl + origins + destinations + modes + key;
                Log.i("FINAL URL ", finalURL);

                task = new ParsingJson();
                task.execute(finalURL);


            }

        };
        Thread mThread = new Thread(r);
        mThread.start();
        return 0;
    }

    public static List<LatLng> getLatLngs() {
        return latLngs;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("DirectionFetcher","in OnBind");
        Bundle data = intent.getExtras();
        if(data!=null) {
            Log.i("DirectionFetcher","got data in bundle :D");
            param1 = (LatLng) data.get("origin");
            param2 = (LatLng) data.get("destination");
            datMagic(param1,param2);
        }

        return mServiceBinder;
    }

    private class ParsingJson extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {
            String encodedPoints = obj.getEncodedPoints();
            latLngs = PolyUtil.decode(encodedPoints);
            distance = obj.getDistance();
            expectedDuration = obj.getDuration();
            startAddress= obj.getStartAddress();
            endAddress = obj.getEndAddress();
            stepData = obj.getStepData();
            Log.i("Async","Now in PostExecute");
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i("Async",params[0]);
            obj = new HandleDirectionAPI(params[0]);
            obj.fetchJSON();
            long futureTime = System.currentTimeMillis()+6000;
            while(System.currentTimeMillis()<futureTime){
                synchronized (this){
                    try{
                        wait(futureTime-System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            Log.i("Async","Reached end of doInBackground");

            return null;
        }
    }



}
