package com.example.booster.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.booster.R

class CustomDialog(context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_item_view)

    }
}