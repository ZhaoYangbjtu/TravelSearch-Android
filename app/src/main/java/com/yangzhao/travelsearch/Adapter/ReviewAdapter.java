package com.yangzhao.travelsearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YangZhao on 2018/4/13.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private List<Review> reviewList =new ArrayList<>();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView personImg;
        TextView name;
        RatingBar ratingBar;
        TextView time;
        TextView text;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            personImg = (ImageView) view.findViewById(R.id.personImg);
            name =(TextView) view.findViewById(R.id.name);
            time =(TextView) view.findViewById(R.id.time);
            text= (TextView) view.findViewById(R.id.text);
            ratingBar=(RatingBar) view.findViewById(R.id.rating);
            cardView=(CardView) view.findViewById(R.id.card_view);
        }
    }
    public ReviewAdapter(List<Review> reviewList, Context context) {

        this.reviewList = reviewList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        final ViewHolder holder =new ViewHolder(view);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Review review = reviewList.get(position);
                Uri uri = Uri.parse(review.getAuthor_url());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                view.getContext().startActivity(intent);
            }
        });
        holder.personImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Review review = reviewList.get(position);
                Uri uri = Uri.parse(review.getAuthor_url());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                view.getContext().startActivity(intent);
            }
        });
        return holder;

    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        Review review = reviewList.get(position);
        //holder.name.setText(Html.fromHtml(
                               // "<a href="+review.getAuthor_url()+">"+review.getName()+"</a>"));
        holder.name.setText(review.getName());
        //holder.name.setMovementMethod(LinkMovementMethod.getInstance());
        holder.ratingBar.setRating(Float.parseFloat(review.getRating()));
        holder.text.setText(review.getText());

        System.out.println(this.TimeStamp2Date(review.getTime()));//打印出你要的时间
        System.out.println(review.getTime());
        holder.time.setText(this.TimeStamp2Date(review.getTime()));
        Picasso.with(context).load(review.getPersonImg()).into(holder.personImg);
        //String url = "http://s16.sinaimg.cn/orignal/89429f6dhb99b4903ebcf&690";
        //得到可用的图片
        //Bitmap bitmap = getHttpBitmap(place.getCategory());
        //holder.category.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount(){
        return reviewList.size();
    }
    public static String TimeStamp2Date(String timestampString) {
        String formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }


}

