package com.example.booster.ui.fileStorage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.booster.R
import com.example.booster.listener.onlyOneClickListener
import kotlinx.android.synthetic.main.dialog_store_file_option_range.*

class StoreFileOptionRangeFragment : DialogFragment() {

    private var mCallback: FragmentToActivity? = null

    private var printOption: String = "전체"
    var printMinNum = 0
    var printMaxNum = 0

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


        val args = arguments
        val fileType = args?.getString("fileType")
        //default로 전체
        order_option_btn_whole1.isSelected = true
        edt_printMinNum.isEnabled = false  // 전체범위 선택됐을 경우 범위 선택 불가 default
        edt_printMaxNum.isEnabled = false

        Log.e("what is argssss", "check: " + args)
        Log.e("what is this filetype", "check: " + fileType)
        if (fileType != ".pdf") {
            order_option_btn_whole2.isEnabled = false
        }

        order_dialog_btn_close_black.onlyOneClickListener {
            printOption = "all"
            dismiss()
        }

        order_option_btn_whole1.onlyOneClickListener {

            optionReset()
            order_option_btn_whole1.isSelected = !order_option_btn_whole1.isSelected
            if (order_option_btn_whole1.isSelected) {
                printOption = "all"
            } else optionReset()
            edt_printMinNum.isEnabled = false  // 전체범위 선택됐을 경우 범위 선택 불가
            edt_printMaxNum.isEnabled = false
        }

        order_option_btn_whole2.onlyOneClickListener {
            optionReset()
            order_option_btn_whole2.isSelected = !order_option_btn_whole2.isSelected
            if (order_option_btn_whole2.isSelected) {
                printOption = "part"
            } else optionReset()
            edt_printMinNum.isEnabled = true
            edt_printMaxNum.isEnabled = true
        }

        dial_store_file_option_range.onlyOneClickListener {
            //완료버튼

            printMinNum = if(edt_printMinNum.text.isNullOrBlank()){ 0 } else edt_printMinNum.text.toString().toInt()
            printMaxNum = if(edt_printMaxNum.text.isNullOrBlank()){0} else edt_printMaxNum.text.toString().toInt()

            if(printOption == "all"){
                sendData("전체", 0, 0)
                dismiss()
            }
            else if(printOption == "part"){
                if(edt_printMaxNum.text.isNullOrBlank() || edt_printMinNum.text.isNullOrBlank()){
                    Toast.makeText(context, "범위를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(printMaxNum < printMinNum){
                        Toast.makeText(context, "범위를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }else{
                        sendData("부분 ", printMinNum , printMaxNum)
                        dismiss()
                    }
                }
            }
            else{
                sendData("전체", 0, 0)
                dismiss()
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

    fun optionReset() {
        printOption = ""
        order_option_btn_whole1.isSelected = false
        order_option_btn_whole2.isSelected = false
    }
}
