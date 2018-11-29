package com.example.assignment1;

import android.app.Application;
import android.content.Context;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.Double.parseDouble;

public class ConversionRates {

    public static Hashtable<String, String> table = new Hashtable<String, String>();

    public static String convert(String currency, String currency2, String value){
        double input = (value.equals("")) ? 0 : parseDouble(value);
        if (currency.equals(currency2)){return String.valueOf(input);}

        else {
            input = input / parseDouble(table.get(currency));
            return convertFromUSD(input, currency2);
        }
    }

    public static String convertFromUSD(double input, String currency){
        input = input * parseDouble(table.get(currency));
        return String.valueOf(input);
    }

    public static void getRateFromEur(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL apiUrl =  new URL("http://data.fixer.io/api/latest?access_key=bbff8409662051ed5e8e4d2a99dc38e0");
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
                                    table.put(currency, String.valueOf(value));
                                }
                            }
                            else{
                                json.skipValue();
                            }
                        }
                        System.out.println("WHILE DONE");
                    }
                    catch (Exception e){
                        Log.e("Connection", e.getMessage());
                    }
                }
                catch (MalformedURLException e){
                    Log.e("URL", e.getMessage());
                }
                System.out.println("I AM IN OK");
            }
        });
        System.out.println("IM DONE OK");
    }
}
