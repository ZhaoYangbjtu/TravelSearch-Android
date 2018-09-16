package com.yangzhao.travelsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yangzhao.travelsearch.Adapter.FavoriteAdapter;
import com.yangzhao.travelsearch.Adapter.PlacesAdapter;
import com.yangzhao.travelsearch.Bean.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by YangZhao on 2018/4/8.
 */

public class FavoriteFragment extends Fragment {

    private View view;
    //private static final String KEY = "title";
    //private TextView tvContent;
    private Button searchBtn;
    private HashSet<Place> favList= new HashSet<>();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favorite,container,false);
        favList=MainActivity.getSet();
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //Context context;
        List<Place> places = new ArrayList<>();
        for(Place p : favList){
            places.add(p);
        }
        FavoriteAdapter adapter = new FavoriteAdapter(places,getActivity().getBaseContext());
        recyclerView.setAdapter(adapter);



        //tvContent = (TextView) view.findViewById(R.id.tv_content);
        //String string = getArguments().getString(KEY);
        //tvContent.setText(string);
        //tvContent.setTextColor(Color.BLUE);
        //tvContent.setTextSize(30);
        return view;
    }
    @Override
    public void onResume(){
        favList=MainActivity.getSet();
        List<Place> places = new ArrayList<>();
        for(Place p : favList){
            places.add(p);
        }
        FavoriteAdapter adapter = new FavoriteAdapter(places,getActivity().getBaseContext());
        recyclerView.setAdapter(adapter);
        super.onResume();

    }


}
