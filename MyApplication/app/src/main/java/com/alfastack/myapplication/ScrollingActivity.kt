package com.alfastack.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfastack.myapplication.adapter.RestaurantAdapter
import com.alfastack.myapplication.controllers.LocationController
import com.alfastack.myapplication.databinding.ActivityScrollingBinding
import com.alfastack.myapplication.dialogfragments.RadiusSetDialog
import com.alfastack.myapplication.viewmodel.LocationViewModel
import com.alfastack.myapplication.viewmodel.RestaurantViewModel
import com.alfastack.placesapiwrapper.ApiWrapper
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : AppCompatActivity() {
    private lateinit var locationController: LocationController
    private var mLocation: Location? = null
    private lateinit var placesCallBack: PlacesCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val mBinding = DataBindingUtil.setContentView<ActivityScrollingBinding>(
            this,
            R.layout.activity_scrolling
        )
        restaurantList.addItemDecoration(
            DividerItemDecoration(
                restaurantList.context,
                DividerItemDecoration.VERTICAL
            )
        )
        restaurantList.collapsingToolbarLayout = toolbar_layout
        restaurantList.params = toolbar_layout.layoutParams as AppBarLayout.LayoutParams
        restaurantList.emptyView = customView
        restaurantList.wrapperView = nestedContent
        val locationViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
                .create(LocationViewModel::class.java)

        val restaurantViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
                .create(RestaurantViewModel::class.java)
        placesCallBack = PlacesCallBack(restaurantViewModel, this)
        restaurantList.adapter = RestaurantAdapter(restaurantViewModel, this)
        restaurantList.layoutManager = LinearLayoutManager(this)
        customView.imageView.setOnClickListener {
            if (!locationController.enableGPS) {
                locationController.enableGPS = true
            }
            locationController.startLocationService()
        }
        locationController = LocationController(this, locationViewModel)
        locationController.OnLocationRequestSendCallback = {
            customView.showProgressiveBar(getString(R.string.localization))
        }
        fab.setOnClickListener { view ->
            if (mLocation != null) {
                RadiusSetDialog.show(this) {
                    ApiWrapper.Builder(placesCallBack).setLocation(mLocation)
                        .setRadius(it.toString()).build().execute()
                }
            } else {
                Snackbar.make(view, getString(R.string.no_location_prov), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        mBinding.item = null
        locationViewModel.locationLiveData.observe(this, Observer {
            customView.hideProgressiveBar(getString(R.string.location_on))
            mBinding.item = it
            mLocation = it
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationController.REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            locationController.enableGPS = true
            locationController.startLocationService()
        } else if (requestCode == LocationController.REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_CANCELED) {
            locationController.enableGPS = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationController.LOCATION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationController.startLocationService()
        } else if (requestCode == LocationController.LOCATION_CODE && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            locationController.enableGPS = false
        }

    }
}
