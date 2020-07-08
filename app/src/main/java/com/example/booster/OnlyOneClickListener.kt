package com.example.booster

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View

class OnlyOneClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 300
) :
    View.OnClickListener {

    private var clickable = true

    override fun onClick(view: View?) {
        if (clickable) {
            clickable = false
            view?.run {
                postDelayed({
                    clickable = true
                }, interval)
                clickListener.onClick(view)
            }
        } else {
            Log.e(TAG, "waiting for a while")
        }
    }
}

fun View.onlyOneClickListener(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnlyOneClickListener(listener))
}