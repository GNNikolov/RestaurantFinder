package com.alfastack.placesapiwrapper.callbacks;

import androidx.annotation.MainThread;

import com.alfastack.placesapiwrapper.models.Restaurant;

import java.util.List;

/**
 * Created by Joro on 21/10/2019
 */
public interface DataFetchedCallback {
    @MainThread
    void onFetched(List<Restaurant> data);
}
