package com.example.booster.ui.storeList

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.booster.R
import com.example.booster.listener.onlyOneClickListener
import kotlinx.android.synthetic.main.dialog_fragment_store_list.*


class  StoreListDialogFragment : DialogFragment(){

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

        dial_frag_store_list_cl_univ_1.onlyOneClickListener {
//            sendData("숭실대")

            val bundle = Bundle()
            bundle.putInt("univIdx", 1)
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, 1, intent)
            dismiss()
        }
        dial_frag_store_list_cl_univ_2.onlyOneClickListener {

//            sendData("중앙대")

            val bundle = Bundle()
            bundle.putInt("univIdx", 2)
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, 1, intent)
            dismiss()

        }
        dial_frag_store_list_cl_univ_3.onlyOneClickListener {
//            sendData("서울대")

            val bundle = Bundle()
            bundle.putInt("univIdx", 3)
            val intent = Intent()
            intent.putExtras(bundle)
            targetFragment!!.onActivityResult(targetRequestCode, 1, intent)
            dismiss()
        }

        dial_frag_store_list_tv_close.onlyOneClickListener {
            dialog!!.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        mCallback = try {
//            context as UnivInfoToFrag
//        } catch (e: ClassCastException) {
//            throw RuntimeException(context!!.toString() + " must implement FragmentEvent")
//
//        }
    }
}

