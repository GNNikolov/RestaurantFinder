package com.alfastack.myapplication

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alfastack.myapplication.controllers.LocationController
import com.alfastack.myapplication.databinding.ActivityScrollingBinding
import com.alfastack.myapplication.viewmodel.LocationViewModel
import com.alfastack.placesapiwrapper.ApiWrapper
import com.alfastack.placesapiwrapper.callbacks.ApiCallback
import com.alfastack.placesapiwrapper.models.Restaurant
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {
    private lateinit var locationController: LocationController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val mBinding = DataBindingUtil.setContentView<ActivityScrollingBinding>(this, R.layout.activity_scrolling)
        val locationViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
                .create(LocationViewModel::class.java)
        locationController = LocationController(this, locationViewModel)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        locationController.startLocationService()
        mBinding.item = null
        locationViewModel.locationLiveData.observe(this, Observer { mBinding.item = it })
        val params: AppBarLayout.LayoutParams = toolbar_layout.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = 0
        ApiWrapper.Builder(object : ApiCallback {
            override fun onFetched(data: MutableList<Restaurant>?) {
                data?.let {
                    for (item in it) {
                        Log.i("Fetched", item.toString())
                    }
                }
            }

            override fun onPreExecute() {

            }

            override fun onFetchFailed(message: String?) {
                Log.i("Finished", message.toString())
            }

        }).setLocation(Location("MINE_PROV").apply {
            latitude = 42.654643
            longitude = 23.349079
        }).setRadius("150").build().execute()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationController.LOCATION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationController.startLocationService()
            locationController.startLocationService()
        }

    }
}
