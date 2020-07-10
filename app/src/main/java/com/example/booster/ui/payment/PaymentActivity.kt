package com.example.booster.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}
