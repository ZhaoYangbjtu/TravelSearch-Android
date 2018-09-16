package com.yangzhao.travelsearch;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.yangzhao.travelsearch.Adapter.PhotoAdapter;
import com.yangzhao.travelsearch.Adapter.ReviewAdapter;
import com.yangzhao.travelsearch.Bean.Place;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by YangZhao on 2018/4/11.
 */

public class PhotosFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private View view;
    //private static final String KEY = "title";
    //private TextView tvContent;
    private String place_id;
    private ImageView imageView;
    RecyclerView recyclerView;
    private TextView noResults;

    GoogleApiClient mGoogleApiClient;
    List<Bitmap> photoList=new ArrayList<Bitmap>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_photos,container,false);


        //imageView= (ImageView) view.findViewById(R.id.myphoto);
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Log.d("photo","1");
        imageView=(ImageView) view.findViewById(R.id.image);
        Place place=DetailActivity.getInfo();
        place_id=place.getId();
        mGoogleApiClient.connect();
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //Context context;
        PhotoAdapter adapter = new PhotoAdapter(photoList,getActivity().getBaseContext());
        recyclerView.setAdapter(adapter);
        noResults=(TextView) view.findViewById(R.id.noResults);



        //PhotoTask.AttributedPhoto attributedPhoto = null;
        //setImage();
            // Release the PlacePhotoMetadataBuffer.





        //tvContent = (TextView) view.findViewById(R.id.tv_content);
        //String string = getArguments().getString(KEY);
        //tvContent.setText(string);
        //tvContent.setTextColor(Color.BLUE);
        //tvContent.setTextSize(30);

        return view;
    }
    public void onConnectionSuspended(int i) {
        Log.d("222","1234444");

    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d("333","1234444");

    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.d("zhixing","chengong1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("zhixing","chengong2");
                PlacePhotoMetadataResult result = Places.GeoDataApi
                        .getPlacePhotos(mGoogleApiClient,place_id).await();
                Log.d("zhixing","chengong3");
                PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
                Log.d("zhixing","chengong4");
                int size=photoMetadataBuffer.getCount();
                PlacePhotoMetadata photo[]=new PlacePhotoMetadata[size];
                for(int i=0;i<size;i++){
                    photo[i]=photoMetadataBuffer.get(i);
                }
                 //photo[0] = photoMetadataBuffer.get(0);
                 //photo[1]= photoMetadataBuffer.get(1);
                 //photo[2]= photoMetadataBuffer.get(2);
                Log.d("zhixing","chengong5");

                // Get the first bitmap and its attributions.
                //PlacePhotoMetadata photo = photoMetadata.get(0);
               // CharSequence attribution = photo.getAttributions();
                // Load a scaled bitmap for this photo.
                final List<Bitmap> photoList=new ArrayList<Bitmap>();

                for (int j = 0; j < photo.length; j++) {
                    Bitmap bitmap = photo[j].getPhoto(mGoogleApiClient).await().getBitmap();
                    photoList.add(bitmap);
                }

                    //photoList.add(bitmap);
                    // Bitmap bitmap1 = photo[1].getPhoto(mGoogleApiClient).await().getBitmap();
                    //photoList.add(bitmap1);
                    // Bitmap bitmap2 = photo[2].getPhoto(mGoogleApiClient).await().getBitmap();
                    //photoList.add(bitmap2);

                    // System.out.println("image:" + image);
                    //System.out.println("image:" + image);
                    //System.out.println("image:" + image);
                Log.d("photo", "2");
                    //getActivity().runOnUiThread(new Runnable() {
                    // @Override
                    //public void run() {
                    // imageView.setImageBitmap(image);
                    //  }
                    // });
                getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(photoList.size()==0){
                                noResults.setVisibility(view.VISIBLE);
                            }
                            else {
                                noResults.setVisibility(view.GONE);
                                PhotoAdapter adapter = new PhotoAdapter(photoList, getActivity().getBaseContext());
                                recyclerView.setAdapter(adapter);
                            }
                            DetailActivity.getDialog().dismiss();

                        }
                });

                //Context context;


                photoMetadataBuffer.release();
            }
        }).start();

    }
}
