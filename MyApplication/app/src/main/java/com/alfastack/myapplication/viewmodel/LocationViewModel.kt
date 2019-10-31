package com.alfastack.myapplication.viewmodel

import android.app.Application
import android.location.Location
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alfastack.myapplication.StatusView

class LocationViewModel(mApplication: Application) : AndroidViewModel(mApplication) {
    val locationLiveData = MutableLiveData<Location>()

    companion object {
        @BindingAdapter("android:background")
        @JvmStatic
        fun setLocationText(view: StatusView, location: Location?) {
            view.updateViews(location)
        }
    }
}