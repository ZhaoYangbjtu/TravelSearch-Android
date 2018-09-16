package com.yangzhao.travelsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yangzhao.travelsearch.Bean.Place;

import org.w3c.dom.Text;

/**
 * Created by YangZhao on 2018/4/11.
 */

public class InfoFragment extends Fragment {
    private View view;
    //private static final String KEY = "title";
    private TextView address,phone,priceLevel,googlePage,website;
    private RatingBar rating;
    private Button searchBtn;
    private Place place;
    private LinearLayout websitelayout,phonelayout,pricelayout,addresslayout,googlelayout,ratinglayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        place = DetailActivity.getInfo();

        view = inflater.inflate(R.layout.fragment_info,container,false);
        //Log.d("info",data);


        address = (TextView) view.findViewById(R.id.address);
        address.setText(place.getAddress());
        phone = (TextView) view.findViewById(R.id.phone_number);
        websitelayout=(LinearLayout)view.findViewById(R.id.websitelayout);
        googlelayout=(LinearLayout)view.findViewById(R.id.googlelayout);
        addresslayout=(LinearLayout)view.findViewById(R.id.addresslayout);
        pricelayout=(LinearLayout) view.findViewById(R.id.pricelayout);
        phonelayout=(LinearLayout) view.findViewById(R.id.phonelayout);
        ratinglayout=(LinearLayout) view.findViewById(R.id.ratinglayout);
        if(place.getPhone()!=null){
            phone.setText(place.getPhone());
        }
        else{
            phonelayout.setVisibility(view.GONE);
        }

        rating = (RatingBar) view.findViewById(R.id.rating);
        if(place.getRating()!=null){
            rating.setRating(Float.parseFloat(place.getRating()));
        }
        else{
            ratinglayout.setVisibility(view.GONE);
        }

        priceLevel=(TextView) view.findViewById(R.id.price_level);
        String $="";
        if(place.getPriceLevel()!=null) {

            for(int i=0;i<Integer.parseInt(place.getPriceLevel());i++){
                $+="$";
            }
            priceLevel.setText($);
        }
        else{
            pricelayout.setVisibility(view.GONE);
        }
        googlePage = (TextView) view.findViewById(R.id.google_page);
        if(place.getGooglePage()!=null){
            googlePage.setText(place.getGooglePage());
        }
        else{
            googlelayout.setVisibility(view.GONE);
        }

        website= (TextView) view.findViewById(R.id.website);

        if(place.getWebsite()!=null){
            website.setText(place.getWebsite());
        }
        else{
            websitelayout.setVisibility(view.GONE);
        }


        //String string = getArguments().getString(KEY);
        //tvContent.setText(string);
        //tvContent.setTextColor(Color.BLUE);
        //tvContent.setTextSize(30);
        return view;
    }
}
