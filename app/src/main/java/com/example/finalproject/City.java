package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class City implements Parcelable {
    public final String name;
    public final int population;
    public final double latitude;
    public final double longitude;
    public final String wikiData;

    public City(String name, int population, double latitude, double longitude, String wikiData) {
        this.name = name;
        this.population = population;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wikiData = wikiData;
    }
    // region Parcelable implementation
    protected City(Parcel in) {
        name = in.readString();
        population = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        wikiData = in.readString();
    }
    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(population);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(wikiData);
    }
    // endregion
}
