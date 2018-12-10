package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //ConversionRates rates = new ConversionRates();
    Spinner inputSpin;
    Spinner outputSpin;
    TextView inputText;
    TextView outputText;
    String country ="";
    String code = "EUR";
    Intent intent;
    ArrayAdapter<String> adapter;
    countryTasker getCountry = new countryTasker();
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        intent = new Intent(this, conRate.class);
        inputSpin = findViewById(R.id.inputSpinner);
        outputSpin = findViewById(R.id.resultSpin);
        inputText = findViewById(R.id.input);
        outputText = findViewById(R.id.result);
        data = this.getSharedPreferences("currency", MODE_PRIVATE);
        if(isNetworkAvailable(this)){
            ConversionRates.getRateFromEur();
            getCountry.execute();
            System.out.println("Online");
        }
        else{
            readRateFromCache();
            System.out.println("Offline");
        }

        ConversionRates.updateRateTimer();
        //ConversionRates.getCountryCurrencies();

        setRates();




    }

    void saveToCache(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences.Editor currencyEdit = data.edit();
                Hashtable<String, String> clone = (Hashtable<String, String>) ConversionRates.rates.clone();
                for (Map.Entry<String, String> entry : clone.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    currencyEdit.putString(key, value);
                }
                currencyEdit.apply();
            }
        },0, 60000);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void readRateFromCache() {
        try{
            for (Map.Entry<String, ?> entry : data.getAll().entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                System.out.println ("Key: " + key + " Value: " + value);
                ConversionRates.rates.put(key,value);
            }
        }
        catch (Exception e){
            System.out.println("reading: "+ e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.conRates :
                intent.putExtra("countryCode", code);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        saveToCache();
        super.onStart();
        System.out.println("START");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROY");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("RESTART");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("RESUME");
        ConversionRates.getRateFromEur();

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("STOP");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("SAVED");
    }

    void setRates(){
        String[] currencies = new String[] {"USD", "SEK", "EUR", "JPY", "KRW", "GBP", "CNY"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        inputSpin.setAdapter(adapter);
        outputSpin.setAdapter(adapter);
        inputSpin.setOnItemSelectedListener(this);
        outputSpin.setOnItemSelectedListener(this);
        inputSpin.setSelection(adapter.getPosition("EUR"));
    }

    void setInputCurrency(){
        for (Map.Entry<String, String> entry : ConversionRates.rates.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            System.out.println ("Key: " + key + " Value: " + value);
        }
        for (Map.Entry<String, String> entry : ConversionRates.countryCodes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            System.out.println ("Key: " + key + " Value: " + value);
        }
        System.out.println("setInputCUrrency: "+ ConversionRates.countryCodes.get(country) + " country: "+country);
        String code;
        try{
            code = ConversionRates.countryCodes.get(country);
            System.out.println("code: "+code);
            adapter.add(code);
            int pos = adapter.getPosition(code);
            inputSpin.setSelection(pos);
        }
        catch(Exception e){
            inputSpin.setSelection(adapter.getPosition("EUR"));
            System.out.println("Currency not found");
            System.out.println("setInputCurreny: "+e.getMessage());
        }
    }

    void setCurrToLocation(String country) {
        System.out.println("COUNTRY: "+country);
        switch (country){
            case "USA":
                code = "USD";
                break;
            case "Sweden":
                code = "SEK";
                break;
            case "Japan":
                code = "JPY";
                break;
            case "Korea":
                code = "KRW";
                break;
            case "England":
                code = "GBP";
                break;
            case "China":
                code = "CNY";
                break;
            default:
                code = "EUR";
        }

        int pos = adapter.getPosition(code);
        inputSpin.setSelection(pos);
    }
    public void keyboardInput(View view){
        CharSequence textString = inputText.getText();
        switch (view.getId()) {
            case R.id.num1:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"1";
                inputText.setText(textString);
                break;
            case R.id.num2:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"2";
                inputText.setText(textString);
                break;
            case R.id.num3:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"3";
                inputText.setText(textString);
                break;
            case R.id.num4:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"4";
                inputText.setText(textString);
                break;
            case R.id.num5:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"5";
                inputText.setText(textString);
                break;
            case R.id.num6:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"6";
                inputText.setText(textString);
                break;
            case R.id.num7:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"7";
                inputText.setText(textString);
                break;
            case R.id.num8:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"8";
                inputText.setText(textString);
                break;
            case R.id.num9:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"9";
                inputText.setText(textString);
                break;
            case R.id.num0:
                textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+"0";
                inputText.setText(textString);
                break;
            case R.id.numdot:
                if(!textString.toString().contains(".") && textString.length() != 0){
                    textString = (textString.length() >= 14) ? textString.subSequence(0, 14) : textString+".";
                    inputText.setText(textString);
                }
                break;
            case R.id.backspace:
                if(textString.length() != 0)
                inputText.setText(inputText.getText().subSequence(0,inputText.length()-1));
                break;
        }
        String output = ConversionRates.convert(inputSpin.getSelectedItem().toString(),outputSpin.getSelectedItem().toString(),inputText.getText().toString());
        if(output.length() > 14) { output = output.substring(0,14); }
        if(output.endsWith(".") || output.endsWith(",")) { output = output.substring(0,output.length()-1); }

        outputText.setText(output);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try{
            String output = ConversionRates.convert(inputSpin.getSelectedItem().toString(),outputSpin.getSelectedItem().toString(),inputText.getText().toString());
            if(output.length() > 14) { output = output.substring(0,14); }
            if(output.endsWith(".") || output.endsWith(",")) { output = output.substring(0,output.length()-1); }
            outputText.setText(output);
        }
        catch (Exception e){
            System.out.println("onItemSelected: "+e);
        }
    }

    public void getLocation(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL apiUrl = new URL("https://api.myip.com/");
                    try {
                        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                        InputStream response = connection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(response, "UTF-8");
                        JsonReader json = new JsonReader(reader);
                        json.beginObject();
                        String key;
                        while (json.hasNext()) {
                            key = json.nextName();
                            if (key.equals("country")) {

                                country = json.nextString();
                            } else {
                                json.skipValue();
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Con myip", e.getMessage());
                    }
                } catch (MalformedURLException e) {
                    Log.e("URL myip", e.getMessage());
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class countryTasker extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String country = "";
            try {
                URL apiUrl = new URL("https://api.myip.com/");
                try {
                    HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                    InputStream response = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(response, "UTF-8");
                    JsonReader json = new JsonReader(reader);
                    json.beginObject();
                    String key;
                    while (json.hasNext()) {
                        key = json.nextName();
                        if (key.equals("country")) {

                            country = json.nextString();
                        } else {
                            json.skipValue();
                        }
                    }
                } catch (Exception e) {
                    Log.e("Con myip", e.getMessage());
                }
            } catch (MalformedURLException e) {
                Log.e("URL myip", e.getMessage());
            }
            return country;
        }

        @Override
        protected void onPostExecute(String response) {
            country = response;
            setCurrToLocation(country);
        }
    }


}
