package com.example.booster.ui.payment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.booster.ui.orderList.OrderListAdapter
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.fragment_order_list.*

class PaymentActivity : AppCompatActivity() {

    lateinit var binding : ActivityPaymentBinding
    lateinit var viewModel: PaymentViewModel
    lateinit var adapter: PaymentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.getPaymentInfo(1)
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
    }
}
