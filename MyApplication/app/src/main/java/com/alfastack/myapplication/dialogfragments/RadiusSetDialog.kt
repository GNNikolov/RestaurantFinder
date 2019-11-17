package com.alfastack.myapplication.dialogfragments

import android.app.Dialog
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.alfastack.myapplication.R
import com.alfastack.myapplication.customviews.DialogTextView
import com.alfastack.myapplication.databinding.SeekBarDialogBinding
import com.alfastack.myapplication.viewmodel.DialogViewModel

/**
 * Created by Joro on 31/10/2019
 */
object RadiusSetDialog {

    fun show(context: FragmentActivity, clickOk: (radius: Int, keyword: String?) -> Unit) {
        val binding = DataBindingUtil.inflate<SeekBarDialogBinding>(
            LayoutInflater.from(context),
            R.layout.seek_bar_dialog,
            null,
            false
        )
        createDialog(DialogViewModel(binding) ,context, binding, clickOk).show()
    }

    private fun createDialog(
        dialogViewModel: DialogViewModel,
        context: FragmentActivity,
        seekBarDialogBinding: SeekBarDialogBinding,
        clickOk: (radius: Int, keyword: String?) -> Unit
    ): Dialog {
        val alertDialog = AlertDialog.Builder(context)
            .setCustomTitle(DialogTextView(context))
            .setView(seekBarDialogBinding.root)
        val dialog = alertDialog.create()
        seekBarDialogBinding.buttonOk.setOnClickListener {
            clickOk(dialogViewModel.radius, dialogViewModel.keyword)
            dialog.dismiss()
        }
        return dialog
    }

}