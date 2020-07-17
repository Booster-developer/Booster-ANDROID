package com.example.booster.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.booster.application.BoosterApplication.Companion.globalApplication
import com.example.booster.R

class BoosterUtil(var context: Context = globalApplication) {
    fun getPathFromUri(uri: Uri?): String? {
        if (uri == null) {
            return ""
        }

        //val cursor: Cursor? = context.getContentResolver().query(uri, null, null, null, null)
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToNext()
        var path = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()

        Log.e("pathcheck", path)

        return path
    }

    fun getFileName(uri: Uri?): String? {
        if (uri == null) {
            return ""
        }
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToNext()
        val path = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        val filePath = path?.split("/")

        return filePath?.get(filePath.size - 1)
    }

    fun getFileType(filePath: String): String? {
//        val typeList = getPathFromUri(uri)?.split(".")
//        return typeList?.get(typeList.size - 1)
        return if(filePath.contains(".")){
            filePath.substring(filePath.lastIndexOf("."))
        }else{
            "n/a"
        }
    }


}