package com.example.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static java.lang.Double.parseDouble;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ConversionRates rates = new ConversionRates();
    Spinner spin;
    Spinner spin2;
    EditText editText;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spin = findViewById(R.id.fromSpinner);
        spin2 = findViewById(R.id.toSpinner);
        editText = findViewById(R.id.currInput);
        textView = findViewById(R.id.result);
        //setRates();



    }

    @Override
    protected void onStart() {
        super.onStart();
        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                convert();
                //textView.setText( textView.getText().toString() + String.valueOf(keyCode));
                return false;
            }
        };
        editText.setOnKeyListener(onKeyListener);
        setRates();
    }

    void setRates(){
        String[] currencies = new String[] {"USD", "SEK", "EUR", "JPY", "KRW", "GBP", "CNY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        spin.setAdapter(adapter);
        spin2.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
        spin2.setOnItemSelectedListener(this);
    }


    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Spinner spin = findViewById(R.id.fromSpinner);
        /*String currency = spin.getSelectedItem().toString();
        double input = (editText.getText().toString().equals("")) ? 0 : parseDouble(editText.getText().toString());
        switch(currency){
            case "SEK":
                input = input*rates.SEKtoUSD;
                textView.setText(String.valueOf(input));
                System.out.println(rates.SEKtoUSD);

                break;
            case "JPY":
                System.out.println(rates.JPYtoUSD);
                break;
        }*/
        convert();
    }


    void convert(){
        String currency = spin.getSelectedItem().toString();
        double input = (editText.getText().toString().equals("")) ? 0 : parseDouble(editText.getText().toString());
        switch(currency){
            case "SEK":
                input = input*rates.SEKtoUSD;
                textView.setText(String.valueOf(input));
                System.out.println(rates.SEKtoUSD);

                break;
            case "JPY":
                System.out.println(rates.JPYtoUSD);
                break;
        }
    }
    //@Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
