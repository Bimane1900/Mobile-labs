package com.example.assignment1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
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
    Spinner inputSpin;
    Spinner outputSpin;
    TextView inputText;
    TextView outputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, conRate.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputSpin = findViewById(R.id.inputSpinner);
        outputSpin = findViewById(R.id.resultSpin);
        inputText = findViewById(R.id.input);
        outputText = findViewById(R.id.result);
        //setRates();
        //startActivity(intent);
    }

   /*     <Spinner
    android:id="@+id/fromSpinner"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    tools:layout_editor_absoluteX="8dp"
    tools:layout_editor_absoluteY="77dp"
    android:layout_marginEnd="32dp"
    android:layout_marginStart="32dp"
    android:layout_marginTop="16dp"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"/>

    <EditText
    android:inputType="numberDecimal"
    android:id="@+id/currInput"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:layout_constraintTop_toBottomOf="@id/fromSpinner"/>
*/
    @Override
    protected void onStart() {
        super.onStart();
        setRates();
    }

    void setRates(){
        String[] currencies = new String[] {"USD", "SEK", "EUR", "JPY", "KRW", "GBP", "CNY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        inputSpin.setAdapter(adapter);
        outputSpin.setAdapter(adapter);
        inputSpin.setOnItemSelectedListener(this);
        outputSpin.setOnItemSelectedListener(this);
    }

    public void keyboardInput(View view){
        System.out.println(view.getId());
        System.out.println(R.id.num1);
        CharSequence textString = inputText.getText();
        switch (view.getId()) {
            case R.id.num1:
                inputText.setText(textString+"1");
                break;
            case R.id.num2:
                inputText.setText(textString+"2");
                break;
            case R.id.num3:
                inputText.setText(textString+"3");
                break;
            case R.id.num4:
                inputText.setText(textString+"4");
                break;
            case R.id.num5:
                inputText.setText(textString+"5");
                break;
            case R.id.num6:
                inputText.setText(textString+"6");
                break;
            case R.id.num7:
                inputText.setText(textString+"7");
                break;
            case R.id.num8:
                inputText.setText(textString+"8");
                break;
            case R.id.num9:
                inputText.setText(textString+"9");
                break;
            case R.id.num0:
                inputText.setText(textString+"0");
                break;
            case R.id.numdot:
                if(!textString.toString().contains(".") && textString.length() != 0)
                inputText.setText(textString+".");
                break;
            case R.id.backspace:
                if(textString.length() != 0)
                inputText.setText(inputText.getText().subSequence(0,inputText.length()-1));
                break;
        }
        String output = rates.convert(inputSpin.getSelectedItem().toString(),outputSpin.getSelectedItem().toString(),inputText.getText().toString());
        outputText.setText(output);
    }


    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String output = rates.convert(inputSpin.getSelectedItem().toString(),outputSpin.getSelectedItem().toString(),inputText.getText().toString());
        outputText.setText(output);
    }


    /*void convert(String currency, String currency2, String value){
        //String currency = inputSpin.getSelectedItem().toString();
        //String currency2 = outputSpin.getSelectedItem().toString();
        double input = (value.equals("")) ? 0 : parseDouble(value);
        if (currency == currency2){outputText.setText(String.valueOf(input));}
        else {
            switch (currency) {
                case "SEK":
                    input = input * rates.SEKtoUSD;
                    convertFromUSD(input);
                    System.out.println(rates.SEKtoUSD);
                    break;
                case "GBP":
                    input = input * rates.GBPtoUSD;
                    convertFromUSD(input);
                    break;
                case "EUR":
                    input = input * rates.EURtoUSD;
                    convertFromUSD(input);
                    break;
                case "CNY":
                    input = input * rates.CNYtoUSD;
                    convertFromUSD(input);
                    break;
                case "JPY":
                    input = input * rates.JPYtoUSD;
                    convertFromUSD(input);
                    break;
                case "KRW":
                    input = input * rates.KRWtoUSD;
                    convertFromUSD(input);
                    break;
                default:
                    convertFromUSD(input);
                    break;

            }
        }
    }*/

    /*public String convertFromUSD(double input, String currency){
        //String currency = outputSpin.getSelectedItem().toString();
        switch (currency){
            case "SEK":
                input = input*rates.USDtoSEK;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
                //break;
            case "GBP":
                input = input*rates.USDtoGBP;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
                //break;
            case "EUR":
                input = input*rates.USDtoEUR;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
                //break;
            case "CNY":
                input = input*rates.USDtoCNY;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
                //break;
            case "JPY":
                input = input*rates.USDtoJPY;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
                //break;
            case "KRW":
                input = input*rates.USDtoKRW;
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
                //break;
            default:
                //outputText.setText(String.valueOf(input));
                return String.valueOf(input);
                //break;
        }
    }*/
    //@Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
