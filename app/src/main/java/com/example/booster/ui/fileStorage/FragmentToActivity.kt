package com.example.booster.ui.fileStorage

interface FragmentToActivity {
    fun communicateRange(range:String, min:Int, max:Int)
    fun communicateNum(num:String)
}