package com.example.booster.ui.orderList

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
import com.example.booster.data.datasource.model.DefaultData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.databinding.FragmentOrderListBinding
import com.example.booster.ui.orderDetail.OrderDetailActivity
import kotlinx.android.synthetic.main.fragment_order_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderListFragment : Fragment() {

    private lateinit var viewModel: OrderListViewModel
    lateinit var adapter: OrderListAdapter
    lateinit var binding: FragmentOrderListBinding

    val requestToServer = BoosterServiceImpl

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

        initRv()
        viewModel.getOrderList()
        viewModel.orderInfo.observe(viewLifecycleOwner, Observer {
            frag_order_list_tv_name.text = it.user_name
            frag_order_list_tv_count.text = it.booster_count.toString()
        })
        binding.vm = (this@OrderListFragment).viewModel
        refresh()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrderList()
    }

    private fun refresh(){
        frag_order_list_srl.apply{
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
                    viewModel.putPickUp(orderIdx)
                    Log.e("orderlistorderidx", orderIdx.toString())
                    Handler().postDelayed({ viewModel.getOrderList() }, 500)
                }

            },
        object : OrderListViewHolder.onClickDetailListener{
            override fun onClickDetail(position: Int) {
                val intent = Intent(context, OrderDetailActivity::class.java)
                intent.putExtra("idx", viewModel.orderList.value!!.get(position)!!.order_idx.toString())
                startActivity(intent)
            }

        },
        object : OrderListViewHolder.onClickCancelListener{
            override fun onCancel(position: Int) {

                val orderCancelDialog = OrderCancelFragment()
                orderCancelDialog.show(
                    childFragmentManager,
                    "file option range fragment"
                )

                viewModel.orderList.value?.get(position)?.order_idx?.let {
                    requestToServer.service.deleteOrder(
                        it
                    ).enqueue(object : Callback<DefaultData>{
                        override fun onFailure(call: Call<DefaultData>, t: Throwable) {
                            //통신 실패
                            Log.e("orderlistdelete", "통신 실패")
                        }

                        override fun onResponse(
                            call: Call<DefaultData>,
                            response: Response<DefaultData>
                        ) {
                            if(response.isSuccessful){
                                Log.e("주문 취소 성공", "${viewModel.orderList.value?.get(position)?.order_idx} 주문 취소")
                            }
                        }

                    })
                }
                Handler().postDelayed({ viewModel.getOrderList() }, 500)
            }

        })

        frag_order_condition_rv.adapter = adapter
        frag_order_condition_rv.layoutManager = LinearLayoutManager(requireContext())
        frag_order_condition_rv.addItemDecoration(ItemDecorator(24))

        viewModel.orderList.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                adapter.data = it
                adapter.notifyDataSetChanged()
            }
        })
    }

}
