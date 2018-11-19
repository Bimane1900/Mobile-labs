package com.example.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class conRate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView myListView;
    Spinner inputSpin;
    List<String> mylist = new ArrayList<>();
    String[] currencies = new String[] {"USD", "SEK", "EUR", "JPY", "KRW", "GBP", "CNY"};
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> adapter;
    ConversionRates rates = new ConversionRates();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_rate);

        mylist.add("something");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist );

        myListView = findViewById(R.id.listOfCurr);
        myListView.setAdapter(arrayAdapter);

        inputSpin = findViewById(R.id.SelectSpinner);
        inputSpin.setAdapter(adapter);
        dispatch();
    }

    void dispatch(){
        inputSpin.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //System.out.println("TRIGGGGGER");
        arrayAdapter.clear();
        for (String currency : currencies) {
            if(inputSpin.getSelectedItem().toString() != currency) {
                String output = rates.convert(inputSpin.getSelectedItem().toString(), currency, "1");
                arrayAdapter.add(currency + "   :   " + output);
            }
        }
        //mylist.add("A NEW THING");
        //arrayAdapter.add("hehe");
        //myListView.setAdapter(arrayAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

