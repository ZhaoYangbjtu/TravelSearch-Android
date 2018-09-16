package com.yangzhao.travelsearch.Bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by YangZhao on 2018/4/8.
 */

public class Place  implements Parcelable{
    private String id;
    private String name;
    private String category;
    private String address;
    private String rating;
    private String phone;
    private String googlePage;
    private String website;
    private String priceLevel;
    private String state;
    private String city;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGooglePage() {
        return googlePage;
    }

    public void setGooglePage(String googlePage) {
        this.googlePage = googlePage;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Place() {
        super();
    }

    public Place(Parcel in) {
        id= in.readString();
        name = in.readString();
        address=in.readString();
        category=in.readString();
        state=in.readString();
        city=in.readString();
        //categoryUrl=in.readString();
        //age = in.readInt();
    }
    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(category);
        dest.writeString(state);
        dest.writeString(city);

        //dest.writeInt(age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        Place place = (Place) o;
        return id.equals(place.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
