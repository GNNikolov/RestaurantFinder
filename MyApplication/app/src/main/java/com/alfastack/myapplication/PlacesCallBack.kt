package com.alfastack.myapplication

import androidx.fragment.app.FragmentActivity
import com.alfastack.myapplication.viewmodel.RestaurantViewModel
import com.alfastack.placesapiwrapper.callbacks.ApiCallback
import com.alfastack.placesapiwrapper.models.Restaurant
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*

/**
 * Created by Joro on 01/11/2019
 */
class PlacesCallBack(
    private val viewModel: RestaurantViewModel,
    private val activityContext: FragmentActivity
) : ApiCallback() {
    override fun onPreExecute() {
        super.onPreExecute()
        activityContext.customView.showProgressiveBar("Please wait...")
    }

    override fun onFetched(data: MutableList<Restaurant>?) {
        activityContext.customView.hideProgressiveBar()
        viewModel.restaurants.value = data
    }

    override fun onFetchFailed(message: String?) {
        message?.let {
            Snackbar.make(activityContext.coordinator, "Error: $it", Snackbar.LENGTH_LONG).show()
            return
        }
        Snackbar.make(activityContext.coordinator, "Unexpected error", Snackbar.LENGTH_LONG).show()
        activityContext.customView.hideProgressiveBar(activityContext.getString(R.string.location_on))
    }
}