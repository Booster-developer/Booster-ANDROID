package com.example.booster.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.booster.R
import kotlinx.android.synthetic.main.dialog_store_file_option_range.*

class StoreFileOptionRangeFragment : DialogFragment() {

    private var mCallback: FragmentToActivity? = null

    private var printOption: String = "0"
    private var printMinNum: String = "0"
    private var printMaxNum: String = "0"




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_store_file_option_range, container, false)
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

        order_option_btn_whole1.setOnClickListener {
            order_option_btn_whole1.isSelected = !order_option_btn_whole1.isSelected
            printOption = "all"
        }

        order_option_btn_whole2.setOnClickListener {
            order_option_btn_whole2.isSelected = !order_option_btn_whole2.isSelected
            printOption= "part"
        }

        dial_frag_file_option.setOnClickListener {
            //완료버튼
            printMinNum = edt_printMinNum.text.toString()
            printMaxNum = edt_printMaxNum.text.toString()


            if(printOption == "all"){
                sendData("전체", 0, 0)
                dismiss()
            }
            else if(printOption == "part"){
                if(printMinNum.isNullOrBlank() || printMaxNum.isNullOrBlank()){
                    Toast.makeText(context, "범위를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(printMaxNum<printMinNum){
                        Toast.makeText(context, "범위를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }else{
                        sendData("부분 ", printMinNum.toInt() , printMaxNum.toInt())
                        dismiss()
                    }
                }
            }
            else{
                Toast.makeText(context, "범위를 선택해주세요.", Toast.LENGTH_LONG).show()
            }
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

    fun sendData(range:String, min:Int, max:Int)
    {
        mCallback!!.communicateRange(range, min, max);

    }

}

