package com.alfastack.placesapiwrapper.models;

import android.location.Location;

/**
 * Created by Joro on 21/10/2019
 */
public class Restaurant {
    private Location location;
    private String name;
    private boolean openNow;

    public Restaurant(Location location, String name, boolean openNow) {
        this.location = location;
        this.openNow = openNow;
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public boolean isOpenNow() {
        return openNow;
    }
}
