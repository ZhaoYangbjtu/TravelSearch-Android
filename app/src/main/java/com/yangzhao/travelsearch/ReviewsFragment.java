package com.yangzhao.travelsearch;

import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.yangzhao.travelsearch.Adapter.PhotoAdapter;
import com.yangzhao.travelsearch.Adapter.PlacesAdapter;
import com.yangzhao.travelsearch.Adapter.ReviewAdapter;
import com.yangzhao.travelsearch.Adapter.YelpAdapter;
import com.yangzhao.travelsearch.Bean.Place;
import com.yangzhao.travelsearch.Bean.Review;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YangZhao on 2018/4/11.
 */

public class ReviewsFragment extends Fragment {
    private List<Review> reviewList= new ArrayList<>();
    private View view;
    private Spinner reviewCategory,reviewSort;
    //private static final String KEY = "title";
    //private TextView tvContent;
    private Button searchBtn;
    private RecyclerView recyclerView;
    private List<Review> yelpList= new ArrayList<>();
    private List<Review> tempReviewList= new ArrayList<>();
    private List<Review> tempYelpList= new ArrayList<>();
    private TextView noResults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reviews,container,false);
        reviewCategory=(Spinner)view.findViewById(R.id.reviewCategory);
        reviewSort=(Spinner)view.findViewById(R.id.reviewSort);
        noResults=(TextView) view.findViewById(R.id.noResults);
        JSONArray reviewArray= DetailActivity.getReviews();

        for(int i=0;i<reviewArray.length();i++){
            try {
                JSONObject r = reviewArray.getJSONObject(i);
                Review review= new Review();
                review.setName(r.getString("author_name"));
                review.setPersonImg(r.getString("profile_photo_url"));
                review.setRating(r.getString("rating"));
                review.setTime(r.getString("time"));
                review.setText(r.getString("text"));
                review.setAuthor_url(r.getString("author_url"));

                reviewList.add(review);

            }
            catch(Exception e){

            }
        }
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //Context context;
        if(reviewList.size()==0){
            noResults.setVisibility(view.VISIBLE);
        }
        else {
            noResults.setVisibility(view.GONE);
            ReviewAdapter adapter = new ReviewAdapter(reviewList, getActivity().getBaseContext());
            recyclerView.setAdapter(adapter);
        }
        for(int i=0;i<reviewList.size();i++){
            tempReviewList.add(reviewList.get(i));
        }
        //tempReviewList=reviewList;
        reviewSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    if(reviewCategory.getSelectedItem().toString().equals("Google reviews")) {
                        ReviewAdapter adapter = new ReviewAdapter(tempReviewList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else if(reviewCategory.getSelectedItem().toString().equals("Yelp reviews")){
                        ReviewAdapter adapter = new ReviewAdapter(tempYelpList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }
                }
                else if(position==1) {
                    if(reviewCategory.getSelectedItem().toString().equals("Google reviews")) {
                        Collections.sort(reviewList, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                return Integer.parseInt(o2.getRating()) - Integer.parseInt(o1.getRating());
                            }
                        });
                        ReviewAdapter adapter = new ReviewAdapter(reviewList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else if(reviewCategory.getSelectedItem().toString().equals("Yelp reviews")){
                        Collections.sort(yelpList, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                return Integer.parseInt(o2.getRating()) - Integer.parseInt(o1.getRating());
                            }
                        });
                        ReviewAdapter adapter = new ReviewAdapter(yelpList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }
                }
                else if(position==2){
                    if(reviewCategory.getSelectedItem().toString().equals("Google reviews")) {
                        Collections.sort(reviewList, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                return Integer.parseInt(o1.getRating()) - Integer.parseInt(o2.getRating());
                            }
                        });
                        ReviewAdapter adapter = new ReviewAdapter(reviewList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else if(reviewCategory.getSelectedItem().toString().equals("Yelp reviews")){
                        Collections.sort(yelpList, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                return Integer.parseInt(o1.getRating()) - Integer.parseInt(o2.getRating());
                            }
                        });
                        ReviewAdapter adapter = new ReviewAdapter(yelpList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);

                    }

                }
                else if(position==3){
                    if (reviewCategory.getSelectedItem().toString().equals("Google reviews")) {
                        Collections.sort(reviewList, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                return  Integer.parseInt(o2.getTime())-Integer.parseInt(o1.getTime());
                            }
                        });
                        ReviewAdapter adapter = new ReviewAdapter(reviewList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else if(reviewCategory.getSelectedItem().toString().equals("Yelp reviews")){
                        Collections.sort(yelpList, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                return  Integer.parseInt(o2.getTime())-Integer.parseInt(o1.getTime());
                            }
                        });
                        ReviewAdapter adapter = new ReviewAdapter(yelpList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }


                }
                else if(position==4){
                    if (reviewCategory.getSelectedItem().toString().equals("Google reviews")) {

                        Collections.sort(reviewList, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                return  Integer.parseInt(o1.getTime())-Integer.parseInt(o2.getTime());
                            }
                        });
                        ReviewAdapter adapter = new ReviewAdapter(reviewList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else if(reviewCategory.getSelectedItem()=="Yelp reviews"){
                        System.out.println("执行shijina排序");
                        Collections.sort(yelpList, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                return  Integer.parseInt(o1.getTime())-Integer.parseInt(o2.getTime());
                            }
                        });
                        ReviewAdapter adapter = new ReviewAdapter(yelpList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }


                }

            }
            @Override//什么都没选
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reviewCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    reviewSort.setSelection(0);
                    if(tempReviewList.size()==0){
                        noResults.setVisibility(view.VISIBLE);
                    }
                    else{
                        noResults.setVisibility(view.GONE);
                        ReviewAdapter adapter = new ReviewAdapter(tempReviewList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                    }

                }
                else if(position==1){
                    reviewSort.setSelection(0);
                    if(yelpList.size()==0) {
                        sendRequestWithOkHttp();
                    }
                    else{
                        ReviewAdapter adapter = new ReviewAdapter(yelpList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);

                    }
                }

            }
            @Override//什么都没选
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //tvContent = (TextView) view.findViewById(R.id.tv_content);
        //String string = getArguments().getString(KEY);
        //tvContent.setText(string);
        //tvContent.setTextColor(Color.BLUE);
        //tvContent.setTextSize(30);
        return view;
    }
    public void sortReivew(){

    }
    private void sendRequestWithOkHttp() {
        Place place= new Place();
        place=DetailActivity.getInfo();

        final String url = "http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_yelp_business?name="+place.getName()+"&city="+place.getCity()+"&state="+place.getState()+"&country=US&address="+place.getAddress()+"";
        //final Uri uri= Uri.parse(url);
        System.out.println(url);
            //http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_nearby_places?latitude=34.0223519&longitude=-118.285117&distance=16090&category=cafe&keyword=usc
        new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    //RequestBody requestBody = new FormBody.Builder().add("latitude","34.0223519").add("longitude","-118.285117").add("distance","16090").add("category","cafe").add("keyword","usc").build();
                    System.out.println(url);
                    Request request = new Request.Builder().get().url(url).build();

                    Response response = null;
                    String responseData;
                    try {
                        response = client.newCall(request).execute();
                        responseData = response.body().string();
                        //System.out.println(responseData);
                        //parseJSON(responseData);
                        Log.d("Activity", responseData);

                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                if(jsonObject.getJSONArray("businesses").length()==0){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            noResults.setVisibility(view.VISIBLE);
                                        }
                                    });

                                }
                                else {

                                    String id = jsonObject.getJSONArray("businesses").getJSONObject(0).getString("id");
                                    String url = "http://yang923nodejs.us-west-1.elasticbeanstalk.com/request_yelp_reviews?id=" + id + "";
                                    System.out.println(url);
                                    Request rq = new Request.Builder().get().url(url).build();

                                    Response rs = null;
                                    String rd;
                                    rs = client.newCall(rq).execute();
                                    rd = rs.body().string();
                                    parseJson(rd);
                                    System.out.println(rd);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            //Intent intent = new Intent(getActivity(), PlacesActivity.class);
                            //启动Activity
                            //Bundle bundle = new Bundle();
                            //bundle.putParcelableArrayList("placeList", placeList);
                            //intent.putExtras(bundle);
                            //proDialog.dismiss();//万万不可少这句，否则会程序会卡死。

                            //startActivity(intent);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }
    private void parseJson(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray reviews=jsonObject.getJSONArray("reviews");
            int i=0;
            for(;i<reviews.length();i++){
                Review review =new Review();
                review.setText(reviews.getJSONObject(i).getString("text"));
                review.setRating(reviews.getJSONObject(i).getString("rating"));
                review.setTime(reviews.getJSONObject(i).getString("time_created"));
                review.setPersonImg(reviews.getJSONObject(i).getJSONObject("user").getString("image_url"));
                review.setName(reviews.getJSONObject(i).getJSONObject("user").getString("name"));
                yelpList.add(review);

            }
            tempYelpList=yelpList;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(yelpList.size()==0) {
                        noResults.setVisibility(view.VISIBLE);
                    }
                    else {
                        YelpAdapter adapter = new YelpAdapter(yelpList, getActivity().getBaseContext());
                        recyclerView.setAdapter(adapter);
                        noResults.setVisibility(view.GONE);
                    }
                }
            });

        }catch(Exception e ){
            e.printStackTrace();
        }

    }
}


