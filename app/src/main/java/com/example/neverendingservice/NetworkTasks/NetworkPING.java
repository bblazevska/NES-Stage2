package com.example.neverendingservice.NetworkTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkPING extends AsyncTask<Void,Void,String> {

    public static String ping;
    public String host;
    public int packetSize;
    public int jobPeriod;
    public BufferedReader input;
    public int count;
    public  String line;

    @Override
    protected String doInBackground(Void... voids) {

        try {
            String jsonData = getJSON.getBackendJson();
            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i =0; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                host = jsonObject.getString("host");
                count = jsonObject.getInt("count");
                packetSize = jsonObject.getInt("packetSize");


                try{
                    String pingCmd = "ping -s "+ packetSize + " -c "+ count + " " + host;
                    Runtime runtime = Runtime.getRuntime();
                    Process process = runtime.exec(pingCmd);
                    BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line ;
                    while ((line = input.readLine()) != null){
                        Log.i("PINGLINE", "doInBackground: " + line);
                        ping += line;

                    }
                    input.close();
                    Log.i("PING", "doInBackground: " + ping);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ping;
    }
}
