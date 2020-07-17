package com.example.booster.ui.payment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2nd_seminar.ui.ItemDecorator
import com.example.booster.R
import com.example.booster.databinding.ActivityPaymentBinding
import com.example.booster.ui.bottomtap.BottomTabActivity
import com.example.booster.ui.home.HomeActivity
import com.example.booster.ui.orderList.OrderListAdapter
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.fragment_order_list.*

class PaymentActivity : AppCompatActivity() {

    lateinit var binding : ActivityPaymentBinding
    lateinit var viewModel: PaymentViewModel
    lateinit var adapter: PaymentAdapter

    var orderIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        orderIdx = intent.getIntExtra("order_idx", orderIdx)
        Log.e("orderIdxpay", orderIdx.toString())

        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.getPaymentInfo(orderIdx)
        initRv()
        setClick()
    }

    private fun initRv(){
        adapter = PaymentAdapter(this)
        act_payment_rv.adapter = adapter
        act_payment_rv.layoutManager = LinearLayoutManager(this)
        act_payment_rv.addItemDecoration(ItemDecorator(15))


        viewModel.paymentInfo.observe(this, Observer {
            adapter.data = it.data.fileOption
            Log.e("patment1", it.data.toString())
            act_payment_tv_name.text =it.data.store_name
            act_payment_tv_order_idx.text = orderIdx.toString()


            Log.e("patment2", it.data.fileOption.toString())
            adapter.notifyDataSetChanged()
        })
    }

    fun setClick(){
        act_payment_et_req.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_payment_et_req.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })

        act_payment_et_req.setOnFocusChangeListener { view, b ->
            if(b){
                view.setBackgroundResource(R.drawable.border_304fff_1dp)
            }else{
                view.setBackgroundResource(R.drawable.border_bbbbbb_1dp)
            }
        }


        //결제완료하고 주문현황 넘어가기
        act_payment_btn_pay.setOnClickListener {
            val intent = Intent(this, BottomTabActivity::class.java)
            intent.putExtra("orderIdx", orderIdx)
            startActivity(intent)
        }

        act_payment_iv_back.setOnClickListener{
            finish()
        }
    }
}
