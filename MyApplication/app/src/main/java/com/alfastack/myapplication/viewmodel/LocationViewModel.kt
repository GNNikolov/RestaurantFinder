package com.alfastack.myapplication.viewmodel

import android.app.Application
import android.location.Location
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alfastack.myapplication.R

class LocationViewModel(mApplication: Application) : AndroidViewModel(mApplication) {
    val locationLiveData = MutableLiveData<Location>()

    companion object {
        @BindingAdapter("android:background")
        @JvmStatic
        fun setLocationText(view: TextView, location: Location?) {
            if (location != null) {
                view.text = "Location available.\nReady to search!"
            } else {
                view.text = "No active GPS\nClick to enable it."
            }
        }
        @BindingAdapter("android:foregroundTint")
        @JvmStatic
        fun setLocationIcon(imageView: ImageView, mLocation: Location?) {
            val mDrawable =
                if (mLocation == null) imageView.context.getDrawable(R.drawable.ic_action_name)
                else imageView.context.getDrawable(R.drawable.ic_loc_on)
            imageView.setImageDrawable(mDrawable)
        }
    }
}