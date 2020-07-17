package com.example.booster.util

import android.view.View
import android.view.animation.AlphaAnimation


class AnimationUtil {

    fun fade_out(view: View){
        val a : AlphaAnimation = AlphaAnimation(0.0f,1.0f)
        a.fillAfter = true
        a.duration = 1000
        view.startAnimation(a)
    }

    fun fade_in(view: View){
        val a :AlphaAnimation = AlphaAnimation(1.0f,0.0f)
        a.fillAfter = true
        a.duration = 1000
        view.startAnimation(a)
    }
}