package com.yangzhao.travelsearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.yangzhao.travelsearch.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<Place> placeList = new ArrayList<>();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImg;
        TextView placeName;
        TextView placeAddress;
        CardView cardView;
        ImageView heartView;


        public ViewHolder(View view) {
            super(view);
            categoryImg = (ImageView) view.findViewById(R.id.category);
            placeName = (TextView) view.findViewById(R.id.name);
            placeAddress = (TextView) view.findViewById(R.id.address);
            cardView = (CardView) view.findViewById(R.id.card_view);
            heartView=(ImageView) view.findViewById(R.id.heart);
        }
    }

    public FavoriteAdapter(List<Place> placesList, Context context) {

        this.placeList = placesList;
        this.context = context;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        final FavoriteAdapter.ViewHolder holder = new FavoriteAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Place place = placeList.get(position);
                Toast.makeText(v.getContext(), "you clicked content" + place.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),DetailActivity.class);
                //启动Activity
                Bundle bundle= new Bundle();
                //Place place= placeList.get(position);
                bundle.putString("place_id", place.getId());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
                //sendRequestWithOkHttp(view);
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
        holder.heartView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position =holder.getAdapterPosition();
                Place place= placeList.get(position);
                HashSet<Place> set = MainActivity.getSet();
                if(set.contains(place)){
                    holder.heartView.setImageResource(R.drawable.heart_outline_black);


                }
                else{
                    holder.heartView.setImageResource(R.drawable.heart_fill_red);
                }
                MainActivity.checkFavorite(place);
                System.out.println("shanchu");
                MainActivity.mAdapter.notifyDataSetChanged();
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
   /* private void sendRequestWithOkHttp(View view) {
        final View view1=view;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                //RequestBody requestBody = new FormBody.Builder().add("username",).add("password",).build();
                Request request = new Request.Builder().get().url("http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_place_detail?id=ChIJ7aVxnOTHwoARxKIntFtakKo").build();
                Response response = null;
                String responseData;
                try {
                    response = client.newCall(request).execute();
                    responseData = response.body().string();
                    System.out.println(responseData);
                    //parseJSON(responseData);
                    Log.d("Activity",responseData);
                    if(!responseData.equals("[]")){
                        Intent intent = new Intent(view1.getContext(),DetailActivity.class);
                        //启动Activity
                        Bundle bundle= new Bundle();
                        bundle.putString("jsonDoc", responseData);
                        intent.putExtras(bundle);
                        view1.getContext().startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/


    @Override
    public void onBindViewHolder(final FavoriteAdapter.ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
        Picasso.with(context).load(place.getCategory()).into(holder.categoryImg);
        //String url = "http://s16.sinaimg.cn/orignal/89429f6dhb99b4903ebcf&690";
        //得到可用的图片
        //Bitmap bitmap = getHttpBitmap(place.getCategory());
        //holder.category.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}