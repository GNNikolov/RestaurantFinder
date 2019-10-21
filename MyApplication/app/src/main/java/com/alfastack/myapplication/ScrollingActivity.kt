package com.alfastack.myapplication

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.alfastack.placesapiwrapper.ApiHandler
import com.alfastack.placesapiwrapper.callbacks.ApiCallback
import com.alfastack.placesapiwrapper.models.Restaurant
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        ApiHandler(object : ApiCallback{
            override fun onFetched(data: MutableList<Restaurant>?) {
                data?.let {
                    for (item in it){
                        Log.i("Fetched", item.toString())
                    }
                }
            }

            override fun onPreExecute() {

            }

            override fun onFetchFailed(message: String?) {
                Log.i("Finished", message.toString())
            }

        }).execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=42.654643,%2023.349079&radius=600&type=restaurant&key=AIzaSyD9DpsJqq_TeWBHy1Hfir0B2C2rY4pmvYY")

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
