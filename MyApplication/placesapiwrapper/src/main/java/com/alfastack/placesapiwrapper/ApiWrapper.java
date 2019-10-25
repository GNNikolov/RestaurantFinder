package com.alfastack.placesapiwrapper;

import android.location.Location;
import android.util.Log;

import com.alfastack.placesapiwrapper.callbacks.ApiCallback;

/**
 * Created by Joro on 21/10/2019
 */
public class ApiWrapper {
    private final String baseURL;
    private final ApiHandler mHandler;

    private ApiWrapper(final ApiCallback mCallBack, final String baseURL) {
        this.mHandler = new ApiHandler(mCallBack);
        this.baseURL = baseURL;
    }

    public static class Builder {
        private ApiCallback mCallBack;
        private String baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyD9DpsJqq_TeWBHy1Hfir0B2C2rY4pmvYY&type=restaurant";

        public Builder(final ApiCallback mCallBack){
            this.mCallBack = mCallBack;
        }

        public Builder setLocation(final Location location) {
            final String mLocation = "&location=" + location.getLatitude()
                    + ",%20"  + location.getLongitude();
            baseURL = baseURL.concat(mLocation);
            return this;
        }

        public Builder setRadius(final String radius) {
            final String mRadius = "&radius=" + radius;
            baseURL = baseURL.concat(mRadius);
            return this;
        }

        public ApiWrapper build() {
            return new ApiWrapper(mCallBack, baseURL);
        }
    }

    public void execute() {
        Log.i("URLDBG", String.valueOf(baseURL.length()));
        mHandler.execute(baseURL);
    }

}
