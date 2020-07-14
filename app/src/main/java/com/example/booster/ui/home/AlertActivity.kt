package com.example.booster.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.AlertData
import com.example.booster.data.datasource.model.AlertDataInfo
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.ui.fileStorage.MarginItemDecoration
import kotlinx.android.synthetic.main.activity_alert.*
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
                loadAlert()
            }

        })

        alertAdapter = AlertAdapter(this,
            object : AlertViewHolder.onClickAlertListener {
                override fun onClickAlert(position: Int) {
                    item_alert_img.setImageResource(R.drawable.notice_ic_alarm_inactive)
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
        for(i in 0 .. datas.size-1){
            alertAdapter.data.add(datas[i])
        }
        alertAdapter.notifyDataSetChanged()
    }
}