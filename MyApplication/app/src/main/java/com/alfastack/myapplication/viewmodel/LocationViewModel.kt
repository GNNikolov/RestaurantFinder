package com.alfastack.myapplication.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LocationViewModel(mApplication: Application) : AndroidViewModel(mApplication) {
    val locationLiveData = MutableLiveData<Location>()
}