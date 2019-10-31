package com.alfastack.myapplication.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.alfastack.myapplication.customviews.DialogTextView

/**
 * Created by Joro on 31/10/2019
 */
class RadiusSetDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.alfastack.myapplication.R.layout.seek_bar_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(activity!!)
        alertDialogBuilder.setCustomTitle(DialogTextView(context!!))
        alertDialogBuilder.setView(com.alfastack.myapplication.R.layout.seek_bar_dialog)
        return alertDialogBuilder.create()
    }

    companion object {
        fun show(supportFragmentManager: FragmentManager) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val prev = supportFragmentManager.findFragmentByTag("dialog")
            if (prev != null) {
                fragmentTransaction.remove(prev)
            }
            fragmentTransaction.addToBackStack(null)
            val dialogFragment = RadiusSetDialog()
            dialogFragment.show(fragmentTransaction, "dialog")
        }
    }
}