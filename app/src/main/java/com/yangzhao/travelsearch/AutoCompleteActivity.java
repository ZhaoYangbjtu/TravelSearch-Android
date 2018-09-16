package com.yangzhao.travelsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.yangzhao.travelsearch.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteActivity extends AppCompatActivity {

    public static final String TAG = "AutoCompleteActivity";
    private static final int AUTO_COMP_REQ_CODE = 2;

    protected GeoDataClient geoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);

        //Toolbar tb = findViewById(R.id.toolbar);
        //setSupportActionBar(tb);
        //tb.setSubtitle("Auto Complete");

        //set place types spinner data from array
        Spinner placeType = findViewById(R.id.place_type);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.placeTypes, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeType.setAdapter(spinnerAdapter);

        //Set adapter for autocomplete text view
        AutoCompleteTextView searchPlace = findViewById(R.id.search_place);

        CustomAutoCompleteAdapter adapter =  new CustomAutoCompleteAdapter(this);
        searchPlace.setAdapter(adapter);
        //searchPlace.setOnItemClickListener(onItemClickListener);

    }
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Toast.makeText(AutoCompleteActivity.this,
                            "selected place "
                                    + ((com.yangzhao.travelsearch.Place)adapterView.
                                    getItemAtPosition(i)).getPlaceText()
                            , Toast.LENGTH_SHORT).show();
                    //do something with the selection
                    //searchScreen();
                }
            };

    public void searchScreen(){
        Intent i = new Intent();
        i.setClass(this, AutoCompleteActivity.class);
        startActivity(i);
    }
}