package com.alfastack.myapplication

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alfastack.myapplication.controllers.LocationController
import com.alfastack.myapplication.viewmodel.LocationViewModel
import com.alfastack.placesapiwrapper.ApiWrapper
import com.alfastack.placesapiwrapper.callbacks.ApiCallback
import com.alfastack.placesapiwrapper.models.Restaurant
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {
    private lateinit var locationController: LocationController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
                .create(LocationViewModel::class.java)
        locationController = LocationController(this, locationViewModel)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        locationController.startLocationService()
        locationViewModel.locationLiveData.observe(this, Observer {
            Log.i("Location", it.toString())
        })
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
