package com.alfastack.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alfastack.placesapiwrapper.models.Restaurant

/**
 * Created by Joro on 28/10/2019
 */
class RestaurantViewModel : ViewModel() {
    val restaurants = MutableLiveData<List<Restaurant>>()
}