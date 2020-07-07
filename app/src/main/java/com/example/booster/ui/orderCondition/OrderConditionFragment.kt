package com.example.booster.ui.orderCondition

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.booster.R
import com.example.booster.ui.storeList.StoreListFragment
import kotlinx.android.synthetic.main.fragment_order_condition.*

class OrderConditionFragment : Fragment() {

    private lateinit var viewModel: OrderConditionViewModel
    lateinit var adapter: OrderConditionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_order_condition, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrderConditionViewModel::class.java)
        initRv()
    }

    private fun initRv(){
        adapter = OrderConditionAdapter(requireContext())
        frag_order_condition_rv.adapter = adapter
        frag_order_condition_rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.orderList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }
}
