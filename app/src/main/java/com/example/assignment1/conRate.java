package com.example.assignment1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_rate);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        intent = new Intent(this, MainActivity.class);

        //mylist.add("something");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.textviewlayout, mylist);/*{
            @Override
            public View getView(int position, @androidx.annotation.Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(R.layout.)

                return view;
            }
        };*/

        myListView = findViewById(R.id.listOfCurr);
        myListView.setAdapter(arrayAdapter);

        inputSpin = findViewById(R.id.SelectSpinner);
        inputSpin.setAdapter(adapter);
        //dispatch();
        inputSpin.setOnItemSelectedListener(this);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rates_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.go_to_main :
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/

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
                output = (output.length() >= 10) ? output.substring(0,10) : output;
                while(output.endsWith("0")){ output = output.substring(0, output.length()-1); }
                String text = currency+" : ";
                arrayAdapter.add(currency + " : " + output);
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

