package com.alfastack.myapplication.viewmodel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.alfastack.myapplication.databinding.SeekBarDialogBinding

class DialogViewModel(private val seekBarDialogBinding: SeekBarDialogBinding) : BaseObservable() {

    init {
        seekBarDialogBinding.vModel = this
    }

    var radius: Int = 5
        @Bindable
        get
        set(value) {
            field = value
            seekBarDialogBinding.textRadius.text = String.format("%d(meters)", field)
        }

    var keyword: String? = null
        @Bindable
        get
        set(value) {
            field = value
            Log.i("KeywordInput", field.toString())
        }
}