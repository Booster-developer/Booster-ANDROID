package com.example.booster.ui.orderDetail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.booster.R
import com.example.booster.listener.onlyOneClickListener
import kotlinx.android.synthetic.main.dialog_order_detail_img.*

class OrderDetailImgFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_order_detail_img, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments

        dialog_order_detail_img_close.onlyOneClickListener {
            dismiss()
        }

        if (position != null) {
            Glide.with(view.context).load(position.getString("thumbnail")).into(dialog_order_detail_img)
        }
    }
}
