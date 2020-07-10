package com.example.booster.ui


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.fragment.app.DialogFragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.booster.R
import kotlinx.android.synthetic.main.dialog_store_file_option_num.*

class StoreFileOptionNumFragment : DialogFragment() {

    private var mCallback: FragmentToActivity? = null

    private var printOption: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_store_file_option_num, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        order_dialog_btn_close_black.setOnClickListener {
            dismiss()
        }

        order_option_btn_minus.setOnClickListener {

            printOption = edt_printNum.text.toString().toInt() - 1
            if(printOption<1){
                Toast.makeText(context, "다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                printOption = 1
            }
            else{
                edt_printNum.setText(printOption.toString())
            }
        }

        order_option_btn_plus.setOnClickListener {
            printOption = edt_printNum.text.toString().toInt() + 1
            edt_printNum.setText(printOption.toString())
        }

        dial_store_file_option_num.setOnClickListener {
            sendData("${printOption}")
            dismiss()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCallback = try {
            context as FragmentToActivity
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + " must implement FragmentToActivity"
            )
        }
    }

    override fun onDetach() {
        mCallback = null;
        super.onDetach();
    }

    fun onRefresh() {
        Toast.makeText(getActivity(), "Fragment : Refresh called.",
            Toast.LENGTH_SHORT).show();
    }

    fun sendData(num:String)
    {
        mCallback!!.communicateNum(num);

    }
}
