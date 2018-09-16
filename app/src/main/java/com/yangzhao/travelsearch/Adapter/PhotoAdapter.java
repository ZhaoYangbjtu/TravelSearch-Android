package com.yangzhao.travelsearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yangzhao.travelsearch.Bean.Place;
import com.yangzhao.travelsearch.Bean.Review;
import com.yangzhao.travelsearch.DetailActivity;
import com.yangzhao.travelsearch.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YangZhao on 2018/4/13.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

    private List<Bitmap> photoList=new ArrayList<Bitmap>();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);

        }
    }
    public PhotoAdapter(List<Bitmap> photoList, Context context) {

        this.photoList = photoList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent,false);
        final ViewHolder holder =new ViewHolder(view);

        return holder;

    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        Bitmap image = photoList.get(position);
        holder.imageView.setImageBitmap(image);

        //Picasso.with(context).load(review.getPersonImg()).into(holder.personImg);
        //String url = "http://s16.sinaimg.cn/orignal/89429f6dhb99b4903ebcf&690";
        //得到可用的图片
        //Bitmap bitmap = getHttpBitmap(place.getCategory());
        //holder.category.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount(){
        return photoList.size();
    }


}

