package com.sjcoders.covid_19trackerapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonTask extends AsyncTask<String,Void,String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("PREEXE","HERE");
        MainActivity.progress.setMessage("Loading COVID-19 Data....");
        MainActivity.progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        MainActivity.progress.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("POSTEXE",s+MainActivity.callFromGlobal);
        MainActivity.progress.cancel();
        ArrayList<String> data = MainActivity.getGlobalData(s);
        if(data!=null && MainActivity.callFromGlobal)
            MainActivity.setFields(data);
        else if(data!=null && !MainActivity.callFromGlobal)
            MainActivity.countryFields(data);

    }

    @Override
    protected String doInBackground(String... urls) {

        try {
            String result="";
            URL uRl = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) uRl.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();
            while(data!=-1){
                char c = (char) data;
                result+=c;
                data = reader.read();
            }
            return result;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "failed";
    }
}
