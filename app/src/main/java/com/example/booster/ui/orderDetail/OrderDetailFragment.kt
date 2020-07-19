package com.example.booster.ui.orderDetail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.example.booster.R
import com.example.booster.listener.onlyOneClickListener
import kotlinx.android.synthetic.main.dialog_item_view.*

class OrderDetailFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val color = mArgs!!.getString("fileColor")
        val direction = mArgs.getString("fileDir")
        val side = mArgs.getString("fileSide")
        val collect = mArgs.getString("fileCollect")
        val start = mArgs.getString("fileStart")
        val end = mArgs.getString("fileEnd")
        val copy = mArgs.getString("fileCopy")

        dial_item_view_tv_color2.text = color
        dial_item_view_tv_orientation2.text = direction
        dial_item_view_tv_sided2.text = side
        dial_item_view_tv_multiple2.text = collect
        if(start=="0"&&end=="0"){
            dial_item_view_tv_partial2.text ="전체"
        }else dial_item_view_tv_partial2.text = start + " ~ " + end + "p"

        dial_item_view_tv_number2.text = copy

        dial_item_view_close.onlyOneClickListener {
            dismiss()
        }

    }
}
