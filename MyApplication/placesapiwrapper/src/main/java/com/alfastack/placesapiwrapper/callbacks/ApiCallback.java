package com.alfastack.placesapiwrapper.callbacks;

import androidx.annotation.MainThread;

import com.alfastack.placesapiwrapper.models.Restaurant;

import java.util.List;

/**
 * Created by Joro on 21/10/2019
 */
@MainThread
public abstract class ApiCallback {

    public ApiCallback() { }

    @MainThread
    public abstract void onFetched(List<Restaurant> data);

    @MainThread
    public void onPreExecute() { }

    @MainThread
    public abstract void onFetchFailed(String message);
}
