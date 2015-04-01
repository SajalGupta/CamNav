package com.example.sajal.camnav;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleDirectionAPI  {

    private String urlString;
    private String encodedPoints;
   // public static boolean parsingComplete;
    private String distance;
    private String duration;
    private String startAddress;
    private String endAddress;
    private LatLng[] stepData;

    public LatLng[] getStepData() {
        return stepData;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public String getDuration() {
        return duration;
    }

    public String getDistance() {
        return distance;
    }

    public String getEncodedPoints() {
        return encodedPoints;
    }

    public HandleDirectionAPI(String url) {
        this.urlString = url;
    }
    public void fetchJSON(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data);
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {


        try {
            JSONObject reader = new JSONObject(in);

            JSONArray sys  = reader.getJSONArray("routes");
            JSONObject sys2;
            encodedPoints = sys.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
            Log.i("JsonDATA",encodedPoints);
            distance = sys.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
            duration = sys.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");
            startAddress = sys.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getString("start_address");
            endAddress = sys.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getString("end_address");
            stepData = new LatLng[sys.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").length()];
            for(int i=0;i<sys.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").length();i++){
                stepData[i] = new LatLng(sys.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(i).getJSONObject("start_location").getDouble("lat"),sys.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(i).getJSONObject("start_location").getDouble("lng"));
                Log.i("StepData",String.valueOf(stepData[i].latitude)+"  "+String.valueOf(stepData[i].longitude));
            }








        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
