package com.example.booster.ui.fileStorage

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

interface FragmentToActivity {
    fun communicateRange(range:String, min:Int, max:Int)
    fun communicateNum(num:Int)
    fun sendType(type: String, frag: DialogFragment)
}