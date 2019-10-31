package com.alfastack.myapplication.customviews

import android.content.Context
import android.location.Location
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alfastack.myapplication.R

/**
 * Created by Joro on 27/10/2019
 */
class StatusView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var progressBar = ProgressBar(context)
    var imageView: ImageView = ImageView(context)
    var locationMessage: TextView = TextView(context)
    private val mColor = ContextCompat.getColor(context,
        R.color.darkGray
    )
    private val imgSize = context.resources.getDimension(R.dimen.img_size).toInt()
    
    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        orientation = VERTICAL
        gravity = Gravity.CENTER_VERTICAL
        addView(progressBar)
        addView(imageView)
        addView(locationMessage)
        progressBar.visibility = View.GONE
        adjustComponents()
    }

    private fun adjustComponents() {
        imageView.setImageDrawable(context.getDrawable(R.drawable.ic_action_name))
        imageView.setColorFilter(mColor)
        imageView.layoutParams = LayoutParams(imgSize, imgSize)
        val params = LayoutParams(imgSize, imgSize).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }
        imageView.layoutParams = params
        val textParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }
        locationMessage.layoutParams = textParams
        locationMessage.setTextColor(mColor)
        locationMessage.text = context.getString(R.string.location_off)
        locationMessage.textAlignment = View.TEXT_ALIGNMENT_CENTER
        locationMessage.textSize = 19f
    }

    fun updateViews(mLocation: Location?) {
        val mDrawable =
            if (mLocation == null) imageView.context.getDrawable(R.drawable.ic_action_name)
            else imageView.context.getDrawable(R.drawable.ic_loc_on)
        imageView.setImageDrawable(mDrawable)
        if (mLocation != null) {
            locationMessage.text = context.getString(R.string.location_on)
        } else {
            locationMessage.text = context.getString(R.string.location_off)
        }
    }

    fun showProgressiveBar() {
        imageView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        locationMessage.text = context.getString(R.string.localization)
    }

    fun hideProgressiveBar() {
        if (progressBar.visibility == View.VISIBLE) {
            imageView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            locationMessage.text = context.getString(R.string.location_on)
        }
    }

}