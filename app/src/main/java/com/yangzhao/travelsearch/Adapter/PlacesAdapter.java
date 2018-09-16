package com.yangzhao.travelsearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yangzhao.travelsearch.Bean.Place;
import com.yangzhao.travelsearch.DetailActivity;
import com.yangzhao.travelsearch.MainActivity;
import com.yangzhao.travelsearch.PlacesActivity;
import com.yangzhao.travelsearch.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



/**
 * Created by YangZhao on 2018/4/8.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder>{

    private List<Place> placeList =new ArrayList<>();
    private Context context;
    private String id;


    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImg;
        TextView placeName;
        TextView placeAddress;
        CardView cardView;
        ImageView heartView;
        String placeId;

        public ViewHolder(View view) {
            super(view);
            categoryImg = (ImageView) view.findViewById(R.id.category);
            placeName =(TextView) view.findViewById(R.id.name);
            placeAddress =(TextView) view.findViewById(R.id.address);
            cardView=(CardView) view.findViewById(R.id.card_view);
            heartView=(ImageView) view.findViewById(R.id.heart);
        }
    }
    public PlacesAdapter(List<Place> placesList, Context context) {

        this.placeList = placesList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item,parent,false);
        final ViewHolder holder =new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position =holder.getAdapterPosition();
                Place place= placeList.get(position);
                id = place.getId();
                Toast.makeText(v.getContext(),"you clicked content"+place.getName(),Toast.LENGTH_SHORT).show();

                sendRequestWithOkHttp(view,id);

            }
        });
        holder.heartView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position =holder.getAdapterPosition();
                Place place= placeList.get(position);
                HashSet<Place> set = MainActivity.getSet();
                System.out.println(place.getId());
                if(set.contains(place)){
                    holder.heartView.setImageResource(R.drawable.heart_outline_black);
                    //FragmentPagerAdapter mAdapter=MainActivity.getAdapater();
                    //mAdapter.notifyDataSetChanged();
                }
                else{
                    holder.heartView.setImageResource(R.drawable.heart_fill_red);
                }
                MainActivity.checkFavorite(place);
                //getActivity().getSupportFragmentManager().findFragmentById(R.id.fg1);

                //Intent intent = new Intent(view.getContext(),DetailActivity.class);
                //启动Activity
                //Bundle bundle= new Bundle();
                //bundle.putString("courseid",course.getCourseid());
                //bundle.putString("teacher" ,course.getTeacher());
                //bundle.putString("coursename",course.getCoursename());
                //intent.putExtras(bundle);
                //view.getContext().startActivity(intent);
            }
        });
        return holder;

    }




    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        Place place = placeList.get(position);
        holder.placeId=place.getId();
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
        Picasso.with(context).load(place.getCategory()).into(holder.categoryImg);
        HashSet<Place> set = MainActivity.getSet();

        if(set.contains(place)){
            System.out.println("判断为真");
            holder.heartView.setImageResource(R.drawable.heart_fill_red);
            //FragmentPagerAdapter mAdapter=MainActivity.getAdapater();
            //mAdapter.notifyDataSetChanged();
        }
        /*Iterator<Place> it = set.iterator();
        while(it.hasNext()){
            System.out.println("it.next().getId():"+it.next().getId());
            System.out.println("place.getId():"+place.getId());
            if(it.next().getId()==place.getId()){
                holder.heartView.setImageResource(R.drawable.heart_fill_red);
                break;

            }
        }*/
        //String url = "http://s16.sinaimg.cn/orignal/89429f6dhb99b4903ebcf&690";
        //得到可用的图片
        //Bitmap bitmap = getHttpBitmap(place.getCategory());
        //holder.category.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount(){
        return placeList.size();
    }
    private void sendRequestWithOkHttp(View view,final String id) {
        final View view1=view;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final
                OkHttpClient client = new OkHttpClient();
                //RequestBody requestBody = new FormBody.Builder().add("username",).add("password",).build();
                Request request = new Request.Builder().get().url("http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_place_detail?id="+id+"").build();
                Response response = null;
                String responseData;
                try {
                    response = client.newCall(request).execute();
                    responseData = response.body().string();
                    System.out.println(responseData);
                    //parseJSON(responseData);
                    Log.d("Activity",responseData);
                    if(!responseData.equals("[]")){
                        try {
                            Intent intent = new Intent(view1.getContext(),DetailActivity.class);
                            //启动Activity
                            Bundle bundle= new Bundle();
                            bundle.putString("place_list",responseData);

                            intent.putExtras(bundle);
                            view1.getContext().startActivity(intent);

                            //initData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}

