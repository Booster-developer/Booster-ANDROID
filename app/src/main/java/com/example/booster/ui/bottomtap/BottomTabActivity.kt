package com.example.booster.ui.bottomtap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.example.booster.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_bottom_tab.*

class BottomTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_tab)

        setAdapter()
        setTabBar()
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

    private fun setAdapter(){
        bottom_vp.adapter =
            BottomTabAdapter(
                supportFragmentManager,
                5
            )

        bottom_vp.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(bottom_tab_layout))

        bottom_tab_layout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                bottom_vp!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}
