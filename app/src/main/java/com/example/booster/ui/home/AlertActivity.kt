package com.example.booster.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.AlertData
import com.example.booster.data.datasource.model.AlertDataInfo
import com.example.booster.data.datasource.model.NoticeData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.ui.bottomtap.BottomTabActivity
import com.example.booster.ui.fileStorage.MarginItemDecoration
import kotlinx.android.synthetic.main.activity_alert.*
import kotlinx.android.synthetic.main.activity_bottom_tab.*
import kotlinx.android.synthetic.main.item_alert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlertActivity : AppCompatActivity() {

    val requestToServer = BoosterServiceImpl
    lateinit var alertAdapter: AlertAdapter
    var datas = ArrayList<AlertDataInfo>()
    lateinit var alertResponse: AlertData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)

        requestToServer.service.getAlertList().enqueue(object : Callback<AlertData>{
            override fun onFailure(call: Call<AlertData>, t: Throwable) {
                //통신 실패
                Log.e("AlertActivity", "통신 실패")
            }

            override fun onResponse(call: Call<AlertData>, response: Response<AlertData>) {
                alertResponse = response.body()!!
                if (response.body()!!.data!=null) loadAlert()
            }

        })

        alertAdapter = AlertAdapter(this,
            object : AlertViewHolder.onClickAlertListener {
                override fun onClickAlert(position: Int) {
                    if(datas[position].notice_confirm==1){
                        read(position)
                        requestToServer.service.checkNotice(
                            orderIdx = datas[position].notice_idx
                        ).enqueue(object : Callback<NoticeData>{
                            override fun onFailure(call: Call<NoticeData>, t: Throwable) {
                                //통신 실패
                                Log.e("alertPutError", "통신 실패")
                            }

                            override fun onResponse(
                                call: Call<NoticeData>,
                                response: Response<NoticeData>
                            ) {
                                //통신 성공
                                Log.e("alertPutSuccess", response.body().toString())
                            }

                        })

                        val intent = Intent(this@AlertActivity, BottomTabActivity::class.java)
                        intent.putExtra("alertFlag", 1)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        )

        act_alert_rv.adapter = alertAdapter
        act_alert_rv.layoutManager = LinearLayoutManager(this)
        act_alert_rv.addItemDecoration(
            MarginItemDecoration(
                0,
                resources.getDimensionPixelSize(R.dimen.paddingItemDecorationDefault)
            )
        )

    }

    fun loadAlert(){
        datas = alertResponse.data
        for(i in 0 until datas.size){
            alertAdapter.data.add(datas[i])
        }
        Log.e("alert", datas.toString())
        alertAdapter.notifyDataSetChanged()

    }

    fun read(position: Int){
        datas[position].notice_confirm = 0
        Log.e("alertname", datas[position].store_name)
        alertAdapter.notifyItemChanged(position)
    }
}