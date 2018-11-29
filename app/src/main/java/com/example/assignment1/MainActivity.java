package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.net.http.HttpResponseCache;
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
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //ConversionRates rates = new ConversionRates();
    Spinner inputSpin;
    Spinner outputSpin;
    TextView inputText;
    TextView outputText;
    String country ="";
    Intent intent;




    public Context getContext(){
        return MainActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getLocation();
        setContentView(R.layout.activity_main);
        ConversionRates.getRateFromEur();

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        intent = new Intent(this, conRate.class);
        inputSpin = findViewById(R.id.inputSpinner);
        outputSpin = findViewById(R.id.resultSpin);
        inputText = findViewById(R.id.input);
        outputText = findViewById(R.id.result);
        if(savedInstanceState != null)
            System.out.println("asd: "+ savedInstanceState.getString("TEST"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
            System.out.println("RESTORE: "+ savedInstanceState.getString("TEST"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.conRates :
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRates();
        System.out.println("START");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ey = "Bamboozle";
        System.out.println("DESTROY");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("RESUME");
        //if(country.length() > 0)
        //setCurrToLocation();
        //Toast.makeText(this, "RESUME", Toast.LENGTH_LONG).show();

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
        outState.putString("TEST", "THIS IS SAVED");
        System.out.println("SAVED");
    }

    void setRates(){
        String[] currencies = new String[] {"USD", "SEK", "EUR", "JPY", "KRW", "GBP", "CNY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        inputSpin.setAdapter(adapter);
        outputSpin.setAdapter(adapter);
        inputSpin.setOnItemSelectedListener(this);
        outputSpin.setOnItemSelectedListener(this);

    }

    void setCurrToLocation() {
        //country = "Korea";
        System.out.println("COUNTRY: "+country);
        switch (country){
            case "USA":
                inputSpin.setSelection(0);
                break;
            case "Sweden":
                inputSpin.setSelection(1);
                break;
            case "Japan":
                inputSpin.setSelection(3);
                break;
            case "Korea":
                inputSpin.setSelection(4);
                break;
            case "England":
                inputSpin.setSelection(5);
                break;
            case "China":
                inputSpin.setSelection(6);
                break;
            default:
                inputSpin.setSelection(2);
        }
        /*if (country.equals("Sweden")) {
            inputSpin.setSelection(1);
        }*/
    }
    public void keyboardInput(View view){
        System.out.println(view.getId());
        System.out.println(R.id.num1);
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
        if(output.length() > 14)
        {
            output = output.substring(0,14);
        }
        outputText.setText(output);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String output = ConversionRates.convert(inputSpin.getSelectedItem().toString(),outputSpin.getSelectedItem().toString(),inputText.getText().toString());
        if(output.length() > 14)
        {
            output = output.substring(0,14);
        }
        outputText.setText(output);

    }


    public void getLocation(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL apiUrl =  new URL("https://api.myip.com/");
                    try {
                        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                        InputStream response = connection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(response, "UTF-8");
                        JsonReader json = new JsonReader(reader);
                        json.beginObject();
                        String key;
                        while(json.hasNext()){
                            key = json.nextName();
                            if(key.equals("country")){

                                country = json.nextString();
                            }
                            else{
                                json.skipValue();
                            }
                        }
                        setCurrToLocation();
                    }
                    catch (Exception e){
                        Log.e("Connection", e.getMessage());
                    }
                }
                catch (MalformedURLException e){
                    Log.e("URL", e.getMessage());
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
