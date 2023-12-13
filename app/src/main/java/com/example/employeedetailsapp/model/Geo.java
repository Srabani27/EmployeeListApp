package com.example.employeedetailsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Geo implements Parcelable {
    private String lat;
    private String lng;

    protected Geo(Parcel in) {
        lat = in.readString();
        lng = in.readString();
    }

    public static final Creator<Geo> CREATOR = new Creator<Geo>() {
        @Override
        public Geo createFromParcel(Parcel in) {
            return new Geo(in);
        }

        @Override
        public Geo[] newArray(int size) {
            return new Geo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat);
        dest.writeString(lng);
    }

    // Constructor,

    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    // getters, setters...


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
