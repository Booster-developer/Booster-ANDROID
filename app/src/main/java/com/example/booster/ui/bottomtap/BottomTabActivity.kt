package com.example.booster.ui.bottomtap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.booster.R
import kotlinx.android.synthetic.main.activity_bottom_tab.*

class BottomTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_tab)

        setTabBar()
    }

    private fun setTabBar() {
        val bottomTabBar: View = LayoutInflater.from(this).inflate(R.layout.tab_layout, null)
        bottom_tab_layout.run {
            setupWithViewPager(bottom_vp)

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
}
