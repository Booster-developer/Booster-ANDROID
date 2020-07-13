package com.example.booster.ui.storeList

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.booster.R
import kotlinx.android.synthetic.main.dialog_fragment_store_list.*
import kotlinx.android.synthetic.main.fragment_store_list.*

class  StoreListDialogFragment : DialogFragment(){

    private var mCallback: UnivInfoToFrag? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_store_list, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dial_frag_store_list_cl_univ_1.setOnClickListener {
            sendData("숭실대")
            Log.e("univSendData", "1")
            dismiss()
        }
        dial_frag_store_list_cl_univ_2.setOnClickListener {

            sendData("중앙대")
            Log.e("univSendData", "2")
            dismiss()

        }
        dial_frag_store_list_cl_univ_3.setOnClickListener {
            sendData("서울대")
            Log.e("univSendData", "3")
            dismiss()

        }

        dial_frag_store_list_tv_close.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCallback = try {
            context as UnivInfoToFrag
        } catch (e: ClassCastException) {
            throw RuntimeException(context!!.toString() + " must implement FragmentEvent")

        }
    }

    override fun onDetach() {
        mCallback = null;
        super.onDetach();
    }

    private fun sendData(univ: String){
        mCallback?.communicateUniv(univ);
    }
}

