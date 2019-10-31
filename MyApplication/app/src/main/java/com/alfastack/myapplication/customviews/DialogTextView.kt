package com.alfastack.myapplication.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alfastack.myapplication.R
/**
 * Created by Joro on 31/10/2019
 */
class DialogTextView(context: Context, attributeSet: AttributeSet?) : TextView(context, attributeSet) {
    constructor(context: Context) : this(context, null)

    init {
        text = context.getString(R.string.dialog_radius_title)
        setPadding(20, 30, 20, 30)
        textSize = 20f
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        setTextColor(Color.WHITE)
    }
}