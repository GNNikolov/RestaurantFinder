package com.alfastack.placesapiwrapper.models;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joro on 21/10/2019
 */
public class Restaurant {
    private Location location;
    @SerializedName("name")
    private String name;
    @SerializedName("vicinity")
    private String address;

    public Restaurant(Location location, String name, String address) {
        this.location = location;
        this.address = address;
        this.name = name;
    }

    public Restaurant() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    @Override
    public String toString() {
        return name + "/" + address;
    }
}
