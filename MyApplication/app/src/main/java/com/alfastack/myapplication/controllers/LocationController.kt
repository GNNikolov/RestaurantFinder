package com.alfastack.myapplication.controllers

import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.alfastack.myapplication.viewmodel.LocationViewModel
import com.google.android.gms.location.*

class LocationController(private val fragmentActivity: FragmentActivity, private val locationViewModel: LocationViewModel) : LifecycleObserver {
    private val fusedLocationClient: FusedLocationProviderClient =
        FusedLocationProviderClient(fragmentActivity)
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationAvailability(p0: LocationAvailability?) {
            super.onLocationAvailability(p0)
        }

        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            p0?.let {
                for (location in p0.locations) {
                    locationViewModel.locationLiveData.value = location
                }
            }

        }
    }

    val mPermissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    init {
        fragmentActivity.lifecycle.addObserver(this)
    }

    fun isPermissionGranted(): Boolean {
        for (permission in mPermissions) {
            if (ContextCompat.checkSelfPermission(
                    fragmentActivity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startLocationService() {
        if (isPermissionGranted()) {
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = MIN_INTERVAL
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            ActivityCompat.requestPermissions(fragmentActivity, mPermissions, LOCATION_CODE)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeCallbacks() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        const val LOCATION_CODE = 101
        const val MIN_INTERVAL: Long = 1500
    }


}