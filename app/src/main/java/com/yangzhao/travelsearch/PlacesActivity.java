package com.yangzhao.travelsearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yangzhao.travelsearch.Adapter.PlacesAdapter;
import com.yangzhao.travelsearch.Bean.Place;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlacesActivity extends AppCompatActivity {
    private ArrayList<Place> placeList= new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private TextView noResults;
    private String next_token;
    private Button next,previous;
    private ArrayList<ArrayList<Place>> allPlaces=new ArrayList<ArrayList<Place>>();
    private static  int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        noResults=(TextView) findViewById(R.id.noResults) ;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search results");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        next=(Button) findViewById(R.id.next);
        previous=(Button) findViewById(R.id.previous);

        initPlaces();

        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PlacesAdapter adapter = new PlacesAdapter(placeList,getBaseContext());
        recyclerView.setAdapter(adapter);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(next_token!=null&&!next_token.equals("")){
                    requestNextPage();
                    i++;
                    previous.setEnabled(true);
                }
                else if(i<(allPlaces.size()-1)){
                    i++;
                    PlacesAdapter adapter = new PlacesAdapter(allPlaces.get(i),getBaseContext());
                    recyclerView.setAdapter(adapter);
                    previous.setEnabled(true);
                    if(i==(allPlaces.size()-1)){
                        next.setEnabled(false);
                    }

                }
                else{
                    next.setEnabled(false);
                }

            }
        });
        previous .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i>0){
                    i--;
                    next.setEnabled(true);
                    if(i==0){
                        previous.setEnabled(false);
                    }
                    PlacesAdapter adapter = new PlacesAdapter(allPlaces.get(i),getBaseContext());
                    recyclerView.setAdapter(adapter);
                }

            }
        });



    }
    public void initPlaces(){
        Bundle bundle=getIntent().getExtras();
        placeList=bundle.getParcelableArrayList("placeList");
        allPlaces.add(placeList);
        next_token=bundle.getString("next_token");
        if(next_token!=null&&!next_token.equals("")){
             next.setEnabled(true);
        }
        else{
            next.setEnabled(false);
        }
        if(i==0){
            previous.setEnabled(false);
        }
        else{
            previous .setEnabled(true);
        }

        if(placeList.size()==0){
            noResults.setVisibility(View.VISIBLE);
        }
        else{
            noResults.setVisibility(View.GONE);
        }
    }
    public void requestNextPage(){
        final String url = "http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_next_page?pagetoken="+next_token+"";
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                //RequestBody requestBody = new FormBody.Builder().add("latitude","34.0223519").add("longitude","-118.285117").add("distance","16090").add("category","cafe").add("keyword","usc").build();
                Request request = new Request.Builder().get().url(url).build();

                Response response = null;
                String responseData;
                try {
                    response = client.newCall(request).execute();
                    responseData = response.body().string();
                    //System.out.println(responseData);
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        if(!jsonObject.isNull("next_page_token")){
                            next_token=jsonObject.getString("next_page_token");
                        }
                        else{
                            next_token="";
                        }
                    }catch(Exception e){
                        e.printStackTrace();

                    }
                    parseJSON(responseData);
                    Log.d("Activity", responseData);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSON(String jsonData){
        placeList=new ArrayList<>();
        try{
            JSONObject jsonObject= new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            for (int i=0; i<jsonArray.length();i++){
                String str=jsonArray.getJSONObject(i).toString();
                JSONObject p= new JSONObject(str);
                Place place=new Place();
                place.setCategory(p.getString("icon"));
                place.setId(p.getString("place_id"));
                place.setName(p.getString("name"));
                place.setAddress(p.getString("vicinity"));
                Log.d("vicinity",place.getAddress());
                placeList.add(place);
            }
            allPlaces.add(placeList);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PlacesAdapter adapter = new PlacesAdapter(placeList,getBaseContext());
                    recyclerView.setAdapter(adapter);
                    if(next_token==null||next_token.equals("")){
                        next.setEnabled(false);
                    }

                }
            });



        }
        catch (Exception e){
            e.printStackTrace();

        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onResume(){
        PlacesAdapter adapter = new PlacesAdapter(placeList,getBaseContext());
        recyclerView.setAdapter(adapter);
        super.onResume();

    }


}
