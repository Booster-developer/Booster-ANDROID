package com.example.booster.ui.bottomtap

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.booster.ui.home.HomeFragment
import com.example.booster.ui.myPage.MypageFragment
import com.example.booster.ui.orderList.OrderListFragment
import com.example.booster.ui.storeList.StoreListFragment


class BottomTabAdapter (fm : FragmentManager, val fragmentCount : Int):
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return HomeFragment()
            1 -> return StoreListFragment()
            2 -> return null!! //여기서 SelectStoreActivity로 가야함
            3 -> return OrderListFragment()
            4 -> return MypageFragment()
            else -> null!!
        }
    }
    override fun getCount(): Int = fragmentCount
}