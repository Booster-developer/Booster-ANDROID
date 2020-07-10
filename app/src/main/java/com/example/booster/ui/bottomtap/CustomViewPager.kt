package com.example.booster.ui.bottomtap

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager : ViewPager {
    var enables = false

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context) : super(context)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (enables) return super.onInterceptTouchEvent(ev)
        else {
            if (ev!!.action == MotionEvent.ACTION_MOVE) {

            } else if (super.onInterceptTouchEvent(ev)) super.onTouchEvent(ev)
        }
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (enables) return super.onTouchEvent(ev)
        else return ev!!.action != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev)
    }

    fun setEnable(enable: Boolean) {
        this.enables = enable
    }

}