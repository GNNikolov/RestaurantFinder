package com.alfastack.myapplication.dialogfragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.alfastack.myapplication.R

/**
 * Created by Joro on 17/12/2019
 */
class ConnectionLostDialog : DialogFragment() {
    private var title: String = "No connection!"
    private var message: String = "Please check or enable your connection..."

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setIcon(R.drawable.ic_error)
            .setMessage(message)
            .setCancelable(false)
        return mDialog.create()
    }

    fun show(fragmentManager: FragmentManager, title: String, message: String) {
        this.title = title
        this.message = message
        isCancelable = false
        show(fragmentManager, "DIALOG_NO_CONNECTION")
    }

}