package com.alfastack.myapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alfastack.myapplication.databinding.RestaurantListItemBinding
import com.alfastack.myapplication.viewmodel.RestaurantViewModel
import com.alfastack.placesapiwrapper.models.Restaurant

/**
 * Created by Joro on 28/10/2019
 */
class RestaurantAdapter(
    private var viewModel: RestaurantViewModel,
    private val fragmentActivity: FragmentActivity
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    private var items: List<Restaurant>? = null

    init {
        viewModel.restaurants.observe(fragmentActivity, Observer {
            items = it
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        Log.i("MSize", "called")
        val binding = RestaurantListItemBinding.inflate(LayoutInflater.from(parent.context))
        return RestaurantViewHolder(binding)
    }

    override fun getItemCount(): Int = items?.size ?: 0


    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val item = items?.get(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class RestaurantViewHolder(private val binding: RestaurantListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.data = restaurant
            binding.executePendingBindings()
        }
    }
}