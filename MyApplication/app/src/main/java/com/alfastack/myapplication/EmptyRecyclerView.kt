package com.alfastack.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * Created by Joro on 27/10/2019
 */
class EmptyRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    var emptyView: LocationComponents? = null
        set(value) {
            field = value
            initEmptyView()
        }

    var params: AppBarLayout.LayoutParams? = null

    var collapsingToolbarLayout: CollapsingToolbarLayout? = null

    var wrapperView: LinearLayout? = null

    private val dataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            initEmptyView()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            initEmptyView()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            initEmptyView()
        }
    }

    private fun initEmptyView() {
        if (adapter != null && emptyView != null) {
            if (adapter?.itemCount == 0) {
                emptyView?.visibility = View.VISIBLE
                visibility = View.GONE
                params?.scrollFlags = 0
                collapsingToolbarLayout?.layoutParams = params
                adjustGravity(Gravity.CENTER_VERTICAL)
            } else {
                emptyView?.visibility = View.GONE
                visibility = View.VISIBLE
                params?.scrollFlags = SCROLL_FLAG_ENTER_ALWAYS or SCROLL_FLAG_SCROLL
                collapsingToolbarLayout?.layoutParams = params
                adjustGravity(Gravity.TOP)
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        super.setAdapter(adapter)
        oldAdapter?.unregisterAdapterDataObserver(dataObserver)
        adapter?.registerAdapterDataObserver(dataObserver)
        initEmptyView()
    }

    private fun adjustGravity(param: Int) {
        wrapperView?.let {
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            ).apply {
                gravity = param
            }
            it.layoutParams = params
        }
    }
}