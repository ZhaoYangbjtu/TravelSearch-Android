package com.yangzhao.travelsearch;


import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.yangzhao.travelsearch.Bean.Place;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private  static HashSet<Place> favList=new HashSet<Place>();

    private TabLayout mTabLayout;
    public static ViewPager mViewPager;
    private SearchFragment searchFragment= new SearchFragment();
    private static FavoriteFragment favoriteFragment=new FavoriteFragment();
    public static FragmentPagerAdapter  mAdapter;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2, view3, view4, view5;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private List<String> listTitles;
    //private List<Fragment> fragments;
    private List<TextView> listTextViews;
    public static Double Lat=0.0;
    public static Double Lng=0.0;

    @Override
    public void onLocationChanged(final Location location) {
        //your code here
        Lat = location.getLatitude();
        Lng = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        //Place p= new Place();
        //p.setId("123");
        //p.setAddress("hoover");
        //p.setName("usc");
        //favList.add(p);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setTitle("Places Search");
        initData();
        //final Location location=new Location("");
        //onLocationChanged(location);

    }
    private void initData() {

        listTitles = new ArrayList<>();
        //fragments = new ArrayList<>();
        listTextViews = new ArrayList<>();
        listTitles.add("推荐");
        listTitles.add("热点");
        //listTitles.add("视频");
        //listTitles.add("北京");
        //listTitles.add("社会");
        //listTitles.add("娱乐");
        //listTitles.add("问答");
        //listTitles.add("图片");
        //listTitles.add("科技");
        //listTitles.add("汽车");
        //listTitles.add("体育");
        //listTitles.add("财经");
        //listTitles.add("军事");
        //listTitles.add("国际");
        //for (int i = 0; i < listTitles.size(); i++) {
        //ContentFragment fragment = ContentFragment.newInstance(listTitles.get(i));
        //fragments.add(fragment);

        //}
        //mTabLayout.setTabMode(TabLayout.SCROLL_AXIS_HORIZONTAL);//设置tab模式，当前为系统默认模式
        //for (int i=0;i<listTitles.size();i++){
        //mTabLayout.addTab(mTabLayout.newTab().setText(listTitles.get(i)));//添加tab选项
        // }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position==0){
                    System.out.println("get0");
                    return searchFragment;
                }

                else{
                    //favoriteFragment.updateView();
                    System.out.println("get1");
                    return new FavoriteFragment();
                }
            }


            @Override
            public int getCount() {
                return 2;
            }
            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            //@Override
            /*public CharSequence getPageTitle(int position) {
                if(position==0) {
                    return "SEARCH";
                }
                else{
                    return "FAVORITE";
                }
            }*/
            @Override
            public int getItemPosition(Object object){
                System.out.println("执行刷新");
                //mTabLayout.getTabAt(0).setCustomView(getTabView(0));
                //mTabLayout.getTabAt(1).setCustomView(getTabView(1));;
                return POSITION_NONE;
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                System.out.println("数据改变");
            }

        };


        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1) {
                    mViewPager.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager,false);//将TabLayout和ViewPager关联起来。


        //mTabLayout.getTabAt(0).setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
        mTabLayout.getTabAt(0).setCustomView(getTabView(0));
        mTabLayout.getTabAt(1).setCustomView(getTabView(1));
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.divider)); //设置分割线的样式
        linearLayout.setDividerPadding(10); //设置分割线间隔

        //mTabLayout.getTabAt(1).setIcon(R.drawable.share);
        //mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

    }
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
        if(position==0) {
            TextView txt_title = (TextView) view.findViewById(R.id.tabtext);
            txt_title.setText("SEARCH");
            ImageView img_title = (ImageView) view.findViewById(R.id.tabicon);
            img_title.setImageResource(R.drawable.search);
        }
        else {
            TextView txt_title = (TextView) view.findViewById(R.id.tabtext);
            txt_title.setText("FAVORITE");
            ImageView img_title = (ImageView) view.findViewById(R.id.tabicon);
            img_title.setImageResource(R.drawable.heart_fill_white);

        }
        return view;
    }

    public static FavoriteFragment getFav(){
        return favoriteFragment;
    }
    public static HashSet<Place> getSet(){
        return favList;
    }
    public static void checkFavorite(Place p){
        if(favList.contains(p)){
            favList.remove(p);
        }
        else{
            favList.add(p);
        }
    }
    public static ViewPager getViewPager(){
        return mViewPager;
    }

}