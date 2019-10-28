package com.alfastack.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.alfastack.myapplication.databinding.RestaurantListItemBinding
import com.alfastack.myapplication.viewmodel.RestaurantViewModel
import com.alfastack.placesapiwrapper.models.Restaurant

/**
 * Created by Joro on 28/10/2019
 */
class RestaurantAdapter(
    private val viewModel: RestaurantViewModel,
    fragmentActivity: FragmentActivity
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>(), LifecycleObserver {

    private var items: List<Restaurant>? = null
    private val observer = Observer<List<Restaurant>> {
        items = it
        notifyDataSetChanged()
    }

    init {
        fragmentActivity.lifecycle.addObserver(this)
        viewModel.restaurants.observe(fragmentActivity, observer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
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

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun deallocateObservers() {
        viewModel.restaurants.removeObserver(observer)
    }
}