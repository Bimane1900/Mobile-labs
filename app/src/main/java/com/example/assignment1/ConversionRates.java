package com.example.assignment1;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyPair;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.Double.parseDouble;

public class ConversionRates {

    public static Hashtable<String, String> rates = new Hashtable<String, String>();
    public static Hashtable<String, String> countryCodes = new Hashtable<String, String>();

    public static String convert(String currency, String currency2, String value){
        double input = (value.equals("")) ? 0 : parseDouble(value);
        if (currency.equals(currency2)){return value;}

        else {
            input = input / parseDouble(rates.get(currency));
            return convertFromEUR(input, currency2);
        }
    }

    public static String convertFromEUR(double input, String currency){
        String result = "";
        DecimalFormat deci = new DecimalFormat();
        deci.setMaximumFractionDigits(13);
        input = input * parseDouble(rates.get(currency));
        if(input > 1000000000) result = String.format("%.2fB", input/ 1000000000.0);
        else result = deci.format(input);
        //DecimalFormat deci = new DecimalFormat();
        //deci.setMaximumFractionDigits(13);
        //String.format("%.2fM", theNumber/ 1000000.0);
        return result;
    }

    public static void getCountryCurrencies(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL apiUrl =  new URL("http://data.fixer.io/api/symbols?access_key=bbff8409662051ed5e8e4d2a99dc38e0");
                    try {
                        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                        InputStream response = connection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(response, "UTF-8");
                        JsonReader json = new JsonReader(reader);
                        json.beginObject();
                        String key, currency, country;
                        while(json.hasNext()){
                            key = json.nextName();
                            if(key.equals("symbols")){
                                json.beginObject();
                                while(json.hasNext()){
                                    currency = json.nextName();
                                    country = json.nextString();
                                    //countryCodes.put(country, currency);
                                    //System.out.println("currency: "+ currency +", country: "+ country);
                                }
                            }
                            else{
                                json.skipValue();
                            }
                        }
                        System.out.println("Countries added");
                    }
                    catch (Exception e){
                        Log.e("Con fixer io codes", e.getMessage());
                    }
                }
                catch (MalformedURLException e){
                    Log.e("URL fixer io codes", e.getMessage());
                }
            }
        });
    }

    public static void getRateFromEur(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL apiUrl =  new URL("http://data.fixer.io/api/latest?access_key=27071389e13bc478b998c2da0dc04208");
                    try {
                        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                        InputStream response = connection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(response, "UTF-8");
                        JsonReader json = new JsonReader(reader);
                        json.beginObject();
                        String key, currency;
                        double value;
                        while(json.hasNext()){
                            key = json.nextName();
                            if(key.equals("rates")){
                                json.beginObject();
                                while(json.hasNext()){
                                    currency = json.nextName();
                                    value = json.nextDouble();
                                    rates.put(currency, String.valueOf(value));
                                }
                            }
                            else{
                                json.skipValue();
                            }
                        }
                        /*for (Map.Entry<String, String> entry : rates.entrySet()) {
                            String lol = entry.getKey();
                            String val = entry.getValue();
                            System.out.println (" IN API Key: " + lol + " Value: " + val);
                        }*/
                        System.out.println("Rates updated");
                    }
                    catch (Exception e){
                        Log.e("Connection fixer io", e.getMessage());
                    }
                }
                catch (MalformedURLException e){
                    Log.e("URL fixer io", e.getMessage());
                }
            }
        });
    }

    static void updateRateTimer(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getRateFromEur();
            }
        },60000, 360000);
    }


}
