package com.example.booster.ui.orderList

import android.content.DialogInterface
import android.content.Intent
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
import com.example.booster.ui.orderDetail.OrderDetailActivity
import kotlinx.android.synthetic.main.fragment_order_list.*


class OrderListFragment : Fragment(), DialogInterface.OnDismissListener {
    override fun onDismiss(dialog: DialogInterface?) {
        viewModel.getOrderList()
    }

    val orderCancelDialog = OrderCancelFragment()

    lateinit var viewModel: OrderListViewModel
    lateinit var adapter: OrderListAdapter
    lateinit var binding: FragmentOrderListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val rootView = inflater.inflate(R.layout.fragment_order_list, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderListViewModel::class.java)

        initRv()
        viewModel.getOrderList()
        viewModel.orderInfo.observe(viewLifecycleOwner, Observer {
            binding.fragOrderListTvName.text = it.user_name
            binding.fragOrderListTvCount.text = it.booster_count.toString()
        })
        binding.vm = (this@OrderListFragment).viewModel
        refresh()

//        binding.fragOrderListTvName.text = viewModel.orderInfo.value!!.user_name
    }

    override fun onResume() {
        super.onResume()
        Log.e("orderListFrag", "onResume")
        viewModel.getOrderList()
    }

    private fun refresh() {
        frag_order_list_srl.apply {
            setOnRefreshListener {
                viewModel.getOrderList()
                this@apply.isRefreshing = false
            }
        }
    }

    private fun initRv() {
        adapter = OrderListAdapter(requireContext(),
            object : OrderListViewHolder.onClickPickUpListener {
                override fun onClickPickUp(position: Int, textView: TextView, orderIdx: Int) {
                    Log.e("orderIdx -> ", orderIdx.toString())
//                    viewModel.putPickUp(orderIdx)
                    viewModel.putPickUp(orderIdx)
                    Handler().postDelayed({ viewModel.getOrderList() }, 1000)
                }
            }, object : OrderListViewHolder.onClickDetailListener {
                override fun onClickDetail(position: Int) {
                    val intent = Intent(context, OrderDetailActivity::class.java)
                    intent.putExtra(
                        "idx",
                        viewModel.orderList.value!![position].order_idx.toString()
                    )
                    startActivity(intent)
                }
            }, object : OrderListViewHolder.onClickCancelListener {
                override fun onCancel(position: Int) {
                    val idx = viewModel.orderList.value!![position].order_idx
                    var bundle = Bundle()
                    bundle.putInt("idx", idx)
                    orderCancelDialog.arguments = bundle
                    orderCancelDialog.show(childFragmentManager, "dialog")

                }
            })


        frag_order_condition_rv.adapter = adapter
        frag_order_condition_rv.layoutManager = LinearLayoutManager(requireContext())
        frag_order_condition_rv.addItemDecoration(ItemDecorator(24))

        viewModel.orderList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.e("viewModel.orderList.observe", "오오오오오ㅗ오오오오ㅗ오옹")

                adapter.data = it
                adapter.notifyDataSetChanged()
            } else {
                adapter.data.clear()
                adapter.notifyDataSetChanged()
            }
        })
    }

}
