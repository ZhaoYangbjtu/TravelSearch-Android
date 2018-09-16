package com.yangzhao.travelsearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yangzhao.travelsearch.Adapter.PhotoAdapter;
import com.yangzhao.travelsearch.Bean.Place;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private InfoFragment infoFragment = new InfoFragment();
    private PhotosFragment photosFragment = new PhotosFragment();
    private MapFragment mapFragment = new MapFragment();
    private ReviewsFragment reviewsFragment = new ReviewsFragment();
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2, view3, view4, view5;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private List<String> listTitles;
    private List<TextView> listTextViews;
    private static JSONObject data;
    private static ProgressDialog proDialog;
    private ActionBar actionBar;
    private String place_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("执行oncreate");
        proDialog = android.app.ProgressDialog.show(this, "", "Fetching results");
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String place_list = bundle.getString("place_list");
        try{
            data=new JSONObject(place_list);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //sendRequestWithOkHttp(place_id);
        setContentView(R.layout.activity_detail);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        try{
            actionBar.setTitle(data.getJSONObject("result").getString("name"));
        }
        catch (Exception e){
            e.printStackTrace();
        }


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        initData();
        System.out.println("执行完成oncreate");
;



    }
    public static String getLatitude(){
        String latitude=new String();
        try{
            latitude=data.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return latitude;
    }
    public static String getLongitude(){
        String longitude=new String();
        try{
            longitude=data.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lng");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return longitude;
    }


    public static Place getInfo() {
        //JSONObject jsonObject= new JSONObject(data);
        Place place = new Place();
        try {
            //JSONArray jsonArray=data.getJSONArray("result");
            //data.getString("result");
            place.setId(data.getJSONObject("result").getString("place_id"));
            //data.getJSONObject("result").getString("vicinity");
            place.setName(data.getJSONObject("result").getString("name"));
            place.setAddress(data.getJSONObject("result").getString("vicinity"));
            if(!data.getJSONObject("result").isNull("price_level")) {
                place.setPriceLevel(data.getJSONObject("result").getString("price_level"));
            }
            if(!data.getJSONObject("result").isNull("rating")) {
                place.setRating(data.getJSONObject("result").getString("rating"));
            }
            if(!data.getJSONObject("result").isNull("formatted_phone_number")) {
                place.setPhone(data.getJSONObject("result").getString("formatted_phone_number"));
            }
            if(!data.getJSONObject("result").isNull("url")) {
                place.setGooglePage(data.getJSONObject("result").getString("url"));
            }
            if(!data.getJSONObject("result").isNull("website")) {
                place.setWebsite(data.getJSONObject("result").getString("website"));
            }

            JSONArray address_components=data.getJSONObject("result").getJSONArray("address_components");

            for(int i=0;i<address_components.length();i++){
                JSONObject r= address_components.getJSONObject(i);
                String administrative_area_level=r.getJSONArray("types").get(0).toString();
                System.out.println("administrative_area_level"+administrative_area_level);
                if(administrative_area_level.equals("administrative_area_level_1")){
                    place.setState(address_components.getJSONObject(i).getString("short_name"));
                    System.out.println("administrative_area_level_1"+administrative_area_level);
                }
                if(administrative_area_level.equals("administrative_area_level_2")){
                    place.setCity(address_components.getJSONObject(i).getString("short_name"));
                    System.out.println("administrative_area_level_2"+administrative_area_level);
                }
            }

            System.out.println("1place.getId():"+place.getId());
            place.setCategory(data.getJSONObject("result").getString("icon"));

        } catch (Exception e) {
            System.out.println("出现异常" );
            e.printStackTrace();
        }
        return place;
    }

    public static JSONArray getReviews() {
        //JSONObject jsonObject= new JSONObject(data);
        //Place place=new Place();
        JSONArray reviewArray = new JSONArray();
        try {
            //JSONArray jsonArray=data.getJSONArray("result");
            //data.getString("result");
            reviewArray = data.getJSONObject("result").getJSONArray("reviews");


        } catch (Exception e) {
            e.printStackTrace();

        }
        return reviewArray;

    }


    private void initData() {
        System.out.println("开始执行initdata");
        final FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return infoFragment;
                } else if (position == 1) {
                    return photosFragment;
                } else if (position == 2) {
                    return mapFragment;
                } else {
                    return reviewsFragment;
                }

            }

            @Override
            public int getCount() {
                return 4;
            }

            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mTabLayout.setupWithViewPager(mViewPager,false);//将TabLayout和ViewPager关联起来。
        mTabLayout.getTabAt(0).setCustomView(getTabView(0));
        mTabLayout.getTabAt(1).setCustomView(getTabView(1));
        mTabLayout.getTabAt(2).setCustomView(getTabView(2));
        mTabLayout.getTabAt(3).setCustomView(getTabView(3));
                //mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getBaseContext(),
                        R.drawable.divider)); //设置分割线的样式
        linearLayout.setDividerPadding(10); //设置分割线间隔



        //proDialog.dismiss();


        //mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("执行createmenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        HashSet<Place> set=MainActivity.getSet();
        System.out.println("place.getId():"+getInfo().getId());
        if(set.contains(getInfo())){
            menu.findItem(R.id.favorite).setIcon(R.drawable.heart_fill_white);

        }
        else{
            menu.findItem(R.id.favorite).setIcon(R.drawable.heart_outline_white);

        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://twitter.com/intent/tweet?text=Check out "+getInfo().getName()+" located at "+getInfo().getAddress()+". Website: "+getInfo().getWebsite()+" &hashtags=TravelAndEntertainmentSearch");
                intent.setData(content_url);
                startActivity(intent);
                Toast.makeText(this, "你点击了“fenxiang”按键！", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.favorite:
                Toast.makeText(this, "你点击了“shoucang”按键！", Toast.LENGTH_SHORT).show();
                HashSet<Place> set = MainActivity.getSet();
                if(set.contains(getInfo())){
                    item.setIcon(R.drawable.heart_outline_white);
                    //holder.heartView.setImageResource(R.drawable.heart_outline_black);
                    //FragmentPagerAdapter mAdapter=MainActivity.getAdapater();
                    //mAdapter.notifyDataSetChanged();
                }
                else{
                    item.setIcon(R.drawable.heart_fill_white);
                }
                MainActivity.checkFavorite(getInfo());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
        if(position==0) {
            TextView txt_title = (TextView) view.findViewById(R.id.tabtext);
            txt_title.setText("INFO");
            ImageView img_title = (ImageView) view.findViewById(R.id.tabicon);
            img_title.setImageResource(R.drawable.info_outline);
        }
        else  if(position==1){
            TextView txt_title = (TextView) view.findViewById(R.id.tabtext);
            txt_title.setText("PHOTOS");
            ImageView img_title = (ImageView) view.findViewById(R.id.tabicon);
            img_title.setImageResource(R.drawable.photos);

        }
        else if(position==2){
            TextView txt_title = (TextView) view.findViewById(R.id.tabtext);
            txt_title.setText("MAPS");
            ImageView img_title = (ImageView) view.findViewById(R.id.tabicon);
            img_title.setImageResource(R.drawable.maps);

        }
        else {
            TextView txt_title = (TextView) view.findViewById(R.id.tabtext);
            txt_title.setText("REVIEWS");
            ImageView img_title = (ImageView) view.findViewById(R.id.tabicon);
            img_title.setImageResource(R.drawable.review);

        }
        return view;
    }

    @Override
    public void onResume(){
        System.out.println("执行onresume");
        super.onResume();
        //sendRequestWithOkHttp(place_id);



    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public static ProgressDialog getDialog(){
        return proDialog;
    }
}


