package com.example.booster.ui.orderList

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2nd_seminar.ui.ItemDecorator
import com.example.booster.R
import com.example.booster.databinding.FragmentOrderListBinding
import kotlinx.android.synthetic.main.fragment_order_list.*

class OrderListFragment : Fragment() {

    private lateinit var viewModel: OrderListViewModel
    lateinit var adapter: OrderListAdapter
    lateinit var binding: FragmentOrderListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_order_list, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_list, container, false)
        binding.lifecycleOwner = this
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderListViewModel::class.java)
        binding.vm = (this@OrderListFragment).viewModel

        initRv()
        viewModel.getOrderList()
    }

    private fun initRv() {
        adapter = OrderListAdapter(requireContext(),
            object : OrderListViewHolder.onClickPickUpListener {
                override fun onClickPickUp(position: Int, textView: TextView, orderIdx: Int) {
                    Log.e("orderIdx -> ", orderIdx.toString())
                    viewModel.putPickUp(orderIdx)
                    Handler().postDelayed({ viewModel.getOrderList() }, 500)
                }
            })

        frag_order_condition_rv.adapter = adapter
        frag_order_condition_rv.layoutManager = LinearLayoutManager(requireContext())
        frag_order_condition_rv.addItemDecoration(ItemDecorator(24))

        viewModel.orderList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }
}
