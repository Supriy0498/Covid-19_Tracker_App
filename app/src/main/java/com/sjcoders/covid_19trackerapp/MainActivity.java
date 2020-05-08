package com.sjcoders.covid_19trackerapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public static TextView tv1,tv2,tv3,textView5,textView6,textView7,tvCountryName,tv4,tv5,tv6;
    public static ProgressDialog progress ;
    public static Button button;
    public static EditText editText;
    public static ArrayList<String> data;
    public static boolean  callFromGlobal=true;
    JsonTask jsonTask;
    public static String countryName="";

    public void getCountryData(View view) {
        callFromGlobal = false;
        countryName = "";
        countryName = editText.getText().toString();
        InputMethodManager imgr = (InputMethodManager)  getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
        if (countryName.trim().equals("")){
            Toast.makeText(this, "Please Enter the country name !!", Toast.LENGTH_SHORT).show();
            editText.getText().clear();
        }
        else {
            countryName = countryName.toUpperCase();
            if (countryName.equals("UNITED STATES OF AMERICA") || countryName.equals("AMERICA"))
                countryName = "USA";
            else if (countryName.equals("GREAT BRITAIN") || countryName.equals("ENGLAND"))
                countryName = "UNITED KINGDOM";
            editText.getText().clear();
            Log.i("Country Name", countryName);
            jsonTask = new JsonTask();
            jsonTask.execute("https://covid19.mathdro.id/api/countries/" + countryName);
        }
    }

    public void refresh(View view){
        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        callFromGlobal=true;
        jsonTask = new JsonTask();
        jsonTask.execute("https://covid19.mathdro.id/api");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        tvCountryName= findViewById(R.id.tvCountryName);
        tv4.setVisibility(View.INVISIBLE);
        tv5.setVisibility(View.INVISIBLE);
        tv6.setVisibility(View.INVISIBLE);
        tvCountryName.setVisibility(View.INVISIBLE);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        progress = new ProgressDialog(this);
        jsonTask = new JsonTask();
            jsonTask.execute("https://covid19.mathdro.id/api");

    }

    public static void countryFields(ArrayList<String> countryData)
    {
        Log.i("COUNTRY CASE",countryData.get(0));
        Log.i("COUNTRY DEATH",countryData.get(1));
        Log.i("COUNTRY RECO",countryData.get(2));
        tv4.setVisibility(View.VISIBLE);
        tv5.setVisibility(View.VISIBLE);
        tv6.setVisibility(View.VISIBLE);
        tvCountryName.setVisibility(View.VISIBLE);
        tvCountryName.setText(countryName);
        textView5.setText(countryData.get(0));
        textView6.setText(countryData.get(1));
        textView7.setText(countryData.get(2));
    }

    public static void setFields(ArrayList<String> arrayList)
    {
        MainActivity.tv1.setText(arrayList.get(0));
        MainActivity.tv2.setText(arrayList.get(1));
        MainActivity.tv3.setText(arrayList.get(2));

    }

    public static ArrayList<String> getGlobalData(String s){
        ArrayList<String> data = new ArrayList<>();
        Log.i("JSON",s);
        String cases = "", totalDeaths = "" , totalRecovered = "";
        try {
            JSONObject BigJson = new JSONObject(s);
            JSONObject confirmed = BigJson.getJSONObject("confirmed");
            JSONObject recovered = BigJson.getJSONObject("recovered");
            JSONObject deaths = BigJson.getJSONObject("deaths");
            Log.i("G Confirmed", String.valueOf(confirmed));
            Log.i("G Recovered", String.valueOf(recovered));
            Log.i("G Deaths", String.valueOf(deaths));
            cases = confirmed.getString("value");
            totalDeaths = deaths.getString("value");
            totalRecovered = recovered.getString("value");
            data.add(cases);
            data.add(totalDeaths);
            data.add(totalRecovered);
            return data;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
