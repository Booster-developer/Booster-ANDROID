package com.example.booster.ui.orderDetail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.OrderDetailData
import com.example.booster.data.datasource.model.OrderDetailInfo
import com.example.booster.data.datasource.model.OrderOption
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.ui.fileStorage.MarginItemDecoration
import kotlinx.android.synthetic.main.activity_order_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderDetailActivity : AppCompatActivity() {

    var datas = ArrayList<OrderOption> ()
    lateinit var orderAdapter : OrderAdapter
    val requestToServer = BoosterServiceImpl
    lateinit var orderDetailResponse : OrderDetailInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val idx = intent.getStringExtra("idx")

        requestToServer.service.getOrderDetail(idx.toInt()).enqueue(object : Callback<OrderDetailData>{
            override fun onFailure(call: Call<OrderDetailData>, t: Throwable) {
                //통신 실패
                Log.e("log", "통신 실패")
            }

            override fun onResponse(
                call: Call<OrderDetailData>,
                response: Response<OrderDetailData>
            ) {
                orderDetailResponse = response.body()!!.data
                Log.e("log", orderDetailResponse.toString())
                load()
            }

        })

        orderAdapter = OrderAdapter(this,
                object : OrderDetailViewHolder.onClickOrderItemListener {
                    override fun onClickOrderItem(position: Int) {

                        //item option fragment로 띄우기
                        val args = Bundle()
                        val color = datas[position].file_color
                        val direction = datas[position].file_direction
                        val side = datas[position].file_sided_type
                        val collect = datas[position].file_collect
                        val rangeStart = datas[position].file_range_start
                        val rangeEnd = datas[position].file_range_end
                        val copy = datas[position].file_copy_number
                        args.putString("fileColor", color)
                        args.putString("fileDir", direction)
                        args.putString("fileSide", side)
                        args.putString("fileCollect", collect.toString())
                        args.putString("fileStart", rangeStart.toString())
                        args.putString("fileEnd", rangeEnd.toString())
                        args.putString("fileCopy", copy.toString())

                        val orderOptionDialog = OrderDetailFragment()

                        orderOptionDialog.arguments = args
                        orderOptionDialog.show(
                            supportFragmentManager, "item option fragment"
                        )

                    }
                },
            object : OrderDetailViewHolder.onClickImgListener {
                override fun onClickImg(position: Int) {
                    datas[position]
                    Log.e("position", position.toString())

                    val imgDialog = OrderDetailImgFragment()
                    val args = Bundle()
                    args.putString("thumbnail", datas[position].file_thumbnail_path)
                    imgDialog.arguments = args
                    imgDialog.show(
                        supportFragmentManager, "item option fragment"
                    )
                }

            }
            )

        order_detail_rv.adapter = orderAdapter
        order_detail_rv.layoutManager = LinearLayoutManager(this)
        order_detail_rv.addItemDecoration(
            MarginItemDecoration(
                0,
                resources.getDimensionPixelSize(R.dimen.paddingItemDecorationDefault)
            )
        )

    }


    fun load(){
        act_order_detail_idx.text = "no.  " + orderDetailResponse.order_idx.toString()
        act_order_detail_store_name.text = orderDetailResponse.order_store_name
        if(orderDetailResponse.order_state==1){ //접수됨
            act_order_detail_order_state.text = "접수중"
            act_order_detail_order_state_color.setImageResource(R.drawable.history_detail_ic_order)
        }
        else if(orderDetailResponse.order_state==2){    //인쇄중
            act_order_detail_order_state.text = "인쇄중"
            act_order_detail_order_state_color.setImageResource(R.drawable.history_detail_ic_print)
        }
        else {  //완료됨
            act_order_detail_order_state.text = "완료됨"
            act_order_detail_order_state_color.setImageResource(R.drawable.history_detail_ic_fin)
        }

        act_order_detail_txt_orderTime.text = orderDetailResponse.order_time
        act_order_detail_txt_price.text = orderDetailResponse.order_price.toString() + "원"
        act_order_detail_request.text = orderDetailResponse.order_comment

        datas = orderDetailResponse.order_file_list

        for(i in 0 .. datas.size-1){
            orderAdapter.data.add(datas[i])
        }
        orderAdapter.notifyDataSetChanged()

    }

}



