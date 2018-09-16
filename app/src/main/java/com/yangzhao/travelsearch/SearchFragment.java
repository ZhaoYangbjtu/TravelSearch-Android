package com.yangzhao.travelsearch;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yangzhao.travelsearch.Bean.Place;
import android.support.v4.app.Fragment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by YangZhao on 2018/4/8.
 */

public class SearchFragment extends Fragment {
    private View view;
    //private static final String KEY = "title";
    //private TextView tvContent;
    private AutoCompleteTextView specifyLocation;
    private Button searchBtn,clearBtn;
    private RadioGroup radioGroup;
    private EditText keyword;
    private EditText distance;
    private String category;
    private Spinner spinner;
    private ArrayList<Place> placeList;
    private TextInputLayout keyword_layout;
    private TextInputLayout distance_layout;
    private TextView keyword_validation;
    private TextView from_validation;
    private String url;
    private String next_token;
    private double radius;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search,container,false);
        keyword=(EditText) view.findViewById(R.id.keyword);
        distance=(EditText) view.findViewById(R.id.distance);

        spinner=(Spinner) view.findViewById(R.id.spinner);
        searchBtn=(Button) view.findViewById(R.id.searchBtn);
        clearBtn=(Button) view.findViewById(R.id.clearBtn);
        specifyLocation = view.findViewById(R.id.specifyLocation);

        CustomAutoCompleteAdapter adapter =  new CustomAutoCompleteAdapter(getActivity().getBaseContext());
        specifyLocation.setAdapter(adapter);
        //EditText  autocomplete=(EditText) view.findViewById(R.id.place_autocomplete_fragment);

        //add child fragment
        /*getChildFragmentManager()
                .beginTransaction()
                .add(R.id.place_autocomplete_fragment, autocompleteFragment, "tag")
                .commit();
                */
        //autocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

       /* autocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(com.google.android.gms.location.places.Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });*/


        radioGroup=(RadioGroup) view.findViewById(R.id.radioGroup);
        //specifyLocation=(EditText) view.findViewById(R.id.specifyLocation);
        specifyLocation.setFocusable(false);
        specifyLocation.setFocusableInTouchMode(false);
        keyword_validation=(TextView) view.findViewById(R.id.keyword_validation);
        from_validation=(TextView)view.findViewById(R.id.from_validation);
        keyword_layout=(TextInputLayout) view.findViewById(R.id.keyword_layout);
        distance_layout=(TextInputLayout) view.findViewById(R.id.distance_layout);
        keyword.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                //EditText textView = (EditText)v;

                String hint;
                hint = keyword_layout.getHint().toString();
                if (hasFocus) {

                    keyword_layout.setTag(hint);
                    keyword_layout.setHint("");
                } else {
                    hint = keyword_layout.getTag().toString();
                    keyword_layout.setHint(hint);
                }
            }
        });
        distance.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                //EditText textView = (EditText)v;

                String hint;
                hint = distance_layout.getHint().toString();
                if (hasFocus) {

                    distance_layout.setTag(hint);
                    distance_layout.setHint("");
                } else {
                    hint = distance_layout.getTag().toString();
                    distance_layout.setHint(hint);
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //username = input_name.getText().toString();
                //password = input_password.getText().toString();

                sendRequestWithOkHttp();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //username = input_name.getText().toString();
                //password = input_password.getText().toString();
                keyword.setText("");
                distance.setText("");
                radioGroup.check(R.id.radioButton1);
                specifyLocation.setText("");
                from_validation.setVisibility(view.GONE);
                keyword_validation.setVisibility(view.GONE);
                spinner.setSelection(0);

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
            }
        });

        return view;

    }
    private void selectRadioBtn(){
        RadioButton radioButton = (RadioButton) getActivity().findViewById(radioGroup.getCheckedRadioButtonId());
        String selectText = radioButton.getText().toString();
        System.out.println(radioGroup.getCheckedRadioButtonId());
        System.out.println(selectText);
        if(radioGroup.getCheckedRadioButtonId()==R.id.radioButton1){
            //android:focusable="false;
            specifyLocation.setFocusable(false);
            specifyLocation.setFocusableInTouchMode(false);
            System.out.println("设置false成功");

        }
        else{
            //android:focusable="false;
            specifyLocation.setFocusableInTouchMode(true);
            specifyLocation.setFocusable(true);
            System.out.println("设置true成功");
            //specifyLocation.requestFocus();

        }
    }

    private void sendRequestWithOkHttp() {
        if(keyword.getText().toString().equals("")&&radioGroup.getCheckedRadioButtonId()==R.id.radioButton2&&specifyLocation.getText().toString().equals("")){
            keyword_validation.setVisibility(view.VISIBLE);
            from_validation.setVisibility(view.VISIBLE);

        }
        else if(keyword.getText().toString().equals("")){
            keyword_validation.setVisibility(view.VISIBLE);
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.radioButton2&&specifyLocation.getText().toString().equals("")){
            from_validation.setVisibility(view.VISIBLE);
        }
        else {
            final ProgressDialog proDialog = android.app.ProgressDialog.show(getActivity(), "", "Fetching results");

            category = (String) spinner.getSelectedItem();
            if(distance.getText().toString().equals("")){
                radius=10*1609.334;
            }
            else{
                try{
                    radius=Double.parseDouble(distance.getText().toString())*1609.334;
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
            if(specifyLocation.getText().toString().equals("")){
                url = "http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_nearby_places?latitude=34.0223519&longitude=-118.285117&distance="
                        + radius + "&category=" + category + "&keyword=" + keyword.getText().toString() + "";
            }
            else{
                url = "http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_nearby_places?latitude=34.0223519&longitude=-118.285117&distance="
                        + radius + "&category=" + category + "&keyword=" + keyword.getText().toString() + "&specify_location="+specifyLocation.getText().toString()+"";
            }
            System.out.println(url);
            //http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_nearby_places?latitude=34.0223519&longitude=-118.285117&distance=16090&category=cafe&keyword=usc
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
                        if (!responseData.equals("[]")) {
                            Intent intent = new Intent(getActivity(), PlacesActivity.class);
                            //启动Activity
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("placeList", placeList);
                            bundle.putString("next_token",next_token);
                            intent.putExtras(bundle);
                            proDialog.dismiss();//万万不可少这句，否则会程序会卡死。

                            startActivity(intent);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
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
        }
        catch (Exception e){
            e.printStackTrace();

        }

    }
}
