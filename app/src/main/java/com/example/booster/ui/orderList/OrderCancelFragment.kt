package com.example.booster.ui.orderList

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.booster.R
import com.example.booster.data.datasource.model.DefaultData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.listener.onlyOneClickListener
import kotlinx.android.synthetic.main.dialog_order_cancel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderCancelFragment : DialogFragment() {

    val requestToServer = BoosterServiceImpl
    private lateinit var viewModel: OrderListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_order_cancel, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(this).get(OrderListViewModel::class.java)
//        dialog_order_cancel_back.onlyOneClickListener {
//            dismiss()
//        }
//
//        val args = arguments
//        val orderIdx = args?.getInt("idx")
//
//        dialog_order_cancel_cancel.onlyOneClickListener {
//
//            if (orderIdx != null) {
//                requestToServer.service.deleteOrder(
//                    orderIdx
//                ).enqueue(object : Callback<DefaultData> {
//                    override fun onFailure(call: Call<DefaultData>, t: Throwable) {
//                        //통신 실패
//                        Log.e("orderlistdelete", "통신 실패")
//                    }
//
//                    override fun onResponse(
//                        call: Call<DefaultData>,
//                        response: Response<DefaultData>
//                    ) {
//                        if (response.isSuccessful) {
//                            Log.e("주문 취소 성공", "주문 취소")
//                            (requireParentFragment() as OrderListFragment).viewModel.getOrderList()
//                            dismiss()
//                        }
//
//                    }
//
//                })
//            }
//
//            dismiss()
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderListViewModel::class.java)
        dialog_order_cancel_back.onlyOneClickListener {
            dismiss()
        }

        val args = arguments
        val orderIdx = args?.getInt("idx")

        dialog_order_cancel_cancel.onlyOneClickListener {

            if (orderIdx != null) {
                requestToServer.service.deleteOrder(
                    orderIdx
                ).enqueue(object : Callback<DefaultData> {
                    override fun onFailure(call: Call<DefaultData>, t: Throwable) {
                        //통신 실패
                        Log.e("orderlistdelete", "통신 실패")
                    }

                    override fun onResponse(
                        call: Call<DefaultData>,
                        response: Response<DefaultData>
                    ) {
                        if (response.isSuccessful) {
                            Log.e("주문 취소 성공", "주문 취소")
                            (requireParentFragment() as OrderListFragment).viewModel.getOrderList()
//                            dismiss()
                        }

                    }

                })
            }

            dismiss()
        }
    }
}