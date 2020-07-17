package com.example.booster.ui.bottomtap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.booster.R
import com.example.booster.ui.home.HomeFragment
import com.example.booster.ui.selectStore.SelectStoreActivity
import com.example.booster.util.UserManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_bottom_tab.*
import kotlinx.android.synthetic.main.tab_layout.*


class BottomTabActivity : AppCompatActivity() {
    var flag = 0
    var token = ""
    var myUniv = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_tab)

        flag = intent.getIntExtra("alertFlag", 2)
        setAdapter()
        setTabBar()
        click()
        if (flag == 1) bottom_vp.currentItem = 3

        if (intent.hasExtra("orderIdx")) {
            bottom_vp.currentItem = 3
        }

        if (intent.hasExtra("token")) {
            bottom_vp.currentItem = 0
            token = intent.getStringExtra("token")
            myUniv = intent.getIntExtra("univ", 1)
            Log.e("token", token)
            UserManager.token = token
            UserManager.univ = myUniv
        }

//        bottom_vp.setOnPageChangeListener(object : OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//
//            override fun onPageSelected(position: Int) {
//                if(position==0){
//                    HomeFragment().userVisibleHint = true
//                }
//            }
//        })
    }

    private fun setTabBar() {
        val bottomTabBar: View = LayoutInflater.from(this).inflate(R.layout.tab_layout, null)
        bottom_tab_layout.run {

            addTab(
                this.newTab()
                    .setCustomView(bottomTabBar.findViewById(R.id.tab_cl_home) as ConstraintLayout)
            )
            addTab(
                this.newTab()
                    .setCustomView(bottomTabBar.findViewById(R.id.tab_cl_store) as ConstraintLayout)
            )
            addTab(
                this.newTab()
                    .setCustomView(bottomTabBar.findViewById(R.id.tab_cl_order) as ConstraintLayout)
            )
            addTab(
                this.newTab()
                    .setCustomView(bottomTabBar.findViewById(R.id.tab_cl_history) as ConstraintLayout)
            )
            addTab(
                this.newTab()
                    .setCustomView(bottomTabBar.findViewById(R.id.tab_cl_my) as ConstraintLayout)
            )

            // 인디케이터 없애기
            setSelectedTabIndicator(0)
        }
    }

    private fun setAdapter() {
        bottom_vp.adapter =
            BottomTabAdapter(
                supportFragmentManager,
                5
            )

        bottom_vp.offscreenPageLimit = 4
        bottom_vp.currentItem = 0

        bottom_vp.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(bottom_tab_layout))

        bottom_tab_layout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 2) {
                    val intent = Intent(this@BottomTabActivity, SelectStoreActivity::class.java)
                    val t = bottom_tab_layout.getTabAt(bottom_vp!!.currentItem)
                    t!!.select()
                    startActivity(intent)
                } else {
                    bottom_vp!!.currentItem = tab.position

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    fun click() {
        tab_booster.setOnClickListener {
            val intent = Intent(this, SelectStoreActivity::class.java)
            startActivity(intent)
        }
    }
}
