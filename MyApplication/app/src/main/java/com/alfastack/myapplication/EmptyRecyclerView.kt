package com.alfastack.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED

/**
 * Created by Joro on 27/10/2019
 */
class EmptyRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {
    var emptyView: View? = null
    var params: AppBarLayout.LayoutParams ?= null

    private val dataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            val mAdapter = adapter
            if (mAdapter != null && emptyView != null) {
                if (mAdapter.itemCount == 0) {
                    emptyView?.visibility = View.VISIBLE
                    visibility = View.GONE
                    params?.scrollFlags = 0
                } else {
                    emptyView?.visibility = View.GONE
                    visibility = View.VISIBLE
                    params?.scrollFlags = SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(dataObserver)
        dataObserver.onChanged()
    }
}