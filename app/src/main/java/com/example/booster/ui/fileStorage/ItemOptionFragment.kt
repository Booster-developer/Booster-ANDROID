package com.example.booster.ui.fileStorage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.booster.R
import kotlinx.android.synthetic.main.dialog_item_view.*

class ItemOptionFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_item_view, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mArgs = arguments
        val fileColor = mArgs!!.getString("fileColor")
        val fileDir = mArgs.getString("fileDir")
        Log.e("frag file info -> ", fileColor+ " "+fileDir)

        dial_item_view_tv_color2.text = fileColor
        dial_item_view_tv_orientation2.text = fileDir


        dial_item_view_close.setOnClickListener {
            dismiss()
        }

    }
}
